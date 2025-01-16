package org.pwrup.util;

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
}
