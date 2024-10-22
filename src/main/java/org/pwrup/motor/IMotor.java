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
     * @throws ResetDefaultError if error
     */
    void restoreFactoryDefaults() throws ResetDefaultError;

    /**
     * @param limit in Amperes
     * @throws SetMaxCurrentError if error
     */
    void setSmartCurrentLimit(int limit) throws SetMaxCurrentError;

    /**
     * @param isInverted The state of inversion, true is inverted.
     */
    void setInverted(boolean isInverted);

    /**
     * @return the PID controller
     */
    IPIDController getPIDController();
}
