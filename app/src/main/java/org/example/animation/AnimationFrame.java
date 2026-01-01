package org.example.animation;

public class AnimationFrame {
  private final String[] lines;
  private final int width;
  private final int height;

  public AnimationFrame(String[] lines) {
    this.lines = lines;
    this.height = lines.length;
    this.width = lines.length > 0 ? lines[0].length() : 0;
  }

  public String[] getLines() {
    return lines;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
