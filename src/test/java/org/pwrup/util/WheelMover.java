package org.pwrup.util;

public class WheelMover extends org.pwrup.motor.WheelMover {

    public double currentAngle;

    public WheelMover(double currentAngle) {
        super();
        this.currentAngle = currentAngle;
    }

    @Override
    public void drive(double angle, double speed) {

    }

    @Override
    public double getCurrentAngle() {
        return this.currentAngle;
    }

}
