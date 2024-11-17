package org.pwrup.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class Vec2 {
    public static final Vec2 ZERO = new Vec2(0, 0);

    private double x; // x coordinate
    private double y; // y coordinate

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vec2 fromPolar(double modulo, double angle) {
        return new Vec2(modulo * Math.cos(angle), modulo * Math.sin(angle));
    }

    public double getModulo() {
        return Math.sqrt(x * x + y * y);
    }

    public double getAngle() {
        return Math.atan2(y, x);
    }

    public Vec2 scaleToModulo(double newModulo) {
        double scale = newModulo / getModulo();
        double newX = x * scale;
        double newY = y * scale;
        return new Vec2(newX, newY);
    }

    public Vec2 add(Vec2 other) {
        return new Vec2(x + other.x, y + other.y);
    }

    public Vec2 subtract(Vec2 other) {
        return new Vec2(x - other.x, y - other.y);
    }

    public Vec2 normalize() {
        double modulo = getModulo();
        return new Vec2(x / modulo, y / modulo);
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
