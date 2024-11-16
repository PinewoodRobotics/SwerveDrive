package org.pwrup;

import org.pwrup.util.Config;
import org.pwrup.util.Vec2;

/**
 * This class implements the logic for controlling a swerve drive robot.
 * It allows the robot to move towards a specific target while accounting for
 * rotation and speed constraints.
 */
public class SwerveDrive {

    private final Config config;

    /**
     * Constructor for SwerveDrive.
     *
     * @param config Configuration object containing the robot's wheel positions,
     *               motor movers, and communication interface.
     */
    public SwerveDrive(Config config) {
        this.config = config;
    }

    /**
     * Simplified method to drive the robot toward a target position with a given
     * rotation coefficient and speed.
     * This method uses default scaling of the final speed multiplier (set to 1).
     *
     * @param target              The target position vector (in meters).
     * @param rotationCoefficient A coefficient (-1 to 1) that determines the
     *                            rotational impact.
     * @param speed               The desired speed of movement toward the target
     *                            (in m/s).
     */
    public void driveTowards(Vec2 target, double rotationCoefficient, double speed) {
        driveTowards(target, Vec2.ZERO, rotationCoefficient, speed, 1);
    }

    /**
     * Simplified method to drive the robot toward a target position with a given
     * rotation coefficient and speed.
     * This method uses default scaling of the final speed multiplier (set to 1).
     *
     * @param target              The target position vector (in meters).
     * @param planeVector         The plane reference vector (e.g., current
     *                            orientation or zero vector).
     * @param rotationCoefficient A coefficient (-1 to 1) that determines the
     *                            rotational impact.
     * @param speed               The desired speed of movement toward the target
     *                            (in m/s).
     */
    public void driveTowards(Vec2 target, Vec2 planeVector, double rotationCoefficient, double speed) {
        driveTowards(target, planeVector, rotationCoefficient, speed, 1);
    }

    /**
     * Drives the robot towards a target position while considering the current
     * position, speed,
     * rotation coefficient, and a scaling multiplier for the final speed.
     *
     * @param targetPosition       The target position vector (in meters).
     * @param currentPosition      The current position vector of the robot (in
     *                             meters).
     * @param speed                The desired speed of movement toward the target
     *                             (in m/s).
     * @param rotationCoefficient  A coefficient (-1 to 1) that determines the
     *                             rotational impact.
     * @param finalSpeedMultiplier A multiplier for scaling the final speed output
     *                             (default: 1).
     */
    public void driveTowards(Vec2 targetPosition, Vec2 currentPosition, double speed,
            double rotationCoefficient, double finalSpeedMultiplier) {
        // Step 1: Calculate the direction vector
        // The direction vector represents the difference between the target and current
        // positions.
        // It determines where the robot needs to move.
        Vec2 direction = targetPosition.subtract(currentPosition);

        // Step 2: Normalize the direction vector and scale it by the speed
        // This creates a velocity vector pointing toward the target at the desired
        // speed.
        Vec2 velocity = direction.normalize().scale(speed);

        // Step 3: Initialize an array to hold the relative velocities for each wheel
        // We also track the maximum speed magnitude to normalize outputs later.
        Vec2[] wheelRelativePositions = new Vec2[4];
        double maxModulo = 1.0; // Used to scale speeds so that none exceed the maximum value.

        // Step 4: Calculate the velocity for each wheel
        for (int i = 0; i < config.getWheelPositions().length; i++) {
            // Retrieve the position of the wheel relative to the robot's center.
            Vec2 wheelPosition = config.getWheelPositions()[i];

            // Step 4.1: Calculate the rotation vector for this wheel
            // Rotation is determined by rotating the wheel position vector 90 degrees
            // and scaling it by the rotation coefficient.
            Vec2 rotationVector = new Vec2(-wheelPosition.getY(), wheelPosition.getX());
            rotationVector.scale(rotationCoefficient);

            // Step 4.2: Combine the velocity vector with the rotation vector
            // This determines the total movement vector for this wheel.
            Vec2 wheelVector = velocity.add(rotationVector);

            // Step 4.3: Store the calculated vector and update the maximum magnitude if
            // necessary
            wheelRelativePositions[i] = wheelVector;
            if (wheelVector.getModulo() > maxModulo) {
                maxModulo = wheelVector.getModulo();
            }
        }

        // Step 5: Normalize the velocities for each wheel
        // If any wheel exceeds the maximum allowed speed, scale all velocities
        // proportionally.
        for (int i = 0; i < wheelRelativePositions.length; i++) {
            Vec2 wheelVector = wheelRelativePositions[i];
            wheelVector.scale(wheelVector.getModulo() / maxModulo);

            // Step 5.1: Send the speed and angle commands to the motor controllers
            // The motor movers interpret the magnitude (speed) and angle for each wheel.
            config.getMotorMovers()[i].drive(
                    wheelVector.getModulo(), // Magnitude (speed)
                    wheelVector.getAngle(), // Angle (direction)
                    finalSpeedMultiplier // Speed scaling multiplier
            );
        }

        // Step 6: Publish the wheel positions to the communication interface
        // This can be used for monitoring or debugging purposes.
        config.getComs().publish("wheel/positions", wheelRelativePositions, Vec2[].class);
    }
}