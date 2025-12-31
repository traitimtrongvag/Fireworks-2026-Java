package org.example.visual;

public class Color {
  public final int r;
  public final int g;
  public final int b;

  public Color(int r, int g, int b) {
    this.r = clamp(r);
    this.g = clamp(g);
    this.b = clamp(b);
  }

  public Color dim(double factor) {
    return new Color(
        (int) (r * factor),
        (int) (g * factor),
        (int) (b * factor));
  }

  public String toAnsiCode() {
    return String.format("\033[38;2;%d;%d;%dm", r, g, b);
  }

  private int clamp(int value) {
    return Math.max(0, Math.min(255, value));
  }

  public static Color RED = new Color(255, 60, 60);
  public static Color ORANGE = new Color(255, 165, 0);
  public static Color YELLOW = new Color(255, 255, 100);
  public static Color GREEN = new Color(60, 255, 60);
  public static Color CYAN = new Color(0, 255, 255);
  public static Color BLUE = new Color(80, 120, 255);
  public static Color PURPLE = new Color(200, 100, 255);
  public static Color PINK = new Color(255, 120, 200);
  public static Color WHITE = new Color(255, 255, 255);
  public static Color GOLD = new Color(255, 215, 0);
}
