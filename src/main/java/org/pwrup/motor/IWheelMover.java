package org.pwrup.motor;

import org.pwrup.util.Vec2;

public interface IWheelMover {
    /**
     * @param vector               The vector to drive the wheel towards.
     * @param finalSpeedMultiplier The multiplier to the speed of the wheel. This
     *                             is used to scale the speed of the wheel to
     *                             the max speed of the robot.
     */
    void drive(Vec2 vector, double finalSpeedMultiplier);
}
