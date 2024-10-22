package org.pwrup.motor;

import java.util.Optional;

import org.pwrup.error.PidSetError;
import org.pwrup.util.ControlType;

public interface IPIDController {
    void setP(double p) throws PidSetError;

    void setI(double i) throws PidSetError;

    void setD(double d) throws PidSetError;

    void setIZone(double iZone) throws PidSetError;

    void setFF(double gain) throws PidSetError;

    void setPositionPIDWrappingEnabled(boolean isEnabled) throws PidSetError;

    void setPositionPIDWrappingMinInput(double min) throws PidSetError;

    void setPositionPIDWrappingMaxInput(double max) throws PidSetError;

    void setOutputRange(double min, double max) throws PidSetError;

    void setReference(double value, int type) throws PidSetError;

    default void setReference(double value, ControlType type) throws PidSetError {
        setReference(value, type.value);
    }
}
