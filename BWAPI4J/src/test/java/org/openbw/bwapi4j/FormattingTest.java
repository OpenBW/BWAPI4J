package org.openbw.bwapi4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;

public class FormattingTest {
  private static List<Path> getDirectoryContents(final Path directory) {
    try {
      return Files.walk(Paths.get(directory.toString()))
          .filter(Files::isRegularFile)
          .collect(Collectors.toList());
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Tests whether the names of native functions have a "_native" suffix. Omitting the suffix does
   * not cause any errors. However, the presence of the suffix increases readability and reduces
   * function overloading issues.
   */
  @Test
  public void CheckNativeSuffix() throws IOException {
    for (final Path file : getDirectoryContents(Paths.get("src", "main", "java"))) {
      if (!Files.isRegularFile(file)) {
        continue;
      }

      int lineNumber = 0;

      for (final String line : Files.readAllLines(file)) {
        ++lineNumber;

        if (line.isEmpty()) {
          continue;
        }

        if (line.contains("private native ") && !line.contains("_native")) {
          Assert.fail(
              "\"_native\" suffix not detected: "
                  + file.getFileName().toString()
                  + ":"
                  + lineNumber
                  + ": "
                  + line.trim());
        }
      }
    }
  }
}
