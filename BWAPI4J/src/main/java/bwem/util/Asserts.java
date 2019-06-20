package bwem.util;

public class Asserts {
  public static boolean BWEM_ASSERTS = true;

  public static void bwem_assert(final boolean condition, final String message) {
    if (BWEM_ASSERTS && !condition) {
      if (message != null && !message.isEmpty()) {
        throw new BwemException(message);
      } else {
        throw new BwemException();
      }
    }
  }

  public static void bwem_assert(final boolean condition) {
    bwem_assert(condition, null);
  }
}
