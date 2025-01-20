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
}
