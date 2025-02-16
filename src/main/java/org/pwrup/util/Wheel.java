package org.pwrup.util;

import edu.wpi.first.math.geometry.Translation2d;
import lombok.Getter;
import org.pwrup.motor.WheelMover;

@Getter
public class Wheel {

  private final Vec2 position, normal;
  private final WheelMover mover;

  public Wheel(Vec2 position, WheelMover mover) {
    this.position = position;
    this.mover = mover;
    this.normal = new Vec2(position.getY(), -position.getX());
  }

  public Wheel(Translation2d position, WheelMover mover) {
    this.position = new Vec2(position.getX(), position.getY());
    this.mover = mover;
    this.normal = new Vec2(position.getY(), -position.getX());
  }

  public enum WheelPosition {
    FRONT_LEFT(-1, 1),
    FRONT_RIGHT(-1, -1),
    REAR_LEFT(1, 1),
    REAR_RIGHT(1, -1);

    private final double x, y;
    private WheelPosition(double x, double y) {
      this.x = x;
      this.y = y;
    }

    public Vec2 getPosition(double scale) {
      return new Vec2(this.x * scale, this.y * scale);
    }
  }

  public static Wheel fromFrameSize(float squareSideSize, WheelPosition pos, WheelMover mover) {
    return new Wheel(pos.getPosition(squareSideSize), mover);
  }
}
