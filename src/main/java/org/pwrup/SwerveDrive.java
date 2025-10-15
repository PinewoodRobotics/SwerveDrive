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

  public void drive(
      Vec2 velocity,
      double gyroAngle,
      double rotationCoefficient,
      double finalSpeedMultiplier) {
    drive(
        velocity.rotate(-gyroAngle),
        rotationCoefficient,
        finalSpeedMultiplier);
  }

  public void driveNoMaxModulo(
      Vec2 velocity,
      double gyroAngle,
      double rotationCoefficient,
      double finalSpeedMultiplier) {
    driveNoMaxModulo(velocity.rotate(-gyroAngle), rotationCoefficient, finalSpeedMultiplier);
  }

  public void driveNoMaxModulo(
      Vec2 xyVector,
      double rotationCoefficient,
      double finalSpeedMultiplier) {
    List<Vec2> wheelTurnVectors = new ArrayList<>();
    for (Wheel wheel : config.getWheels()) {
      // Get orthogonal vector and then scale it's x, y such that the modulo is
      // rotationCoefficient
      Vec2 rotationVector = wheel
          .getNormal()
          .scaleToModulo(rotationCoefficient);

      // Add the scaled vector to the direction vector to get the wheel turn vector
      Vec2 wheelVector = xyVector.add(rotationVector);
      wheelTurnVectors.add(wheelVector);
    }

    // No desaturation - drive each wheel with its raw calculated vector
    for (int i = 0; i < wheelTurnVectors.size(); i++) {
      config.getWheels()[i].getMover().drive(wheelTurnVectors.get(i), finalSpeedMultiplier);
    }

    if (config.getComs().isPresent()) {
      config
          .getComs()
          .get()
          .publish(
              "robot_wheel_positions",
              wheelTurnVectors.toArray(),
              Vec2[].class);
    }
  }

  /**
   * Velocity-based API: supply linear chassis velocity (m/s) in field frame and
   * angular velocity (rad/s). XY is rotated into robot frame using gyroAngle.
   */
  public void driveVelocity(
      Vec2 linearVelocityMps,
      double gyroAngle,
      double omegaRadPerSec) {
    driveVelocityRobotRelative(linearVelocityMps.rotate(-gyroAngle), omegaRadPerSec, 1.0);
  }

  /**
   * Velocity-based API in robot frame: supply linear chassis velocity (m/s) and
   * angular velocity (rad/s). finalSpeedMultiplier scales linear speeds (m/s).
   */
  public void driveVelocityRobotRelative(
      Vec2 linearVelocityMpsRobot,
      double omegaRadPerSec,
      double finalSpeedMultiplier) {
    List<Vec2> wheelTurnVectors = new ArrayList<>();
    for (Wheel wheel : config.getWheels()) {
      // Rotational component: v = ω × r = -ω * normal (normal has |r| magnitude)
      Vec2 normal = wheel.getNormal();
      Vec2 rotationVector = normal.scale(-omegaRadPerSec);
      Vec2 wheelVector = linearVelocityMpsRobot.add(rotationVector);
      wheelTurnVectors.add(wheelVector);
    }

    for (int i = 0; i < wheelTurnVectors.size(); i++) {
      config.getWheels()[i].getMover().drive(wheelTurnVectors.get(i), finalSpeedMultiplier);
    }

    if (config.getComs().isPresent()) {
      config
          .getComs()
          .get()
          .publish(
              "robot_wheel_positions",
              wheelTurnVectors.toArray(),
              Vec2[].class);
    }
  }

  public void drive(
      Vec2 xyVector,
      double rotationCoefficient,
      double finalSpeedMultiplier) {
    List<Vec2> wheelTurnVectors = new ArrayList<>();
    double maxModulo = 1.0;
    for (Wheel wheel : config.getWheels()) {
      // Get orthogonal vector and then scale it's x, y such that the modulo is
      // rotationCoefficient
      Vec2 rotationVector = wheel
          .getNormal()
          .scaleToModulo(rotationCoefficient);

      // Add the scaled vector to the direction vector to get the wheel turn vector
      Vec2 wheelVector = xyVector.add(rotationVector);
      wheelTurnVectors.add(wheelVector);

      maxModulo = Math.max(maxModulo, wheelVector.getModulo());
    }

    for (int i = 0; i < wheelTurnVectors.size(); i++) {
      Vec2 wrapped = wheelTurnVectors.get(i).desaturate(maxModulo);
      wheelTurnVectors.set(i, wrapped);
      config.getWheels()[i].getMover().drive(wrapped, finalSpeedMultiplier);
    }

    if (config.getComs().isPresent()) {
      config
          .getComs()
          .get()
          .publish(
              "robot_wheel_positions",
              wheelTurnVectors.toArray(),
              Vec2[].class);
    }
  }

  public void driveTowards(
      Vec2 target,
      double speed,
      double rotationCoefficient) {
    drive(target.scaleToModulo(speed), rotationCoefficient, 1.0);
  }
}
