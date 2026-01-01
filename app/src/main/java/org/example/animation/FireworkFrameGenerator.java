package org.example.animation;

import org.example.visual.Color;

import java.util.*;

public class FireworkFrameGenerator {
  private final int width;
  private final int height;
  private final Random random;

  public FireworkFrameGenerator(int width, int height) {
    this.width = Math.min(width, 120);
    this.height = Math.min(height, 40);
    this.random = new Random();
  }

  public List<AnimationFrame> generateFireworkSequence() {
    List<AnimationFrame> frames = new ArrayList<>();
    List<Firework> fireworks = new ArrayList<>();

    fireworks.add(new Firework(width / 2, height - 5, 0));
    fireworks.add(new Firework(width / 3, height - 5, 20));
    fireworks.add(new Firework(2 * width / 3, height - 5, 40));
    fireworks.add(new Firework(width / 4, height - 5, 60));
    fireworks.add(new Firework(3 * width / 4, height - 5, 80));

    for (int frameNum = 0; frameNum < 120; frameNum++) {
      frames.add(generateFrame(fireworks, frameNum));
    }

    return frames;
  }

  private AnimationFrame generateFrame(List<Firework> fireworks, int frameNum) {
    char[][] buffer = new char[height][width];
    for (int y = 0; y < height; y++) {
      Arrays.fill(buffer[y], ' ');
    }

    for (Firework fw : fireworks) {
      if (frameNum < fw.launchFrame)
        continue;

      int age = frameNum - fw.launchFrame;

      if (age < 15) {
        renderLaunch(buffer, fw, age);
      } else if (age < 50) {
        renderExplosion(buffer, fw, age - 15);
      }
    }

    String[] lines = new String[height];
    for (int y = 0; y < height; y++) {
      lines[y] = new String(buffer[y]);
    }

    return new AnimationFrame(lines);
  }

  private void renderLaunch(char[][] buffer, Firework fw, int age) {
    int rocketY = fw.y - age;
    if (rocketY >= 0 && rocketY < height) {
      set(buffer, fw.x, rocketY, '^');

      for (int i = 1; i <= Math.min(age, 3); i++) {
        int trailY = rocketY + i;
        if (trailY < height) {
          set(buffer, fw.x, trailY, i == 1 ? '|' : '.');
        }
      }
    }
  }

  private void renderExplosion(char[][] buffer, Firework fw, int age) {
    int explosionY = fw.y - 15;
    double radius = age * 0.6;
    int particles = 48;
    double fade = 1.0 - (age / 35.0);

    if (fade <= 0)
      return;

    for (int i = 0; i < particles; i++) {
      double angle = (2 * Math.PI * i) / particles;
      double speed = 1 + random.nextDouble() * 0.3;

      int x = (int) (fw.x + Math.cos(angle) * radius * speed);
      int y = (int) (explosionY + Math.sin(angle) * radius * 0.5 * speed + age * 0.2);

      if (x >= 0 && x < width && y >= 0 && y < height) {
        char symbol = getSymbolForFade(fade);
        set(buffer, x, y, symbol);
      }
    }

    if (age < 5) {
      int rings = 2;
      for (int ring = 0; ring < rings; ring++) {
        double ringRadius = (age + ring * 2) * 0.8;
        int ringParticles = 24;

        for (int i = 0; i < ringParticles; i++) {
          double angle = (2 * Math.PI * i) / ringParticles;
          int x = (int) (fw.x + Math.cos(angle) * ringRadius);
          int y = (int) (explosionY + Math.sin(angle) * ringRadius * 0.4);

          if (x >= 0 && x < width && y >= 0 && y < height) {
            set(buffer, x, y, '*');
          }
        }
      }
    }
  }

  private void set(char[][] buffer, int x, int y, char c) {
    if (x >= 0 && x < width && y >= 0 && y < height) {
      if (buffer[y][x] == ' ' || getPriority(c) > getPriority(buffer[y][x])) {
        buffer[y][x] = c;
      }
    }
  }

  private int getPriority(char c) {
    return switch (c) {
      case '*' -> 4;
      case '+' -> 3;
      case '·' -> 2;
      case '.' -> 1;
      default -> 0;
    };
  }

  private char getSymbolForFade(double fade) {
    if (fade > 0.8)
      return '*';
    if (fade > 0.6)
      return '+';
    if (fade > 0.4)
      return '·';
    if (fade > 0.2)
      return '.';
    return ' ';
  }

  private static class Firework {
    int x;
    int y;
    int launchFrame;

    Firework(int x, int y, int launchFrame) {
      this.x = x;
      this.y = y;
      this.launchFrame = launchFrame;
    }
  }
}
