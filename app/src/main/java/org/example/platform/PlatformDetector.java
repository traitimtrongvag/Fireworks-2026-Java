package org.example.platform;

public class PlatformDetector {
  private final String osName;
  private final String osVersion;
  private final boolean isTermux;

  public PlatformDetector() {
    this.osName = System.getProperty("os.name", "").toLowerCase();
    this.osVersion = System.getProperty("os.version", "").toLowerCase();
    this.isTermux = detectTermux();
  }

  private boolean detectTermux() {
    String termuxEnv = System.getenv("TERMUX_VERSION");
    String prefix = System.getenv("PREFIX");
    return termuxEnv != null || (prefix != null && prefix.contains("/com.termux/"));
  }

  public boolean isWindows() {
    return osName.contains("win");
  }

  public boolean isLinux() {
    return osName.contains("nux");
  }

  public boolean isMac() {
    return osName.contains("mac");
  }

  public boolean isTermux() {
    return isTermux;
  }

  public String getPlatformName() {
    if (isTermux)
      return "Termux/Android";
    if (isWindows())
      return "Windows";
    if (isLinux())
      return "Linux";
    if (isMac())
      return "macOS";
    return "Unknown";
  }

  public boolean supportsAnsiColors() {
    return true;
  }
}
