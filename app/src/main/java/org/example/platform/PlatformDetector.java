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
        if (isTermux) return "Termux/Android";
        if (isWindows()) return "Windows";
        if (isLinux()) return "Linux";
        if (isMac()) return "macOS";
        return "Unknown";
    }
    
    public boolean supportsAnsiColors() {
        return true;
    }
    
    public TerminalSize getTerminalSize() {
        int width = 80;
        int height = 24;
        
        try {
            if (isTermux) {
                String cols = System.getenv("COLUMNS");
                String lines = System.getenv("LINES");
                if (cols != null) width = Integer.parseInt(cols);
                if (lines != null) height = Integer.parseInt(lines);
            } else if (isLinux() || isMac()) {
                Process process = Runtime.getRuntime().exec(new String[]{"sh", "-c", "stty size < /dev/tty"});
                process.waitFor();
                String output = new String(process.getInputStream().readAllBytes()).trim();
                if (!output.isEmpty()) {
                    String[] parts = output.split(" ");
                    if (parts.length == 2) {
                        height = Integer.parseInt(parts[0]);
                        width = Integer.parseInt(parts[1]);
                    }
                }
            } else if (isWindows()) {
                Process process = Runtime.getRuntime().exec("mode con");
                process.waitFor();
                String output = new String(process.getInputStream().readAllBytes());
                for (String line : output.split("\n")) {
                    if (line.contains("Columns")) {
                        width = Integer.parseInt(line.replaceAll("[^0-9]", ""));
                    } else if (line.contains("Lines")) {
                        height = Integer.parseInt(line.replaceAll("[^0-9]", ""));
                    }
                }
            }
        } catch (Exception e) {
            // Use defaults
        }
        
        return new TerminalSize(Math.max(80, width), Math.max(24, height));
    }
    
    public static class TerminalSize {
        public final int width;
        public final int height;
        
        public TerminalSize(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }
}
