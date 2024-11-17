package org.pwrup;

import org.pwrup.util.Config;
import org.pwrup.util.Vec2;
import org.pwrup.util.Wheel;

/**
 * This class implements the logic for controlling a swerve drive robot.
 * It allows the robot to move towards a specific target while accounting for
 * rotation and speed constraints.
 */
public class SwerveDrive {
    private final Config config;

    public SwerveDrive(Config config) {
        this.config = config;
    }

    public void testDrive(Vec2 velocity, double rotationCoefficient, double finalSpeedMultiplier) {
        for (Wheel wheel : config.getWheels()) {
            // Get orthogonal vector and then scale it's x, y such that the modulo is
            // rotationCoefficient
            Vec2 scaled = wheel.getNormal().scaleToModulo(rotationCoefficient);

            // Add the scaled vector to the direction vector to get the wheel turn vector
            Vec2 wheelTurnVector = velocity.add(scaled);
            wheel.getMover().drive(wheelTurnVector, finalSpeedMultiplier);
        }
    }
}