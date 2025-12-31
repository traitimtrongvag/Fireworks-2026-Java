package org.example.fireworks;

import org.example.physics.Particle;
import org.example.physics.Vector3D;
import org.example.visual.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class FireworkType {
  protected final Random random;
  protected final Vector3D origin;
  protected final Color color;

  public FireworkType(Vector3D origin, Color color) {
    this.random = new Random();
    this.origin = origin;
    this.color = color;
  }

  public abstract List<Particle> explode();

  protected Color randomizeColor(Color base) {
    int variance = 30;
    return new Color(
        base.r + random.nextInt(variance) - variance / 2,
        base.g + random.nextInt(variance) - variance / 2,
        base.b + random.nextInt(variance) - variance / 2);
  }
}
