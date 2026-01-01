package org.example.animation;

import org.example.platform.PlatformDetector;

public class FramePlayer {
    private final FireworkSimulation simulation;
    private final PlatformDetector platform;
    private final int width;
    private final int height;

    private static final String CLEAR = "\033[2J";
    private static final String HOME = "\033[H";
    private static final String HIDE_CURSOR = "\033[?25l";
    private static final String SHOW_CURSOR = "\033[?25h";

    public FramePlayer(int width, int height) {
        this.simulation = new FireworkSimulation(width, height);
        this.platform = new PlatformDetector();
        this.width = width;
        this.height = height;
    }

    public void playInfinite() {
        System.out.print(CLEAR + HIDE_CURSOR);
        System.out.flush();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.print(SHOW_CURSOR + "\033[0m");
            System.out.flush();
        }));

        while (true) {
            simulation.update();
            render(simulation.renderToBuffer());
            sleep(65);
        }
    }

    private void render(char[][] buffer) {
        StringBuilder output = new StringBuilder(HOME);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char c = buffer[y][x];
                if (c != ' ') {
                    String color = getColor(c, x, y);
                    output.append(color).append(c).append("\033[0m");
                } else {
                    output.append(c);
                }
            }
            output.append("\n");
        }

        System.out.print(output);
        System.out.flush();
    }

    private String getColor(char c, int x, int y) {
        int paletteId = (x / 20 + y / 10) % 4;

        String[][] palettes = {
                { "\033[38;2;255;60;60m", "\033[38;2;255;140;0m", "\033[38;2;255;200;0m" },
                { "\033[38;2;80;120;255m", "\033[38;2;0;200;255m", "\033[38;2;200;230;255m" },
                { "\033[38;2;180;80;255m", "\033[38;2;255;100;200m", "\033[38;2;255;200;230m" },
                { "\033[38;2;60;255;60m", "\033[38;2;200;255;100m", "\033[38;2;240;255;200m" }
        };

        String[] palette = palettes[paletteId];

        return switch (c) {
            case '*' -> palette[0];
            case '+' -> palette[1];
            case '·', '.' -> palette[2];
            case '|', '^' -> "\033[38;2;255;255;255m";
            default -> "\033[38;2;150;150;150m";
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
