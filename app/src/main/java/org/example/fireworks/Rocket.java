package org.example.fireworks;

import org.example.physics.Particle;
import org.example.physics.Vector3D;
import org.example.visual.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rocket {
  private Vector3D position;
  private Vector3D velocity;
  private final Color trailColor;
  private final double targetHeight;
  private final FireworkType fireworkType;
  private final List<Particle> trail;
  private boolean exploded;

  public Rocket(double startX, double startZ, Color color, FireworkType fireworkType) {
    Random random = new Random();
    this.position = new Vector3D(startX, 0, startZ);
    this.velocity = new Vector3D(
        (random.nextDouble() - 0.5) * 0.3,
        4.5 + random.nextDouble() * 1.5,
        (random.nextDouble() - 0.5) * 0.3);
    this.trailColor = color;
    this.targetHeight = 18 + random.nextDouble() * 8;
    this.fireworkType = fireworkType;
    this.trail = new ArrayList<>();
    this.exploded = false;
  }

  public void update(double deltaTime) {
    if (exploded)
      return;

    velocity = new Vector3D(
        velocity.x * 0.99,
        velocity.y - 0.15 * deltaTime,
        velocity.z * 0.99);

    position = position.add(velocity.multiply(deltaTime));

    if (trail.size() < 5) {
      trail.add(new Particle(
          position,
          new Vector3D(0, 0, 0),
          trailColor,
          0.5));
    }
  }

  public boolean shouldExplode() {
    return !exploded && (position.y >= targetHeight || velocity.y <= 0);
  }

  public List<Particle> explode() {
    exploded = true;
    return fireworkType.explode();
  }

  public Vector3D getPosition() {
    return position;
  }

  public List<Particle> getTrail() {
    return trail;
  }

  public boolean isExploded() {
    return exploded;
  }
}
