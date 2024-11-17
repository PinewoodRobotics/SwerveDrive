package org.pwrup.motor;

import org.pwrup.util.Vec2;

public abstract class WheelMover {
    public double[] optimizeVector(Vec2 vector) {
        double angle = vector.getAngle();
        double speed = vector.getModulo();

        // Normalize angles to [-π, π)
        double normalizedAngle = normalizeToMinusPiToPi(angle);
        double currentAngle = normalizeToMinusPiToPi(getCurrentAngle());

        // Calculate the angle difference
        double angleDiff = normalizeToMinusPiToPi(normalizedAngle - currentAngle);

        // If the angle difference is more than 90 degrees, consider reversing direction
        if (Math.abs(angleDiff) > Math.PI / 2) {
            double reversedAngle = normalizeToMinusPiToPi(normalizedAngle + Math.PI);
            double reversedDiff = normalizeToMinusPiToPi(reversedAngle - currentAngle);

            // Reverse if it minimizes the angular difference
            if (Math.abs(reversedDiff) < Math.abs(angleDiff)) {
                normalizedAngle = reversedAngle;
                speed = -speed;
            }
        }

        return new double[] { normalizedAngle, speed };
    }

    private double normalizeToMinusPiToPi(double angle) {
        return ((angle + Math.PI) % (2 * Math.PI)) - Math.PI;
    }

    /**
     * @param vector               The vector to drive the wheel towards.
     * @param finalSpeedMultiplier The multiplier to the speed of the wheel. This
     *                             is used to scale the speed of the wheel to
     *                             the max speed of the robot.
     */
    public void drive(Vec2 vector, double finalSpeedMultiplier) {
        double[] optimized = optimizeVector(vector);
        drive(optimized[0], Math.max(optimized[1] * finalSpeedMultiplier, 1.0));
    }

    public abstract void drive(double angle, double speed);

    public abstract double getCurrentAngle();
}
