package org.example.fireworks;

import org.example.physics.Particle;
import org.example.physics.Vector3D;
import org.example.visual.Color;

import java.util.ArrayList;
import java.util.List;

public class PalmFirework extends FireworkType {

  public PalmFirework(Vector3D origin, Color color) {
    super(origin, color);
  }

  @Override
  public List<Particle> explode() {
    List<Particle> particles = new ArrayList<>();
    int branches = 12;
    int particlesPerBranch = 15;

    for (int i = 0; i < branches; i++) {
      double angle = (2 * Math.PI * i) / branches;
      double branchSpeed = 3.5 + random.nextDouble();

      Vector3D direction = new Vector3D(
          Math.cos(angle),
          0.2 + random.nextDouble() * 0.3,
          Math.sin(angle)).normalize();

      for (int j = 0; j < particlesPerBranch; j++) {
        double particleSpeed = branchSpeed * (0.7 + j * 0.03);
        Vector3D velocity = direction.multiply(particleSpeed);

        double life = 2.5 + random.nextDouble() * 0.5;
        particles.add(new Particle(origin, velocity, randomizeColor(color), life));
      }
    }

    return particles;
  }
}
