package org.pwrup.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class Vec2 {
    public static final Vec2 ZERO = new Vec2(0, 0);

    private double x; // x coordinate
    private double y; // y coordinate
    private double angle; // angle in radians
    private double modulo; // magnitude

    public Vec2(double x, double y) {
        this(x, y, Math.sqrt(x * x + y * y), Math.atan2(y, x));
    }

    public Vec2(double x, double y, double modulo, double angle) {
        this.x = x;
        this.y = y;
        this.modulo = modulo;
        this.angle = angle;
    }

    public static Vec2 fromPolar(double modulo, double angle) {
        return new Vec2(modulo * Math.cos(angle), modulo * Math.sin(angle), modulo, angle);
    }

    public Vec2 angleDifference(Vec2 other, double modulo) {
        return fromPolar(modulo, angle - other.angle);
    }

    public Vec2 scale(double amt) {
        return new Vec2(x * amt, y * amt, modulo * amt, angle);
    }

    public Vec2 add(Vec2 other) {
        return new Vec2(x + other.x, y + other.y);
    }

    public Vec2 subtract(Vec2 other) {
        return new Vec2(x - other.x, y - other.y);
    }

    public Vec2 normalize() {
        return new Vec2(x / modulo, y / modulo, 1, angle);
    }

    public static double largestModulo(Vec2[] vectors) {
        double max = 0;
        for (Vec2 vector : vectors) {
            if (vector.getModulo() > max) {
                max = vector.getModulo();
            }
        }

        return max;
    }
}
