package org.example.fireworks;

import org.example.physics.Particle;
import org.example.physics.Vector3D;
import org.example.visual.Color;

import java.util.ArrayList;
import java.util.List;

public class RingFirework extends FireworkType {

  public RingFirework(Vector3D origin, Color color) {
    super(origin, color);
  }

  @Override
  public List<Particle> explode() {
    List<Particle> particles = new ArrayList<>();
    int rings = 3;
    int particlesPerRing = 50;

    for (int ring = 0; ring < rings; ring++) {
      double ringRadius = 2.5 + ring * 0.5;

      for (int i = 0; i < particlesPerRing; i++) {
        double angle = (2 * Math.PI * i) / particlesPerRing;

        Vector3D velocity = new Vector3D(
            Math.cos(angle) * ringRadius,
            0.5 + random.nextDouble() * 0.5,
            Math.sin(angle) * ringRadius);

        double life = 1.8 + random.nextDouble() * 0.8;
        particles.add(new Particle(origin, velocity, randomizeColor(color), life));
      }
    }

    return particles;
  }
}
