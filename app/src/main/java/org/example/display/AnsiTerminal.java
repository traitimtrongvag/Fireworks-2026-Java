package org.example.display;

import java.io.IOException;

public class AnsiTerminal {

  public static void enableRawMode() throws IOException {
    if (isUnix()) {
      executeCommand("stty -icanon -echo min 1 < /dev/tty");
    }
  }

  public static void disableRawMode() throws IOException {
    if (isUnix()) {
      executeCommand("stty sane < /dev/tty");
    }
  }

  public static void clearScreen() {
    System.out.print("\033[2J\033[H");
    System.out.flush();
  }

  public static void hideCursor() {
    System.out.print("\033[?25l");
    System.out.flush();
  }

  public static void showCursor() {
    System.out.print("\033[?25h");
    System.out.flush();
  }

  private static boolean isUnix() {
    String os = System.getProperty("os.name").toLowerCase();
    return os.contains("nix") || os.contains("nux") || os.contains("mac");
  }

  private static void executeCommand(String command) throws IOException {
    try {
      Runtime.getRuntime().exec(new String[] { "sh", "-c", command }).waitFor();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IOException("Command interrupted", e);
    }
  }
}
