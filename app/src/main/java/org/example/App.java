package org.example;

import org.example.animation.AnimationFrame;
import org.example.animation.FireworkFrameGenerator;
import org.example.animation.FramePlayer;
import org.example.display.AnsiTerminal;
import org.example.platform.PlatformDetector;

import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            AnsiTerminal.clearScreen();
            AnsiTerminal.hideCursor();

            PlatformDetector platform = new PlatformDetector();
            PlatformDetector.TerminalSize size = platform.getTerminalSize();

            FireworkFrameGenerator generator = new FireworkFrameGenerator(size.width, size.height);
            List<AnimationFrame> frames = generator.generateFireworkSequence();

            FramePlayer player = new FramePlayer(frames, 15);
            player.play(3);

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
