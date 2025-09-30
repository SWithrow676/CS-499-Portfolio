#include "Camera.h"

/**
 * @brief Constructs a camera with specified eye, center, and up vectors.
 */
Camera::Camera(double ex, double ey, double ez,
	double cx, double cy, double cz,
	double ux, double uy, double uz)
{
	eye[0] = ex; eye[1] = ey; eye[2] = ez;
	center[0] = cx; center[1] = cy; center[2] = cz;
	up[0] = ux; up[1] = uy; up[2] = uz;

    isJumping = false;
	verticalVelocity = 0.0;
}

//  ===== Movement methods =====

/**
 * @brief Move the camera forward in the look direction.
 * @param moveSpeed: Camera movement speed.
 */
void Camera::forward(double moveSpeed)
{
    double look[3];

    lookDirection(look);

    normalize(look);
    eye[0] += look[0] * moveSpeed;
    eye[1] += look[1] * moveSpeed;
    eye[2] += look[2] * moveSpeed;

    roomBoundaries();

    updateCenter(look);
}

/**
 * @brief Move the camera backward.
 * @param moveSpeed: Camera movement speed.
 */
void Camera::backward(double moveSpeed)
{
    double look[3];

    lookDirection(look);

    normalize(look);
    eye[0] -= look[0] * moveSpeed;
    eye[1] -= look[1] * moveSpeed;
    eye[2] -= look[2] * moveSpeed;

    roomBoundaries();

    updateCenter(look);
}

/**
 * @brief Strafe the camera left (perpendicular to look direction).
 * @param moveSpeed: Camera movement speed.
 */
void Camera::moveLeft(double moveSpeed)
{
    double look[3], right[3];
    lookDirection(look);

    // Calculate the right vector (cross product of look and up)
    crossProduct(look, up, right);
    normalize(right);

    // Move eye and center to the left (negative right vector)
    eye[0] -= right[0] * moveSpeed;
    eye[1] -= right[1] * moveSpeed;
    eye[2] -= right[2] * moveSpeed;

    center[0] -= right[0] * moveSpeed;
    center[1] -= right[1] * moveSpeed;
    center[2] -= right[2] * moveSpeed;

    roomBoundaries();
}

/**
 * @brief Strafe the camera right (perpendicular to look direction).
 * @param moveSpeed: Camera movement speed.
 */
void Camera::moveRight(double moveSpeed)
{
    double look[3], right[3];
    lookDirection(look);

    crossProduct(look, up, right);
    normalize(right);

    // Move eye and center to the right (positive right vector)
    eye[0] += right[0] * moveSpeed;
    eye[1] += right[1] * moveSpeed;
    eye[2] += right[2] * moveSpeed;

    center[0] += right[0] * moveSpeed;
    center[1] += right[1] * moveSpeed;
    center[2] += right[2] * moveSpeed;

    roomBoundaries();
}

//  ===== Rotation methods =====

/**
 * @brief Rotate the camera view to the left (yaw).
 * @param lookSpeed: Rotation speed in radians.
 */
void Camera::rotateLeft(double lookSpeed)
{
    double look[3];
    lookDirection(look);

    rotatePoint(up, lookSpeed, look);

    updateCenter(look);
}

/**
 * @brief Rotate the camera view to the right (yaw).
 * @param lookSpeed: Rotation speed in radians.
 */
void Camera::rotateRight(double lookSpeed)
{
    double look[3];
    lookDirection(look);

    rotatePoint(up, -lookSpeed, look);

    updateCenter(look);
}

/**
 * @brief Rotate the camera view upward (pitch).
 * @param lookSpeed: Rotation speed in radians.
 */
void Camera::lookUp(double lookSpeed)
{
    double rot_axis[3];
    double look[3];

    lookDirection(look);

    crossProduct(look, up, rot_axis);
    normalize(rot_axis);

    rotatePoint(rot_axis, lookSpeed, look);
    rotatePoint(rot_axis, lookSpeed, up);

    updateCenter(look);
}

/**
 * @brief Rotate the camera view downward (pitch).
 * @param lookSpeed: Rotation speed in radians.
 */
void Camera::lookDown(double lookSpeed)
{
    double rot_axis[3];
    double look[3];

    lookDirection(look);

    crossProduct(look, up, rot_axis);
    normalize(rot_axis);

    rotatePoint(rot_axis, -lookSpeed, look);
    rotatePoint(rot_axis, -lookSpeed, up);

    updateCenter(look);
}

// ===== Jump methods =====

/**
 * @brief Initiate a jump if the camera is on the ground.
 */
void Camera::jump()
{
    if (!isJumping && fabs(eye[1] - ROOM_Y_MIN) < 1e-6) {
        isJumping = true;
        verticalVelocity = JUMP_VELOCITY;
    }
}

/**
 * @brief Update gravity and jump state each frame.
 */
void Camera::updateGravityAndJump()
{
    if (isJumping || eye[1] > ROOM_Y_MIN + 1e-6) {
        verticalVelocity += GRAVITY;
        eye[1] += verticalVelocity;

        // Clamp to floor
        if (eye[1] <= ROOM_Y_MIN) {
            eye[1] = ROOM_Y_MIN;
            verticalVelocity = 0.0;
            isJumping = false;
        }
        // Clamp to ceiling
        if (eye[1] > ROOM_Y_MAX) {
            eye[1] = ROOM_Y_MAX;
            verticalVelocity = 0.0;
        }
        // Keep center in sync
        center[1] = eye[1];
    }
}

//  ===== Helper methods =====

/**
 * @brief Compute the normalized look direction vector.
 * @param look: Output array for the look direction.
 */
void Camera::lookDirection(double look[])
{
    look[0] = center[0] - eye[0];
    look[1] = center[1] - eye[1];
    look[2] = center[2] - eye[2];

    normalize(look);
}

/**
 * @brief Update the center vector based on the eye and look direction.
 * @param look: The look direction vector.
 */
void Camera::updateCenter(double look[])
{
    center[0] = eye[0] + look[0];
    center[1] = eye[1] + look[1];
    center[2] = eye[2] + look[2];
}

/**
 * @brief Compute the cross product of two vectors.
 * @param a: First vector.
 * @param b: Second vector.
 * @param c: Output vector for the cross product.
 */
void Camera::crossProduct(const double a[], const double b[], double c[])
{
    c[0] = a[1] * b[2] - a[2] * b[1];
    c[1] = a[2] * b[0] - a[0] * b[2];
    c[2] = a[0] * b[1] - a[1] * b[0];
}

/**
 * @brief Normalize a 3D vector in place.
 * @param a The vector to normalize.
 */
void Camera::normalize(double a[])
{
    double norm;
    norm = a[0] * a[0] + a[1] * a[1] + a[2] * a[2];
    norm = sqrt(norm);
    a[0] /= norm;
    a[1] /= norm;
    a[2] /= norm;
}

/**
 * @brief Rotate a point around an axis by a given angle.
 * @param a: Axis of rotation.
 * @param theta: Angle in radians.
 * @param p: Point to rotate in place.
 */
void Camera::rotatePoint(const double a[], double theta, double p[])
{
    double temp[3];
    temp[0] = p[0];
    temp[1] = p[1];
    temp[2] = p[2];

    temp[0] = -a[2] * p[1] + a[1] * p[2];
    temp[1] = a[2] * p[0] - a[0] * p[2];
    temp[2] = -a[1] * p[0] + a[0] * p[1];

    temp[0] *= sin(theta);
    temp[1] *= sin(theta);
    temp[2] *= sin(theta);

    temp[0] += (1 - cos(theta)) * (a[0] * a[0] * p[0] + a[0] * a[1] * p[1] + a[0] * a[2] * p[2]);
    temp[1] += (1 - cos(theta)) * (a[0] * a[1] * p[0] + a[1] * a[1] * p[1] + a[1] * a[2] * p[2]);
    temp[2] += (1 - cos(theta)) * (a[0] * a[2] * p[0] + a[1] * a[2] * p[1] + a[2] * a[2] * p[2]);

    temp[0] += cos(theta) * p[0];
    temp[1] += cos(theta) * p[1];
    temp[2] += cos(theta) * p[2];

    p[0] = temp[0];
    p[1] = temp[1];
    p[2] = temp[2];
}

/**
 * @brief Clamp the camera position to stay within room boundaries.
 */
void Camera::roomBoundaries()
{
    if (eye[0] < ROOM_X_MIN) eye[0] = ROOM_X_MIN;
    if (eye[0] > ROOM_X_MAX) eye[0] = ROOM_X_MAX;
    if (eye[1] < ROOM_Y_MIN) eye[1] = ROOM_Y_MIN;
    if (eye[1] > ROOM_Y_MAX) eye[1] = ROOM_Y_MAX;
    if (eye[2] < ROOM_Z_MIN) eye[2] = ROOM_Z_MIN;
    if (eye[2] > ROOM_Z_MAX) eye[2] = ROOM_Z_MAX;
}