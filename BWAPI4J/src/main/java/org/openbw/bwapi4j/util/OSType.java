package org.openbw.bwapi4j.util;

import java.util.Locale;

/**
 * A small set of enums defining what type of system you are running on.
 *
 * <p>The default type means that it is likely to be a *nix.
 */
public enum OSType {
  DEFAULT,
  MAC,
  WINDOWS,
  ;

  /**
   * @return The type of OS.
   *     <p>http://lopica.sourceforge.net/os.html is a source of the various values from {@code
   *     os.name}.
   */
  public static OSType computeType() {
    String osName = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
    if (osName.contains("win")) {
      return WINDOWS;
    } else if (osName.contains("mac")) {
      return MAC;
    } else {
      return DEFAULT;
    }
  }
}
