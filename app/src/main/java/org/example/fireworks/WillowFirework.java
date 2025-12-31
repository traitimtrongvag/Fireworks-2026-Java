package org.example.fireworks;

import org.example.physics.Particle;
import org.example.physics.Vector3D;
import org.example.visual.Color;

import java.util.ArrayList;
import java.util.List;

public class WillowFirework extends FireworkType {

  public WillowFirework(Vector3D origin, Color color) {
    super(origin, color);
  }

  @Override
  public List<Particle> explode() {
    List<Particle> particles = new ArrayList<>();
    int streamCount = 25;
    int particlesPerStream = 8;

    for (int i = 0; i < streamCount; i++) {
      double angle = (2 * Math.PI * i) / streamCount;
      double elevation = -0.3 + random.nextDouble() * 0.4;

      double baseSpeed = 2.5 + random.nextDouble() * 1.5;
      Vector3D baseDirection = new Vector3D(
          Math.cos(angle),
          elevation,
          Math.sin(angle)).normalize();

      for (int j = 0; j < particlesPerStream; j++) {
        double speed = baseSpeed * (0.8 + j * 0.05);
        Vector3D velocity = baseDirection.multiply(speed);

        double life = 2.2 + random.nextDouble() * 0.8;
        particles.add(new Particle(origin, velocity, randomizeColor(color), life));
      }
    }

    return particles;
  }
}
