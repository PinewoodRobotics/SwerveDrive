package org.pwrup;

import java.util.ArrayList;
import java.util.List;

import org.pwrup.util.Config;
import org.pwrup.util.Vec2;
import org.pwrup.util.Wheel;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;

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

  public void driveNonRelative(ChassisSpeeds speeds) {
    driveNonRelative(new Vec2(speeds.vxMetersPerSecond, speeds.vyMetersPerSecond), speeds.omegaRadiansPerSecond);
  }

  public void driveNonRelative(
      Vec2 linearVelocityMpsRobot,
      double omegaRadPerSec) {
    driveNonRelative(linearVelocityMpsRobot, omegaRadPerSec, 1.0);
  }

  public void driveNonRelative(
      Vec2 linearVelocityMpsRobot,
      double omegaRadPerSec,
      double finalSpeedMultiplier) {
    drive0(linearVelocityMpsRobot, omegaRadPerSec, finalSpeedMultiplier);
  }

  public void driveWithGyro(ChassisSpeeds speeds, Rotation2d gyroAngle) {
    driveWithGyro(new Vec2(speeds.vxMetersPerSecond, speeds.vyMetersPerSecond), gyroAngle.getRadians(),
        speeds.omegaRadiansPerSecond);
  }

  public void driveWithGyro(
      Vec2 linearVelocityMpsRobot,
      double gyroAngleRad,
      double omegaRadPerSec) {
    driveWithGyro(linearVelocityMpsRobot, gyroAngleRad, omegaRadPerSec, 1.0);
  }

  public void driveWithGyro(
      Vec2 linearVelocityMpsRobot,
      double gyroAngleRad,
      double omegaRadPerSec,
      double finalSpeedMultiplier) {
    Vec2 rotatedLinearVelocityMpsRobot = linearVelocityMpsRobot.rotate(-gyroAngleRad);

    drive0(
        rotatedLinearVelocityMpsRobot,
        omegaRadPerSec,
        finalSpeedMultiplier);
  }

  public void drive0(
      Vec2 linearVelocityMpsRobot,
      double omegaRadPerSec,
      double finalSpeedMultiplier) {
    List<Vec2> wheelTurnVectors = new ArrayList<>();
    for (Wheel wheel : config.getWheels()) {
      // Get orthogonal vector and then scale it's x, y such that the modulo is
      // rotationCoefficient
      Vec2 rotationVector = wheel
          .getNormal()
          .scaleToModulo(omegaRadPerSec);

      // Add the scaled vector to the direction vector to get the wheel turn vector
      Vec2 wheelVector = linearVelocityMpsRobot.add(rotationVector);
      wheel.getMover().drive(wheelVector, finalSpeedMultiplier);
      wheelTurnVectors.add(wheelVector);
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
}
