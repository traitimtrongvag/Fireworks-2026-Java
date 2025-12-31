package org.example.fireworks;

import org.example.physics.Particle;
import org.example.physics.Vector3D;
import org.example.visual.Color;

import java.util.ArrayList;
import java.util.List;

public class SphereFirework extends FireworkType {

  public SphereFirework(Vector3D origin, Color color) {
    super(origin, color);
  }

  @Override
  public List<Particle> explode() {
    List<Particle> particles = new ArrayList<>();
    int particleCount = 120 + random.nextInt(60);

    for (int i = 0; i < particleCount; i++) {
      double theta = random.nextDouble() * 2 * Math.PI;
      double phi = Math.acos(2 * random.nextDouble() - 1);

      double speed = 3 + random.nextDouble() * 2;
      Vector3D velocity = new Vector3D(
          Math.sin(phi) * Math.cos(theta) * speed,
          Math.sin(phi) * Math.sin(theta) * speed,
          Math.cos(phi) * speed);

      double life = 1.5 + random.nextDouble() * 1.0;
      particles.add(new Particle(origin, velocity, randomizeColor(color), life));
    }

    return particles;
  }
}
