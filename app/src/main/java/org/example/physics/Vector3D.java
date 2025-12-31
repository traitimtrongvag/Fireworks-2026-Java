package org.example.physics;

public class Vector3D {
  public double x;
  public double y;
  public double z;

  public Vector3D(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Vector3D add(Vector3D other) {
    return new Vector3D(x + other.x, y + other.y, z + other.z);
  }

  public Vector3D multiply(double scalar) {
    return new Vector3D(x * scalar, y * scalar, z * scalar);
  }

  public double magnitude() {
    return Math.sqrt(x * x + y * y + z * z);
  }

  public Vector3D normalize() {
    double mag = magnitude();
    if (mag == 0)
      return new Vector3D(0, 0, 0);
    return new Vector3D(x / mag, y / mag, z / mag);
  }
}
