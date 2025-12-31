package org.example.engine;

import org.example.display.TerminalDisplay;
import org.example.fireworks.*;
import org.example.physics.Particle;
import org.example.physics.Vector3D;
import org.example.visual.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class FireworkEngine {
  private final TerminalDisplay display;
  private final List<Particle> particles;
  private final List<Rocket> rockets;
  private final Random random;
  private double timeSinceLastLaunch;
  private int launchCount;

  private static final double TARGET_FPS = 30.0;
  private static final double FRAME_TIME = 1.0 / TARGET_FPS;
  private static final int MAX_FIREWORKS = 25;

  public FireworkEngine(TerminalDisplay display) {
    this.display = display;
    this.particles = new ArrayList<>();
    this.rockets = new ArrayList<>();
    this.random = new Random();
    this.timeSinceLastLaunch = 0;
    this.launchCount = 0;
  }

  public void run() {
    long lastTime = System.nanoTime();

    while (true) {
      long currentTime = System.nanoTime();
      double deltaTime = (currentTime - lastTime) / 1_000_000_000.0;
      lastTime = currentTime;

      update(deltaTime);
      render();

      sleepToMaintainFrameRate(deltaTime);

      if (shouldExit())
        break;
    }
  }

  private void update(double deltaTime) {
    timeSinceLastLaunch += deltaTime;

    if (shouldLaunchFirework()) {
      launchFirework();
      timeSinceLastLaunch = 0;
    }

    updateRockets(deltaTime);
    updateParticles(deltaTime);
  }

  private boolean shouldLaunchFirework() {
    double interval = calculateLaunchInterval();
    return timeSinceLastLaunch >= interval && launchCount < MAX_FIREWORKS;
  }

  private double calculateLaunchInterval() {
    if (launchCount < 5)
      return 0.8;
    if (launchCount < 15)
      return 1.2 + random.nextDouble() * 0.8;
    return 1.5 + random.nextDouble() * 1.5;
  }

  private void launchFirework() {
    double x = -15 + random.nextDouble() * 30;
    double z = -10 + random.nextDouble() * 20;

    Color color = getRandomColor();
    FireworkType type = createRandomFireworkType(x, z, color);

    rockets.add(new Rocket(x, z, color, type));
    launchCount++;
  }

  private Color getRandomColor() {
    Color[] colors = {
        Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN,
        Color.CYAN, Color.BLUE, Color.PURPLE, Color.PINK,
        Color.WHITE, Color.GOLD
    };
    return colors[random.nextInt(colors.length)];
  }

  private FireworkType createRandomFireworkType(double x, double z, Color color) {
    Vector3D origin = new Vector3D(x, 0, z);
    int type = random.nextInt(100);

    if (type < 35)
      return new SphereFirework(origin, color);
    if (type < 60)
      return new WillowFirework(origin, color);
    if (type < 75)
      return new RingFirework(origin, color);
    if (type < 88)
      return new PalmFirework(origin, color);
    return new HeartFirework(origin, color);
  }

  private void updateRockets(double deltaTime) {
    Iterator<Rocket> rocketIterator = rockets.iterator();

    while (rocketIterator.hasNext()) {
      Rocket rocket = rocketIterator.next();
      rocket.update(deltaTime);

      particles.addAll(rocket.getTrail());

      if (rocket.shouldExplode()) {
        particles.addAll(rocket.explode());
        rocketIterator.remove();
      }
    }
  }

  private void updateParticles(double deltaTime) {
    Iterator<Particle> particleIterator = particles.iterator();

    while (particleIterator.hasNext()) {
      Particle particle = particleIterator.next();
      particle.update(deltaTime);

      if (particle.isDead()) {
        particleIterator.remove();
      }
    }
  }

  private void render() {
    display.render(particles);
  }

  private void sleepToMaintainFrameRate(double deltaTime) {
    double sleepTime = FRAME_TIME - deltaTime;
    if (sleepTime > 0) {
      try {
        Thread.sleep((long) (sleepTime * 1000));
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  private boolean shouldExit() {
    return launchCount >= MAX_FIREWORKS && particles.isEmpty() && rockets.isEmpty();
  }
}
