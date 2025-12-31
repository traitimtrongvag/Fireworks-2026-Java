package org.example;

import org.example.display.StartupBanner;
import org.example.display.TerminalDisplay;
import org.example.engine.FireworkEngine;
import org.example.platform.PlatformDetector;

public class App {
    public static void main(String[] args) {
        PlatformDetector platform = new PlatformDetector();
        StartupBanner banner = new StartupBanner(platform);

        banner.display();

        TerminalDisplay display = new TerminalDisplay();
        FireworkEngine engine = new FireworkEngine(display);

        try {
            display.initialize();
            engine.run();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            display.shutdown();
        }
    }
}
