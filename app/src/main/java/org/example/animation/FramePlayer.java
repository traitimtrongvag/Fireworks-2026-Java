package org.example.animation;

import org.example.platform.PlatformDetector;

import java.util.List;
import java.util.Random;

public class FramePlayer {
    private final List<AnimationFrame> frames;
    private final int fps;
    private final PlatformDetector platform;
    private final Random random;
    
    private static final String CLEAR = "\033[2J";
    private static final String HOME = "\033[H";
    private static final String HIDE_CURSOR = "\033[?25l";
    private static final String SHOW_CURSOR = "\033[?25h";
    
    public FramePlayer(List<AnimationFrame> frames, int fps) {
        this.frames = frames;
        this.fps = fps;
        this.platform = new PlatformDetector();
        this.random = new Random();
    }
    
    public void play(int loops) {
        System.out.print(CLEAR + HIDE_CURSOR);
        System.out.flush();
        
        int loopCount = 0;
        while (loops == -1 || loopCount < loops) {
            for (AnimationFrame frame : frames) {
                renderFrame(frame);
                sleep(1000 / fps);
            }
            loopCount++;
        }
        
        System.out.print(SHOW_CURSOR);
        System.out.flush();
    }
    
    private void renderFrame(AnimationFrame frame) {
        PlatformDetector.TerminalSize termSize = platform.getTerminalSize();
        
        StringBuilder output = new StringBuilder(HOME);
        
        int offsetY = Math.max(0, (termSize.height - frame.getHeight()) / 2);
        int offsetX = Math.max(0, (termSize.width - frame.getWidth()) / 2);
        
        for (int i = 0; i < offsetY; i++) {
            output.append("\n");
        }
        
        for (String line : frame.getLines()) {
            output.append(" ".repeat(offsetX));
            output.append(colorize(line));
            output.append("\n");
        }
        
        System.out.print(output);
        System.out.flush();
    }
    
    private String colorize(String line) {
        StringBuilder colored = new StringBuilder();
        
        int paletteIndex = Math.abs(line.hashCode()) % 4;
        String[][] colorPalettes = {
            // Red-Orange-Yellow
            {"\033[38;2;255;60;60m", "\033[38;2;255;140;0m", "\033[38;2;255;200;0m"},
            // Blue-Cyan-White
            {"\033[38;2;80;120;255m", "\033[38;2;0;200;255m", "\033[38;2;200;230;255m"},
            // Purple-Pink-White
            {"\033[38;2;180;80;255m", "\033[38;2;255;100;200m", "\033[38;2;255;200;230m"},
            // Green-Yellow-White
            {"\033[38;2;60;255;60m", "\033[38;2;200;255;100m", "\033[38;2;240;255;200m"}
        };
        String[] palette = colorPalettes[paletteIndex];
        
        for (char c : line.toCharArray()) {
            if (c != ' ') {
                String color = getColorForChar(c, palette);
                colored.append(color).append(c).append("\033[0m");
            } else {
                colored.append(c);
            }
        }
        
        return colored.toString();
    }
    
    private String getColorForChar(char c, String[] palette) {
        return switch (c) {
            case '*' -> palette[0];
            case '+' -> palette[1]; 
            case '·', '.' -> palette[2];
            case '|', '^' -> "\033[38;2;255;255;255m";
            default -> "\033[38;2;180;180;180m";
        };
    }
    
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
