# SwerveDrive Library

A Java library for controlling swerve drive robots, particularly designed for FRC (FIRST Robotics Competition) robots. This library provides precise control over wheel movements, handling both translation and rotation simultaneously.

## Overview

Swerve drive is a sophisticated drive system that allows robots to move in any direction while simultaneously rotating. Each wheel can pivot independently and control its speed, providing exceptional maneuverability.

## Features

- Field-oriented driving support
- Independent wheel control
- Vector-based movement calculations
- Built-in optimization for wheel angles
- Support for custom communication interfaces
- Automatic wheel speed normalization

## Installation

Add these dependencies to your `build.gradle`:

```gradle
dependencies {
    implementation 'edu.wpi.first.wpilibj:wpilibj-java:2024.3.2'
    implementation 'edu.wpi.first.ntcore:ntcore-java:2024.3.2'
    compileOnly 'org.projectlombok:lombok:1.18.30'
    annotationProcessor 'org.projectlombok:lombok:1.18.30'
}
```

## Usage

### 1. Configure Your Wheels

First, create your wheel configuration:

```java
// Create wheel movers (implement the WheelMover abstract class for your hardware)
WheelMover frontLeft = new YourWheelMover();
WheelMover frontRight = new YourWheelMover();
WheelMover backLeft = new YourWheelMover();
WheelMover backRight = new YourWheelMover();
// Define wheel positions relative to robot center
Wheel[] wheels = new Wheel[] {
    new Wheel(new Vec2(1, 1), frontLeft, 1), // Front Left
    new Wheel(new Vec2(-1, 1), frontRight, 1), // Front Right
    new Wheel(new Vec2(-1, -1), backLeft, 1), // Back Left
    new Wheel(new Vec2(1, -1), backRight, 1) // Back Right
};
```

### 2. Create SwerveDrive Instance

```java
// Create your publisher implementation
IPublisher publisher = new YourPublisher();
// Create config
Config config = new Config(publisher, wheels);
// Initialize SwerveDrive
SwerveDrive drive = new SwerveDrive(config);
```

### 3. Control Your Robot

```java
// Drive with velocity and rotation
Vec2 velocity = new Vec2(1.0, 0.0); // Move forward
double rotationCoefficient = 0.5; // Rotate at half speed
double speedMultiplier = 1.0; // Full speed
drive.drive(velocity, rotationCoefficient, speedMultiplier);
// For field-oriented drive, include gyro angle
double gyroAngle = yourGyro.getAngle();
drive.drive(velocity, gyroAngle, rotationCoefficient, speedMultiplier);
// Drive towards a specific point
Vec2 target = new Vec2(5.0, 3.0);
double speed = 0.8;
drive.driveTowards(target, speed, rotationCoefficient);
```

## Key Components

### Vec2

A 2D vector class that handles vector operations including:

- Vector addition/subtraction
- Rotation
- Normalization
- Polar coordinate conversion

### WheelMover

Abstract class for controlling individual wheels. Implement this for your specific hardware:

```java
public abstract class WheelMover {
    public abstract void drive(double angle, double speed);
    public abstract double getCurrentAngle();
}
```

### IPublisher

Interface for publishing robot data:

```java
public interface IPublisher {
    void publish(String topic, Object data, Class<?> type);
}
```

## Contributing

Contributions are welcome! Please feel free to submit pull requests.

## License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.