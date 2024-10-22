package org.pwrup.motor;

import org.pwrup.error.RelativeEncoderError;

public interface IRelativeEncoder {
    void setPosition(double position) throws RelativeEncoderError;

    void setPositionConversionFactor(double factor) throws RelativeEncoderError;

    double getPosition();

}
