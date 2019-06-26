package mockdata;

import bwapi.Position;
import bwapi.Unit;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.BWEventListener;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UnitType.WhatBuilds;
import org.openbw.bwapi4j.type.UpgradeType;
import org.openbw.bwapi4j.type.WeaponType;

public class TestDataExporter implements BWEventListener {

  private static final String TARGET_DIR = "BWAPI4J/build/";
  private static final Class[] toExport = {
    UnitType.class, UpgradeType.class, TechType.class, WeaponType.class
  };

  private BW bw;

  public static void main(String[] args) {
    TestDataExporter exporter = new TestDataExporter();
    exporter.bw = new BW(exporter);
    exporter.bw.startGame();
  }

  @Override
  public void onStart() {
    try (PrintWriter out = new PrintWriter(TARGET_DIR + "BWDataProvider.java")) {
      addPackage(out);
      out.println("public final class BWDataProvider {");
      out.println("    private BWDataProvider() { }");
      out.println("    public static void injectValues() throws Exception {");
      for (Class c : toExport) {
        String typeName = c.getSimpleName();
        String cname = typeName + "s";
        out.println("        " + cname + ".initialize" + typeName + "();");
        createExportHelperClass(cname, o -> export(c, o));
      }
      out.println("    }");
      out.println("}");
    } catch (FileNotFoundException e) {
      throw new UncheckedIOException(e);
    }
  }

  private void createExportHelperClass(String className, Consumer<PrintWriter> typeWriter) {
    try (PrintWriter out = new PrintWriter(TARGET_DIR + className + ".java")) {
      addImports(out);
      out.println("class " + className + " {");
      typeWriter.accept(out);
      out.println("}");
    } catch (FileNotFoundException e) {
      throw new UncheckedIOException(e);
    }
  }

  private void addImports(PrintWriter out) {
    addPackage(out);
    out.println("import java.lang.reflect.*;");
    out.println("import java.util.*;");
    out.println("import java.util.stream.*;");
    out.println("import org.openbw.bwapi4j.util.*;");
    out.println("import org.openbw.bwapi4j.type.*;");
    out.println();
  }

  private void addPackage(PrintWriter out) {
    out.println("package org.openbw.bwapi4j.test;");
  }

  private <E extends Enum<E>> void export(Class<E> typeClass, PrintWriter out) {
    List<Field> fields =
        Stream.of(typeClass.getDeclaredFields())
            .filter(f -> (f.getModifiers() & Modifier.STATIC) == 0)
            .collect(Collectors.toList());
    fields.forEach(f -> f.setAccessible(true));
    out.println("    static void initialize" + typeClass.getSimpleName() + "() throws Exception {");
    EnumSet.allOf(typeClass)
        .stream()
        .forEach(
            e -> out.println("        initialize" + typeClass.getSimpleName() + "_" + e + "();"));
    out.println("    }");
    out.println();
    EnumSet.allOf(typeClass)
        .stream()
        .forEach(
            e -> {
              out.println(
                  "    private static void initialize"
                      + typeClass.getSimpleName()
                      + "_"
                      + e
                      + "() throws Exception {");
              out.println("        Class<?> c = " + typeClass.getSimpleName() + ".class;");
              out.println(
                  "        Map<String, Field> fields = Stream.of(c.getDeclaredFields()).collect(Collectors.toMap(f -> f.getName(), f -> {\n"
                      + "            f.setAccessible(true);\n"
                      + "            return f;\n"
                      + "        })); ");
              fields.forEach(
                  f -> {
                    try {
                      Object value = f.get(e);
                      value = toValue(value);
                      out.println(
                          "        fields.get(\""
                              + f.getName()
                              + "\").set("
                              + typeClass.getSimpleName()
                              + "."
                              + e
                              + ", "
                              + value
                              + ");");
                    } catch (IllegalAccessException e1) {
                      throw new RuntimeException(e1);
                    }
                  });
              out.println("    }");
            });
    out.println("    private static Map<?, ?> toMap(Object... element) {");
    out.println("        Map<Object, Object> map = new HashMap<>();");
    out.println("        for (int i = 0; i < element.length; i += 2) {");
    out.println("            map.put(element[i], element[i + 1]);");
    out.println("        }");
    out.println("        return map;");
    out.println("    }");
  }

  private <E extends Enum<E>> String toValue(Object value) {
    if (value == null) return "null";
    if (value instanceof Enum<?>) {
      value = value.getClass().getSimpleName() + "." + ((Enum) value).name();
    } else if (value instanceof WhatBuilds) {
      WhatBuilds pair = (WhatBuilds) value;
      value =
          "new UnitType.WhatBuilds("
              + toValue(pair.getUnitType())
              + ", "
              + toValue(pair.getAmount())
              + ")";
    } else if (value instanceof ArrayList<?>) {
      List<?> list = (List<?>) value;
      value =
          "new ArrayList(Arrays.asList("
              + list.stream().map(this::toValue).collect(Collectors.joining(", "))
              + "))";
    } else if (value instanceof int[]) {
      int[] ints = (int[]) value;
      value =
          "new int[] {"
              + IntStream.of(ints).mapToObj(Integer::toString).collect(Collectors.joining(", "))
              + "}";
    } else if (value.getClass().isArray()) {
      Object[] array = (Object[]) value;
      value =
          "new "
              + ((Object[]) value).getClass().getComponentType().getSimpleName()
              + " [] {"
              + Stream.of(array).map(this::toValue).collect(Collectors.joining(", "))
              + "}";
    } else if (value instanceof Map<?, ?>) {
      value =
          "toMap("
              + ((Map<?, ?>) value)
                  .entrySet()
                  .stream()
                  .map(e -> toValue(e.getKey()) + ", " + toValue(e.getValue()))
                  .collect(Collectors.joining(", "))
              + ")";
    }
    return value.toString();
  }

  @Override
  public void onEnd(boolean isWinner) {}

  @Override
  public void onFrame() {
    bw.getInteractionHandler().leaveGame();
    bw.exit();
  }

  @Override
  public void onSendText(String text) {
    // do nothing
  }

  @Override
  public void onReceiveText(Player player, String text) {
    // do nothing
  }

  @Override
  public void onPlayerLeft(Player player) {
    // do nothing
  }

  @Override
  public void onNukeDetect(Position target) {
    // do nothing
  }

  @Override
  public void onUnitDiscover(Unit unit) {
    // do nothing
  }

  @Override
  public void onUnitEvade(Unit unit) {
    // do nothing
  }

  @Override
  public void onUnitShow(Unit unit) {
    // do nothing
  }

  @Override
  public void onUnitHide(Unit unit) {
    // do nothing
  }

  @Override
  public void onUnitCreate(Unit unit) {
    //    	logger.info("onUnitCreate");
  }

  @Override
  public void onUnitDestroy(Unit unit) {
    // do nothing
  }

  @Override
  public void onUnitMorph(Unit unit) {
    // do nothing
  }

  @Override
  public void onUnitRenegade(Unit unit) {
    // do nothing
  }

  @Override
  public void onSaveGame(String gameName) {
    // do nothing
  }

  @Override
  public void onUnitComplete(Unit unit) {
    //    	logger.info("onUnitComplete");
  }
}
