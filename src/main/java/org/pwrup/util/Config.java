package org.pwrup.util;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.pwrup.motor.WheelMover;

@AllArgsConstructor
@Getter
public class Config {

  private final Optional<IPublisher> coms;
  private final Wheel[] wheels;
}
