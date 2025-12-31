package org.example.physics;

import org.example.visual.Color;

public class Particle {
  private Vector3D position;
  private Vector3D velocity;
  private final Color color;
  private double life;
  private final double maxLife;
  private final double gravity;
  private final double drag;

  public Particle(Vector3D position, Vector3D velocity, Color color, double life) {
    this.position = position;
    this.velocity = velocity;
    this.color = color;
    this.life = life;
    this.maxLife = life;
    this.gravity = 0.08;
    this.drag = 0.985;
  }

  public void update(double deltaTime) {
    velocity = new Vector3D(
        velocity.x * drag,
        velocity.y - gravity * deltaTime,
        velocity.z * drag);

    position = position.add(velocity.multiply(deltaTime));
    life -= deltaTime;
  }

  public boolean isDead() {
    return life <= 0;
  }

  public Vector3D getPosition() {
    return position;
  }

  public Color getColor() {
    return color;
  }

  public double getBrightness() {
    return Math.max(0, Math.min(1, life / maxLife));
  }
}
