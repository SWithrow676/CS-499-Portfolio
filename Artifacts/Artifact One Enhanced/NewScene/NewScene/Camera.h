#pragma once
#include <cmath>

// Camera for 3D navigation and view control.
class Camera {
public:
    // Construct camera with eye, center, and up vectors.
    Camera(double ex, double ey, double ez,
        double cx, double cy, double cz,
        double ux, double uy, double uz);

    void forward(double moveSpeed);
    void backward(double moveSpeed);
    void moveLeft(double moveSpeed);
    void moveRight(double moveSpeed);

    void rotateLeft(double lookSpeed);
    void rotateRight(double lookSpeed);
    void lookUp(double lookSpeed);
    void lookDown(double lookSpeed);

	void jump();
    void updateGravityAndJump();

    const double* getEye() { return eye; }
    const double* getCenter() { return center; }
    const double* getUp() { return up; }

private:
	double eye[3];      // Camera position
	double center[3];   // Look-at point
	double up[3];       // Up direction

    bool isJumping;
    double verticalVelocity;

    // Room boundaries
    const double ROOM_X_MIN = -2.0 + 0.1;
    const double ROOM_X_MAX = 2.0 - 0.1;
    const double ROOM_Y_MIN = 0.2;
    const double ROOM_Y_MAX = 0.5 - 0.1;
    const double ROOM_Z_MIN = -4.0 + 0.1;
    const double ROOM_Z_MAX = 4.0 - 0.1;

    // Physics constants
    const double GRAVITY = -0.01;
    const double JUMP_VELOCITY = 0.1;

	// Helper methods
    void lookDirection(double look[]);
    void updateCenter(double look[]);
    void crossProduct(const double a[], const double b[], double c[]);
    void normalize(double a[]);
    void rotatePoint(const double a[], double theta, double p[]);
	void roomBoundaries();
};