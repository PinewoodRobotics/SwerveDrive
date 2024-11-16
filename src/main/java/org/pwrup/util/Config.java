package org.pwrup.util;

import org.pwrup.motor.IWheelMover;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Config {
    private final IPublisher coms;

    private final IWheelMover[] motorMovers;
    private final Vec2[] wheelPositions;
    private final double wheelRadius;
}
