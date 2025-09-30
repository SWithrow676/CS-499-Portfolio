#include "Door.h"

/**
 * @brief Constructor that starts with the door open.
 */
Door::Door() : open(true), angle(DOOR_ANGLE_OPEN), targetAngle(DOOR_ANGLE_OPEN) {}

/**
 * @brief Toggle the door's open/closed state and set the target angle accordingly.
 */
void Door::toggle()
{
    open = !open;

    if (open)
        targetAngle = DOOR_ANGLE_OPEN;
    else
        targetAngle = DOOR_ANGLE_CLOSED;
}

/**
 * @brief Animate the door angle toward the target angle.
 */
void Door::update()
{
    if (angle != targetAngle)
    {
        if (angle < targetAngle)
        {
            angle += DOOR_SPEED;
            if (angle > targetAngle)
            {
                angle = targetAngle;
            }
        }
        else
        {
            angle -= DOOR_SPEED;
            if (angle < targetAngle)
            {
                angle = targetAngle;
            }
        }
    }
}

/**
 * @brief Returns the current angle of the door (in degrees).
 */
double Door::getAngle() const {
    return angle;
}

/**
 * @brief Returns true if the door is open.
 */
bool Door::isOpen() const {
    return open;
}