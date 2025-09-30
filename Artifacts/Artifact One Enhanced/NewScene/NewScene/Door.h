#pragma once

// Represents an animated door that can open and close.
class Door {
public:
    Door();

    // Toggle between open and closed state.
    void toggle();
    // Animate the door toward its target angle.
    void update();

    // Get the current angle in degrees.
    double getAngle() const;
    // Returns true if the door is open.
    bool isOpen() const;

private:
    static constexpr double DOOR_ANGLE_OPEN = 90.0;
    static constexpr double DOOR_ANGLE_CLOSED = 0.0;
    static constexpr double DOOR_SPEED = 1.0;

	bool open;          // True if the door is open, false if closed.
	double angle;       // Current angle of the door in degrees.
	double targetAngle; // Target angle to animate toward.
};