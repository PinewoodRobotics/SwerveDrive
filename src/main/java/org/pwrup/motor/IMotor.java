package org.pwrup.motor;

import java.util.Optional;

import org.pwrup.error.ResetDefaultError;
import org.pwrup.error.SetMaxCurrentError;

public interface IMotor {
    /**
     * @return the encoder interface with all of it's functions and methods
     */
    IRelativeEncoder getEncoder();

    /**
     * @return error does not have to be present so return Option
     */
    Optional<ResetDefaultError> restoreFactoryDefaults();

    /**
     * @param limit in Amperes
     * @return error does not have to be present so return Option
     */
    Optional<SetMaxCurrentError> setSmartCurrentLimit(int limit);

    /**
     * @param isInverted The state of inversion, true is inverted.
     */
    void setInverted(boolean isInverted);

    /**
     * @return the PID controller
     */
    IPIDController getPIDController();
}
