package org.pwrup.util;

import org.pwrup.motor.WheelMover;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Config {
    private final IPublisher coms;
    private final Wheel[] wheels;
}
