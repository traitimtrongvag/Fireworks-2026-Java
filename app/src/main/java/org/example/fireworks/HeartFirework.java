package org.example.fireworks;

import org.example.physics.Particle;
import org.example.physics.Vector3D;
import org.example.visual.Color;

import java.util.ArrayList;
import java.util.List;

public class HeartFirework extends FireworkType {

  public HeartFirework(Vector3D origin, Color color) {
    super(origin, color);
  }

  @Override
  public List<Particle> explode() {
    List<Particle> particles = new ArrayList<>();
    int pointCount = 150;

    for (int i = 0; i < pointCount; i++) {
      double t = (2 * Math.PI * i) / pointCount;

      double x = 16 * Math.pow(Math.sin(t), 3);
      double y = 13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t);

      double scale = 0.15;
      Vector3D velocity = new Vector3D(
          x * scale,
          y * scale,
          (random.nextDouble() - 0.5) * 2);

      double life = 2.0 + random.nextDouble() * 0.8;
      Color heartColor = i % 3 == 0 ? Color.PINK : Color.RED;
      particles.add(new Particle(origin, velocity, randomizeColor(heartColor), life));
    }

    return particles;
  }
}
