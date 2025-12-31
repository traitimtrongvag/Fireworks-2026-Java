package org.example.display;

import org.example.platform.PlatformDetector;

public class StartupBanner {
  private final PlatformDetector platform;

  public StartupBanner(PlatformDetector platform) {
    this.platform = platform;
  }

  public void display() {
    clearScreen();

    String[] banner = {
        "╔═══════════════════════════════════════╗",
        "║                                       ║",
        "║     🎆  FIREWORKS SIMULATOR  🎆      ║",
        "║                                       ║",
        "╚═══════════════════════════════════════╝",
        "",
        "  Platform: " + platform.getPlatformName(),
        "  Terminal: " + getTerminalSize(),
        "",
        "  Starting in 3 seconds...",
        ""
    };

    for (String line : banner) {
      System.out.println(line);
    }
    System.out.flush();

    sleep(3000);
  }

  private void clearScreen() {
    System.out.print("\033[2J\033[H");
    System.out.flush();
  }

  private String getTerminalSize() {
    try {
      return System.getenv("COLUMNS") + "x" + System.getenv("LINES");
    } catch (Exception e) {
      return "Auto-detected";
    }
  }

  private void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
