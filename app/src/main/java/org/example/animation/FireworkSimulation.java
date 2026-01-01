package org.example.animation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class FireworkSimulation {
  private final List<Rocket> rockets;
  private final List<FireworkParticle> particles;
  private final Random random;
  private final int width;
  private final int height;
  private int framesSinceLastLaunch;

  public FireworkSimulation(int width, int height) {
    this.rockets = new ArrayList<>();
    this.particles = new ArrayList<>();
    this.random = new Random();
    this.width = width;
    this.height = height;
    this.framesSinceLastLaunch = 0;
  }

  public void update() {
    framesSinceLastLaunch++;

    if (shouldLaunchRocket()) {
      launchRocket();
      framesSinceLastLaunch = 0;
    }

    updateRockets();
    updateParticles();
  }

  private boolean shouldLaunchRocket() {
    int minInterval = rockets.size() > 3 ? 25 : 15;
    int maxInterval = 45;
    int interval = minInterval + random.nextInt(maxInterval - minInterval);
    return framesSinceLastLaunch >= interval;
  }

  private void launchRocket() {
    int x = width / 4 + random.nextInt(width / 2);
    int targetY = height / 4 + random.nextInt(height / 3);
    rockets.add(new Rocket(x, height - 2, targetY));
  }

  private void updateRockets() {
    Iterator<Rocket> it = rockets.iterator();
    while (it.hasNext()) {
      Rocket rocket = it.next();
      rocket.update();

      if (rocket.hasTrail()) {
        particles.add(rocket.createTrailParticle());
      }

      if (rocket.shouldExplode()) {
        particles.addAll(rocket.explode());
        it.remove();
      }
    }
  }

  private void updateParticles() {
    Iterator<FireworkParticle> it = particles.iterator();
    while (it.hasNext()) {
      FireworkParticle particle = it.next();
      particle.update();

      if (particle.isDead() || particle.getY() >= height) {
        it.remove();
      }
    }
  }

  public char[][] renderToBuffer() {
    char[][] buffer = new char[height][width];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        buffer[y][x] = ' ';
      }
    }

    for (Rocket rocket : rockets) {
      int x = rocket.getX();
      int y = rocket.getY();
      if (isValid(x, y)) {
        buffer[y][x] = '^';
      }
    }

    for (FireworkParticle particle : particles) {
      int x = particle.getX();
      int y = particle.getY();
      if (isValid(x, y)) {
        char current = buffer[y][x];
        char symbol = particle.getSymbol();
        if (current == ' ' || getPriority(symbol) > getPriority(current)) {
          buffer[y][x] = symbol;
        }
      }
    }

    return buffer;
  }

  private boolean isValid(int x, int y) {
    return x >= 0 && x < width && y >= 0 && y < height;
  }

  private int getPriority(char c) {
    return switch (c) {
      case '*' -> 4;
      case '+' -> 3;
      case '·' -> 2;
      case '.', '|' -> 1;
      default -> 0;
    };
  }

  private class Rocket {
    private double x;
    private double y;
    private final int targetY;
    private final BurstPattern pattern;
    private int trailCounter;

    Rocket(int x, int y, int targetY) {
      this.x = x;
      this.y = y;
      this.targetY = targetY;
      this.pattern = BurstPattern.randomPattern();
      this.trailCounter = 0;
    }

    void update() {
      y -= 2.0;
      trailCounter++;
    }

    boolean shouldExplode() {
      return y <= targetY;
    }

    boolean hasTrail() {
      return trailCounter % 2 == 0;
    }

    FireworkParticle createTrailParticle() {
      return new FireworkParticle(x, y + 1, 0, 0, 8, '|');
    }

    List<FireworkParticle> explode() {
      return pattern.createBurst(x, y);
    }

    int getX() {
      return (int) x;
    }

    int getY() {
      return (int) y;
    }
  }
}
