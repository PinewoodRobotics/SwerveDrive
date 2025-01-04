package org.pwrup.util;

import org.pwrup.motor.WheelMover;

import lombok.Getter;

@Getter
public class Wheel {
    private final Vec2 position, normal;
    private final WheelMover mover;
    private final double radius;

    public Wheel(Vec2 position, WheelMover mover, double radius) {
        this.position = position;
        this.mover = mover;
        this.radius = radius;
        this.normal = new Vec2(-position.getY(), position.getX());
    }
}
