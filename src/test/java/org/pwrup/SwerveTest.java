package org.pwrup;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.pwrup.motor.WheelMover;
import org.pwrup.util.Config;
import org.pwrup.util.IPublisher;
import org.pwrup.util.Vec2;
import org.pwrup.util.Wheel;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class SwerveTest {
    @Test
    public void testDriveWithNoRotationOrVelocity() {
        WheelMover mover = mock(WheelMover.class);
        IPublisher publisher = mock(IPublisher.class);

        Wheel[] wheels = new Wheel[] {
                new Wheel(new Vec2(1, 1), mover, 1),
                new Wheel(new Vec2(-1, 1), mover, 1),
                new Wheel(new Vec2(-1, -1), mover, 1),
                new Wheel(new Vec2(1, -1), mover, 1)
        };

        Config config = new Config(publisher, wheels);
        SwerveDrive drive = new SwerveDrive(config);

        drive.drive(new Vec2(0, 0), 0, 1.0);

        verify(mover, times(4)).drive(any(Vec2.class), eq(1.0));
        verify(publisher).publish(eq("robot_wheel_positions"), any(), eq(Vec2[].class));
    }

    @Test
    public void testDriveWithOnlyVelocity() {
        WheelMover mover = mock(WheelMover.class);
        IPublisher publisher = mock(IPublisher.class);

        Wheel[] wheels = new Wheel[] {
                new Wheel(new Vec2(1, 1), mover, 1),
                new Wheel(new Vec2(-1, 1), mover, 1),
                new Wheel(new Vec2(-1, -1), mover, 1),
                new Wheel(new Vec2(1, -1), mover, 1)
        };

        Config config = new Config(publisher, wheels);
        SwerveDrive drive = new SwerveDrive(config);

        drive.drive(new Vec2(1, 0), 0, 1.0);

        verify(mover, times(4)).drive(any(Vec2.class), eq(1.0));
        verify(publisher).publish(eq("robot_wheel_positions"), any(), eq(Vec2[].class));
    }

    @Test
    public void testDriveWithOnlyRotation() {
        WheelMover mover = mock(WheelMover.class);
        IPublisher publisher = mock(IPublisher.class);

        Wheel[] wheels = new Wheel[] {
                new Wheel(new Vec2(1, 1), mover, 1),
                new Wheel(new Vec2(-1, 1), mover, 1),
                new Wheel(new Vec2(-1, -1), mover, 1),
                new Wheel(new Vec2(1, -1), mover, 1)
        };

        Config config = new Config(publisher, wheels);
        SwerveDrive drive = new SwerveDrive(config);

        drive.drive(new Vec2(0, 0), 1.0, 1.0);

        verify(mover, times(4)).drive(any(Vec2.class), eq(1.0));
        verify(publisher).publish(eq("robot_wheel_positions"), any(), eq(Vec2[].class));
    }

    @Test
    public void testDriveWithVelocityAndRotation() {
        WheelMover mover = mock(WheelMover.class);
        IPublisher publisher = mock(IPublisher.class);

        Wheel[] wheels = new Wheel[] {
                new Wheel(new Vec2(1, 1), mover, 1),
                new Wheel(new Vec2(-1, 1), mover, 1),
                new Wheel(new Vec2(-1, -1), mover, 1),
                new Wheel(new Vec2(1, -1), mover, 1)
        };

        Config config = new Config(publisher, wheels);
        SwerveDrive drive = new SwerveDrive(config);

        drive.drive(new Vec2(1, 1), 0.5, 1.0);

        verify(mover, times(4)).drive(any(Vec2.class), eq(1.0));
        verify(publisher).publish(eq("robot_wheel_positions"), any(), eq(Vec2[].class));
    }

    @Test
    public void testDriveWithDifferentSpeedMultiplier() {
        WheelMover mover = mock(WheelMover.class);
        IPublisher publisher = mock(IPublisher.class);

        Wheel[] wheels = new Wheel[] {
                new Wheel(new Vec2(1, 1), mover, 1),
                new Wheel(new Vec2(-1, 1), mover, 1),
                new Wheel(new Vec2(-1, -1), mover, 1),
                new Wheel(new Vec2(1, -1), mover, 1)
        };

        Config config = new Config(publisher, wheels);
        SwerveDrive drive = new SwerveDrive(config);

        drive.drive(new Vec2(1, 1), 0.5, 0.5);

        verify(mover, times(4)).drive(any(Vec2.class), eq(0.5));
        verify(publisher).publish(eq("robot_wheel_positions"), any(), eq(Vec2[].class));
    }
}