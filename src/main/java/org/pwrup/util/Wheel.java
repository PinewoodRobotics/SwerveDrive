package org.pwrup.util;

import org.pwrup.motor.IWheelMover;

import lombok.Getter;

@Getter
public class Wheel {
    private final Vec2 position, normal;
    private final IWheelMover mover;
    private final double radius;

    public Wheel(Vec2 position, IWheelMover mover, double radius) {
        this.position = position;
        this.mover = mover;
        this.radius = radius;
        this.normal = new Vec2(-position.getY(), position.getX());
    }
}
