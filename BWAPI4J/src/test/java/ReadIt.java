import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class ReadIt {

  public static void main(String[] args) throws IOException {
    CPP14Parser cpp14Parser =
        new CPP14Parser(
            new CommonTokenStream(
                new CPP14Lexer(
                    new ANTLRFileStream("Path to C++ file"))));
    CPP14Parser.TranslationunitContext translationunit = cpp14Parser.translationunit();
    translationunit.accept(
        new CPP14BaseVisitor<Object>() {
          ClassModel currentClass;
          MemberModel currentMember;

          @Override
          public Object visitDeclaratorid(CPP14Parser.DeclaratoridContext ctx) {
            if (currentMember != null) {
              currentMember.name = ctx.idexpression().getText();
            }
            return super.visitDeclaratorid(ctx);
          }

          @Override
          public Object visitParameterdeclarationclause(
              CPP14Parser.ParameterdeclarationclauseContext ctx) {
            if (currentMember != null && ctx.children != null) {
              currentMember.parametrized = true;
            }
            return super.visitParameterdeclarationclause(ctx);
          }

          @Override
          public Object visitSimpletypespecifier(CPP14Parser.SimpletypespecifierContext ctx) {
            if (currentMember != null) {
              currentMember.type = ctx.getText();
            }
            return super.visitSimpletypespecifier(ctx);
          }

          @Override
          public Object visitMemberdeclaration(CPP14Parser.MemberdeclarationContext ctx) {
            if (currentClass != null) {
              currentMember = new MemberModel();
              currentClass.memberModels.add(currentMember);
            }
            return super.visitMemberdeclaration(ctx);
          }

          @Override
          public Object visitClassspecifier(CPP14Parser.ClassspecifierContext ctx) {
            currentClass = new ClassModel();
            currentClass.className =
                ctx.classhead().classheadname().classname().Identifier().getSymbol().getText();
            super.visitClassspecifier(ctx);
            currentMember = null;
            System.out.println(currentClass);
            return currentClass;
          }
        });
  }

  public static class ClassModel {

    public String className;
    public List<MemberModel> memberModels = new ArrayList<>();

    @Override
    public String toString() {
      return "Class " + className + "\n" +
          memberModels.stream()
              .filter(it -> !it.parametrized)
              .map(MemberModel::toString).collect(Collectors.joining("\n"));
    }
  }

  public static class MemberModel {

    public String type;
    public String name;
    public boolean parametrized;

    @Override
    public String toString() {
      return type + " " + name;
    }
  }
}
