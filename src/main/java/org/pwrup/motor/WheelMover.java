package org.pwrup.motor;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import org.pwrup.util.Vec2;

public abstract class WheelMover {
  private static final double kZeroVectorEpsilon = 1e-9;

  public double[] optimizeVector(Vec2 vector) {
    double speed = vector.getModulo();
    Rotation2d currentAngle = Rotation2d.fromRadians(getCurrentAngle());

    // With no requested wheel motion, keep the current azimuth instead of
    // snapping to a canonical branch.
    if (speed < kZeroVectorEpsilon) {
      return new double[] { currentAngle.getRadians(), 0.0 };
    }

    SwerveModuleState optimized = SwerveModuleState.optimize(
        new SwerveModuleState(speed, Rotation2d.fromRadians(vector.getAngle())),
        currentAngle);
    return new double[] { optimized.angle.getRadians(), optimized.speedMetersPerSecond };
  }

  /**
   * @param vector               The vector to drive the wheel towards.
   * @param finalSpeedMultiplier The multiplier to the speed of the wheel. This
   *                             is used to scale the speed of the wheel to
   *                             the max speed of the robot.
   */
  public void drive(Vec2 vector, double finalSpeedMultiplier) {
    double[] optimized = optimizeVector(vector);
    // Treat optimized[1] as linear wheel speed in meters/second and scale by
    // multiplier
    drive(optimized[0], optimized[1] * finalSpeedMultiplier);
  }

  public abstract void drive(double angle, double speed);

  public abstract double getCurrentAngle();
}
