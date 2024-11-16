package org.pwrup.motor;

public interface IWheelMover {
    /**
     * @param speed                The speed of the wheel. In math terms, the modulo
     *                             of the wheel.
     * @param angle                The angle of the wheel. In radians where 2pi =
     *                             360deg
     * @param finalSpeedMultiplier The multiplier to the speed of the wheel. This
     *                             is used to scale the speed of the wheel to
     *                             the max speed of the robot.
     */
    void drive(double speed, double angle, double finalSpeedMultiplier);
}
