package com.sandbox;

import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.math.Vec3d;

public class Line {
  public static Vector4f RED = new Vector4f(1, 0, 0, 1);
  public static Vector4f GREEN = new Vector4f(0, 1, 0, 1);
  public static Vector4f BLUE = new Vector4f(0, 0, 1, 1);

  private final Vec3d start;
  private final Vec3d end;
  private final Vector4f color;

  public Line(Vec3d start, Vec3d end) {
    this.start = start;
    this.end = end;
    this.color = new Vector4f(0, 0, 0, 0);
  }

  public Line(Vec3d start, Vec3d end, Vector4f color) {
    this.start = start;
    this.end = end;
    this.color = color;
  }

  public Vec3d getStart() {
    return start;
  }

  public Vec3d getEnd() {
    return end;
  }

  public Vector4f getColor() {
    return color;
  }
}
