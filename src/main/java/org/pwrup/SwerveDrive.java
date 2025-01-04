package org.pwrup;

import java.util.ArrayList;
import java.util.List;

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

    public void drive(Vec2 velocity, double gyroAngle, double rotationCoefficient, double finalSpeedMultiplier) {
        drive(velocity.rotate(-gyroAngle), rotationCoefficient, finalSpeedMultiplier);
    }

    public void drive(Vec2 velocity, double rotationCoefficient, double finalSpeedMultiplier) {
        List<Vec2> wheelTurnVectors = new ArrayList<>();
        double maxModulo = 1.0;
        for (Wheel wheel : config.getWheels()) {
            // Get orthogonal vector and then scale it's x, y such that the modulo is
            // rotationCoefficient
            Vec2 scaled = wheel.getNormal().scaleToModulo(rotationCoefficient);

            // Add the scaled vector to the direction vector to get the wheel turn vector
            Vec2 wheelTurnVector = velocity.add(scaled);
            wheelTurnVectors.add(wheelTurnVector);

            maxModulo = Math.max(maxModulo, wheelTurnVector.getModulo());
        }

        for (int i = 0; i < wheelTurnVectors.size(); i++) {
            Vec2 wrapped = wheelTurnVectors.get(i).wrapInto1(maxModulo);
            wheelTurnVectors.set(i, wrapped);
            config.getWheels()[i].getMover().drive(wrapped, finalSpeedMultiplier);
        }

        config.getComs().publish("robot_wheel_positions", wheelTurnVectors.toArray(), Vec2[].class);
    }

    public void driveTowards(Vec2 target, double speed, double rotationCoefficient) {
        drive(target.scaleToModulo(speed), rotationCoefficient, 1.0);
    }
}
