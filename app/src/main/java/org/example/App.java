package org.example;

import org.example.animation.FramePlayer;
import org.example.display.AnsiTerminal;
import org.example.platform.PlatformDetector;

public class App {
    public static void main(String[] args) {
        try {
            AnsiTerminal.clearScreen();

            PlatformDetector platform = new PlatformDetector();
            PlatformDetector.TerminalSize size = platform.getTerminalSize();

            FramePlayer player = new FramePlayer(size.width, size.height);
            player.playInfinite();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                AnsiTerminal.showCursor();
            } catch (Exception e) {
                // Ignore
            }
        }
    }
}
