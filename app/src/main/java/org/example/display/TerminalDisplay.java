package org.example.display;

import org.example.physics.Particle;
import org.example.physics.Vector3D;
import org.example.platform.PlatformDetector;
import org.fusesource.jansi.AnsiConsole;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.List;

public class TerminalDisplay {
  private Terminal terminal;
  private int width;
  private int height;
  private char[][] buffer;
  private String[][] colorBuffer;
  private final PlatformDetector platform;

  private static final String RESET = "\033[0m";
  private static final String CLEAR = "\033[2J";
  private static final String HIDE_CURSOR = "\033[?25l";
  private static final String SHOW_CURSOR = "\033[?25h";
  private static final String HOME = "\033[H";

  public TerminalDisplay() {
    this.platform = new PlatformDetector();
  }

  public void initialize() {
    try {
      if (platform.isWindows()) {
        AnsiConsole.systemInstall();
      }

      terminal = TerminalBuilder.builder()
          .system(true)
          .jansi(true)
          .jna(false)
          .dumb(false)
          .build();

      if (!platform.isWindows()) {
        terminal.enterRawMode();
      }

      width = Math.max(80, terminal.getWidth());
      height = Math.max(24, terminal.getHeight());

      buffer = new char[height][width];
      colorBuffer = new String[height][width];

      System.out.print(CLEAR + HIDE_CURSOR);
      System.out.flush();
    } catch (IOException e) {
      throw new RuntimeException("Failed to initialize terminal on " + platform.getPlatformName(), e);
    }
  }

  public void shutdown() {
    System.out.print(CLEAR + HOME + SHOW_CURSOR + RESET);
    System.out.flush();

    try {
      if (terminal != null) {
        terminal.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (platform.isWindows()) {
        AnsiConsole.systemUninstall();
      }
    }
  }

  public void clear() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        buffer[y][x] = ' ';
        colorBuffer[y][x] = null;
      }
    }
  }

  public void render(List<Particle> particles) {
    clear();

    for (Particle particle : particles) {
      Vector3D pos = particle.getPosition();

      double perspective = 50.0 / (50.0 + pos.z);
      int screenX = (int) (width / 2 + pos.x * 2 * perspective);
      int screenY = (int) (height - 5 - pos.y * perspective);

      if (isValidPosition(screenX, screenY)) {
        double brightness = particle.getBrightness();
        char symbol = getSymbolForBrightness(brightness);

        buffer[screenY][screenX] = symbol;
        colorBuffer[screenY][screenX] = particle.getColor()
            .dim(brightness)
            .toAnsiCode();
      }
    }

    display();
  }

  private void display() {
    StringBuilder output = new StringBuilder(HOME);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (colorBuffer[y][x] != null) {
          output.append(colorBuffer[y][x]);
        }
        output.append(buffer[y][x]);
        if (colorBuffer[y][x] != null) {
          output.append(RESET);
        }
      }
      if (y < height - 1) {
        output.append("\n");
      }
    }

    System.out.print(output);
    System.out.flush();
  }

  private boolean isValidPosition(int x, int y) {
    return x >= 0 && x < width && y >= 0 && y < height;
  }

  private char getSymbolForBrightness(double brightness) {
    if (brightness > 0.8)
      return '*';
    if (brightness > 0.6)
      return '+';
    if (brightness > 0.4)
      return '·';
    if (brightness > 0.2)
      return '.';
    return ' ';
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
