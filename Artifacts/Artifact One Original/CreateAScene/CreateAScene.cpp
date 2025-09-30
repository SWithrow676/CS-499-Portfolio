// CreateAScene.cpp : This file contains the 'main' function. Program execution begins and ends there.

#include <math.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>
#include "glut.h"

// External C file for reading external .obj files
extern "C"
{
#include "glm.h"
}

// Window width, height, and aspec ratio
int windowWidth = 900;
int windowHeight = 900;
float aspect = float(windowWidth) / float(windowHeight);

// Constant Variables for Door, Rotation, and Movement Speed
const double MOVE_SPEED = 0.1;
const double ROTATE_SPEED = 3.1415 / 16;
const double DOOR_SPEED = 1.0;

// Other Global Variables for the Door
bool openDoor = true;
double doorAngle = 90.0,
targetAngle = 90.0;

// Global variable for spin
float spin = 0.0f;

// Default Properties for Camera
double eye[] = { 0.0 ,0.2, 2.0 };
double center[] = { 0.0, 0.2, 0.0 };
double up[] = { 0.0, 1.0, 0.0 };

// Model Pointers used to import the specific models used in the environment
GLMmodel* pmodel2 = NULL;
GLMmodel* pmodel3 = NULL;
GLMmodel* pmodel4 = NULL;
GLMmodel* pmodel5 = NULL;

// Variables to store display commands from the imported objects
GLuint importRose;
GLuint importDolph;
GLuint importAl;
GLuint importPorsche;

// First Light Properties
GLfloat light0_ambient[] = { 0.2f, 0.2f, 0.2f, 1.0f };
GLfloat light0_diffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };
GLfloat light0_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
GLfloat light0_position[] = { 1.0f, 1.0f, 2.0f, 0.0f };

// Second Light Properties
GLfloat light1_ambient[] = { 0.1f, 0.1f, 0.1f, 1.0f };
GLfloat light1_diffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };
GLfloat light1_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
GLfloat light1_position[] = { -1.0f, 0.5f, -1.0f, 0.0f };

/* 
    Material properties for diffuse, glossy, and metallic teapots 
*/

// Diffuse
GLfloat diffuse_amb[] = { 0.2f, 0.2f, 0.2f, 1.0f };
GLfloat diffuse_dif[] = { 0.8f, 0.0f, 0.0f, 1.0f };     // Red
GLfloat diffuse_spec[] = { 0.0f, 0.0f, 0.0f, 1.0f };    // No specularity
GLfloat diffuse_shin[] = { 0.0f };                      // No shininess

// Glossy
GLfloat glossy_amb[] = { 0.2f, 0.2f, 0.2f, 1.0f };
GLfloat glossy_dif[] = { 0.0f, 0.0f, 0.8f, 1.0f };          // Blue
GLfloat glossy_spec[] = { 0.775f, 0.775f, 0.775f, 1.0f };   // High specularity
GLfloat glossy_shin[] = { 50.0f };                          // High shine

// Metallic
GLfloat metallic_amb[] = { 0.3f, 0.3f, 0.3f, 1.0f };    // Higher Ambience
GLfloat metallic_dif[] = { 0.4f, 0.4f, 0.4f, 1.0f };    // Gray
GLfloat metallic_spec[] = { 0.9f, 0.9f, 0.9f, 1.0f };   // High specularity
GLfloat metallic_shin[] = { 125.0f };                   // Higher shine

// Function to set up the environment's lighting properties
void setupLighting() 
{
    // Necessary Enables for lighing and coloring
    glEnable(GL_LIGHTING);
    glEnable(GL_COLOR_MATERIAL);
    glEnable(GL_NORMALIZE);

    // Setup first light source
    glLightfv(GL_LIGHT0, GL_AMBIENT, light0_ambient);
    glLightfv(GL_LIGHT0, GL_DIFFUSE, light0_diffuse);
    glLightfv(GL_LIGHT0, GL_SPECULAR, light0_specular);
    glLightfv(GL_LIGHT0, GL_POSITION, light0_position);
    glEnable(GL_LIGHT0);

    // Setup second light source
    glLightfv(GL_LIGHT1, GL_AMBIENT, light1_ambient);
    glLightfv(GL_LIGHT1, GL_DIFFUSE, light1_diffuse);
    glLightfv(GL_LIGHT1, GL_SPECULAR, light1_specular);
    glLightfv(GL_LIGHT1, GL_POSITION, light1_position);
    glEnable(GL_LIGHT1);
}

// Function that can be called to set diffuse material
void setDiffuse() 
{
    glMaterialfv(GL_FRONT, GL_AMBIENT, diffuse_amb);
    glMaterialfv(GL_FRONT, GL_DIFFUSE, diffuse_dif);
    glMaterialfv(GL_FRONT, GL_SPECULAR, diffuse_spec);
    glMaterialf(GL_FRONT, GL_SHININESS, diffuse_shin[0]);
}

// Function that can be called to set glossy material
void setGlossy() 
{
    glMaterialfv(GL_FRONT, GL_AMBIENT, glossy_amb);
    glMaterialfv(GL_FRONT, GL_DIFFUSE, glossy_dif);
    glMaterialfv(GL_FRONT, GL_SPECULAR, glossy_spec);
    glMaterialf(GL_FRONT, GL_SHININESS, glossy_shin[0]);
}

// Function that can be called to set metallic material
void setMetallic() 
{
    glMaterialfv(GL_FRONT, GL_AMBIENT, metallic_amb);
    glMaterialfv(GL_FRONT, GL_DIFFUSE, metallic_dif);
    glMaterialfv(GL_FRONT, GL_SPECULAR, metallic_spec);
    glMaterialf(GL_FRONT, GL_SHININESS, metallic_shin[0]);
}

// Initialization function containing necessary function calls
void init(void)
{
    // Initializes perspective projection for rendering
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(45.0, aspect, 0.1, 100.0);

    // Sets the current matrix into the ModelView stack
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();

    // Initializes Environment Lighing
    setupLighting();

    // Enable depth test 
    glEnable(GL_DEPTH_TEST);
}

// Function to perform cross products onto two matricies
void crossProduct(double a[], double b[], double c[])
{
    c[0] = a[1] * b[2] - a[2] * b[1];
    c[1] = a[2] * b[0] - a[0] * b[2];
    c[2] = a[0] * b[1] - a[1] * b[0];
}

// Function to normalize a matrix
void normalize(double a[])
{
    double norm;
    norm = a[0] * a[0] + a[1] * a[1] + a[2] * a[2];
    norm = sqrt(norm);
    a[0] /= norm;
    a[1] /= norm;
    a[2] /= norm;
}

// Function to calculate rotation based on passed in parameters
void rotatePoint(double a[], double theta, double p[])
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

// Passed parameters to calulate look based on direction and normalize the matrix
void lookDirection(double look[])
{
    look[0] = center[0] - eye[0];
    look[1] = center[1] - eye[1];
    look[2] = center[2] - eye[2];

    normalize(look);
}

// Updates the center based on camera and look positions
void updateCenter(double look[])
{
    center[0] = eye[0] + look[0];
    center[1] = eye[1] + look[1];
    center[2] = eye[2] + look[2];
}

// Rotates the camera's direction to the left
void Left()
{
    double look[3];
    lookDirection(look);

    rotatePoint(up, ROTATE_SPEED, look);

    updateCenter(look);
}

// Rotates the camera's direction to the right
void Right()
{
    double look[3];
    lookDirection(look);

    rotatePoint(up, -ROTATE_SPEED, look);

    updateCenter(look);
}

// Rotates the camera's direction upwards
void Up()
{
    double rot_axis[3];
    double look[3];

    lookDirection(look);

    crossProduct(look, up, rot_axis);
    normalize(rot_axis);

    rotatePoint(rot_axis, ROTATE_SPEED, look);
    rotatePoint(rot_axis, ROTATE_SPEED, up);

    updateCenter(look);

}

// Rotates the camera's direction downwards
void Down()
{
    double rot_axis[3];
    double look[3];

    lookDirection(look);

    crossProduct(look, up, rot_axis);
    normalize(rot_axis);

    rotatePoint(rot_axis, -ROTATE_SPEED, look);
    rotatePoint(rot_axis, -ROTATE_SPEED, up);

    updateCenter(look);
}

// Moves the camera's position forward
void Forward()
{
    double look[3];

    lookDirection(look);

    normalize(look);
    eye[0] += look[0] * MOVE_SPEED;
    eye[1] += look[1] * MOVE_SPEED;
    eye[2] += look[2] * MOVE_SPEED;

    updateCenter(look);
}

// Moves the camera's position backward
void Backward()
{
    double look[3];

    lookDirection(look);

    normalize(look);
    eye[0] -= look[0] * MOVE_SPEED;
    eye[1] -= look[1] * MOVE_SPEED;
    eye[2] -= look[2] * MOVE_SPEED;

    updateCenter(look);
}

// Inverses the bool value of openDoor upon call and changes target angle based on its value
void Door()
{
    openDoor = !openDoor;

    if (openDoor)
        targetAngle = 90.0;
    else
        targetAngle = 0.0;
}

// Function that changes the door's angle if the target angles is changed
void updateDoor()
{
    if (doorAngle != targetAngle)
    {
        if (doorAngle < targetAngle)
        {
            doorAngle += DOOR_SPEED;
            if (doorAngle > targetAngle)
            {
                doorAngle = targetAngle;
            }
        }
        else
        {
            doorAngle -= DOOR_SPEED;
            if (doorAngle < targetAngle)
            {
                doorAngle = targetAngle;
            }
        }
        glutPostRedisplay();
    }
}

// Draws the imported rose model using the external C file
void drawmodel_rosevase(void)
{
    if (!pmodel2)
    {
        // Model file assigned to model pointer
        pmodel2 = glmReadOBJ((char*)"data/rose+vase.obj");
        if (!pmodel2) exit(0);

        glmUnitize(pmodel2);
        glmFacetNormals(pmodel2);
        glmVertexNormals(pmodel2, 90.0);
    }

    glmDraw(pmodel2, GLM_SMOOTH | GLM_MATERIAL);
}

// Draws the imported dolphin group model using the external C file
void drawmodel_dolphins(void)
{
    if (!pmodel3)
    {
        // Model file assigned to model pointer
        pmodel3 = glmReadOBJ((char*)"data/dolphins.obj");
        if (!pmodel3) exit(0);

        glmUnitize(pmodel3);
        glmFacetNormals(pmodel3);
        glmVertexNormals(pmodel3, 90.0);
    }

    glmDraw(pmodel3, GLM_SMOOTH | GLM_MATERIAL);
}

// Draws the imported Al model using the external C file
void drawmodel_al(void)
{
    if (!pmodel4)
    {
        // Model file assigned to model pointer
        pmodel4 = glmReadOBJ((char*)"data/al.obj");
        if (!pmodel4) exit(0);

        glmUnitize(pmodel4);
        glmFacetNormals(pmodel4);
        glmVertexNormals(pmodel4, 90.0);
    }

    glmDraw(pmodel4, GLM_SMOOTH | GLM_MATERIAL);
}

// Draws the imported Porsche model using the external C file
void drawmodel_porsche(void)
{
    if (!pmodel5)
    {
        // Model file assigned to model pointer
        pmodel5 = glmReadOBJ((char*)"data/porsche.obj");
        if (!pmodel5) exit(0);

        glmUnitize(pmodel5);
        glmFacetNormals(pmodel5);
        glmVertexNormals(pmodel5, 90.0);
    }

    glmDraw(pmodel5, GLM_SMOOTH | GLM_MATERIAL);
}

// Function to determine how fast an object is spinning if assigned "spin" in rotation
void TimerRotate(int value)
{
    spin = spin + 4;
    if (spin > 360.0)
        spin = spin - 360.0;

    glutPostRedisplay();
    glutTimerFunc(30, TimerRotate, 0);
}

// Keyboard functions to rotate camera's perspective
void specialKeys(int key, int x, int y)
{
    switch (key)
    {
    // Look left
    case GLUT_KEY_LEFT: Left(); break;
    // Look right
    case GLUT_KEY_RIGHT: Right(); break;
    // Look up
    case GLUT_KEY_UP:    Up(); break;
    // Look down
    case GLUT_KEY_DOWN:  Down(); break;
    }

    glutPostRedisplay();
}

// Additional keyboard functions
void keyboard(unsigned char key, int x, int y)
{
    switch (key) {
    // Moving forward
    case 'f': Forward(); break;
    // Moving backward
    case 'b': Backward(); break;
    // Toggle door
    case ' ': Door(); break;
    }
    glutPostRedisplay();
}

// Draws all of the envrionment's walls and sets material properties for lighting
void drawWalls()
{
    // Set wall material properties for consistent lighting
    GLfloat wall_ambient[] = { 0.2f, 0.2f, 0.2f, 1.0f };
    GLfloat wall_diffuse[] = { 0.0f, 0.75f, 0.75f, 1.0f }; // Teal
    GLfloat wall_specular[] = { 0.1f, 0.1f, 0.1f, 1.0f };
    GLfloat wall_shininess[] = { 15.0f };

    glMaterialfv(GL_FRONT, GL_AMBIENT, wall_ambient);
    glMaterialfv(GL_FRONT, GL_DIFFUSE, wall_diffuse);
    glMaterialfv(GL_FRONT, GL_SPECULAR, wall_specular);
    glMaterialf(GL_FRONT, GL_SHININESS, wall_shininess[0]);

    // Front wall
    glBegin(GL_QUADS);
    // glNormal3f will be used for display as everything is scaled 
    glNormal3f(0.0f, 0.0f, 1.0f);
    glVertex3f(-2.0f, 0.0f, -4.0f);
    glVertex3f(2.0f, 0.0f, -4.0f);
    glVertex3f(2.0f, 0.5f, -4.0f);
    glVertex3f(-2.0f, 0.5f, -4.0f);
    glEnd();

    // Back wall
    glBegin(GL_QUADS);
    glNormal3f(0.0f, 0.0f, -1.0f);
    glVertex3f(-2.0f, 0.0f, 4.0f);
    glVertex3f(2.0f, 0.0f, 4.0f);
    glVertex3f(2.0f, 0.5f, 4.0f);
    glVertex3f(-2.0f, 0.5f, 4.0f);
    glEnd();

    // Left wall
    glBegin(GL_QUADS);
    glNormal3f(1.0f, 0.0f, 0.0f);
    glVertex3f(-2.0f, 0.0f, -4.0f);
    glVertex3f(-2.0f, 0.0f, 4.0f);
    glVertex3f(-2.0f, 0.5f, 4.0f);
    glVertex3f(-2.0f, 0.5f, -4.0f);
    glEnd();

    // Right wall
    glBegin(GL_QUADS);
    glNormal3f(-1.0f, 0.0f, 0.0f);
    glVertex3f(2.0f, 0.0f, -4.0f);
    glVertex3f(2.0f, 0.0f, 4.0f);
    glVertex3f(2.0f, 0.5f, 4.0f);
    glVertex3f(2.0f, 0.5f, -4.0f);
    glEnd();

    // Middle wall
    glBegin(GL_QUADS);
    glNormal3f(0.0f, 0.0f, -1.0f);

    // Left part of middle wall
    glVertex3f(-2.0f, 0.0f, 0.0f);
    glVertex3f(-0.2f, 0.0f, 0.0f);
    glVertex3f(-0.2f, 0.5f, 0.0f);
    glVertex3f(-2.0f, 0.5f, 0.0f);

    // Right part of middle wall
    glVertex3f(0.2f, 0.0f, 0.0f);
    glVertex3f(2.0f, 0.0f, 0.0f);
    glVertex3f(2.0f, 0.5f, 0.0f);
    glVertex3f(0.2f, 0.5f, 0.0f);

    // Top part above doorway
    glVertex3f(-0.2f, 0.4f, 0.0f);
    glVertex3f(0.2f, 0.4f, 0.0f);
    glVertex3f(0.2f, 0.5f, 0.0f);
    glVertex3f(-0.2f, 0.5f, 0.0f);

    glEnd();
}

// Draws the envrionment's ceiling and sets material properties for lighting
void drawCeiling()
{
    // Set ceiling material properties for consistent lighting
    GLfloat ceiling_ambient[] = { 0.3f, 0.3f, 0.3f, 1.0f };
    GLfloat ceiling_diffuse[] = { 0.0f, 0.75f, 0.75f, 1.0f }; // Teal
    GLfloat ceiling_specular[] = { 0.1f, 0.1f, 0.1f, 1.0f };
    GLfloat ceiling_shininess[] = { 25.0f };

    glMaterialfv(GL_FRONT, GL_AMBIENT, ceiling_ambient);
    glMaterialfv(GL_FRONT, GL_DIFFUSE, ceiling_diffuse);
    glMaterialfv(GL_FRONT, GL_SPECULAR, ceiling_specular);
    glMaterialf(GL_FRONT, GL_SHININESS, ceiling_shininess[0]);

    glBegin(GL_QUADS);
    glNormal3f(0.0f, 1.0f, 0.0f);
    glVertex3f(-2.0f, 0.5f, -4.0f);
    glVertex3f(2.0f, 0.5f, -4.0f);
    glVertex3f(2.0f, 0.5f, 4.0f);
    glVertex3f(-2.0f, 0.5f, 4.0f);
    glEnd();
}

// Draws the envrionment's floor and sets material properties for lighting
void drawFloor()
{
    // Set floor material properties for consistent lighting
    GLfloat floor_ambient[] = { 0.2f, 0.2f, 0.1f, 1.0f };
    GLfloat floor_diffuse[] = { 0.0f, 0.75f, 0.75f, 1.0f }; // Teal
    GLfloat floor_specular[] = { 0.1f, 0.1f, 0.1f, 1.0f };
    GLfloat floor_shininess[] = { 5.0f };

    glMaterialfv(GL_FRONT, GL_AMBIENT, floor_ambient);
    glMaterialfv(GL_FRONT, GL_DIFFUSE, floor_diffuse);
    glMaterialfv(GL_FRONT, GL_SPECULAR, floor_specular);
    glMaterialf(GL_FRONT, GL_SHININESS, floor_shininess[0]);

    glBegin(GL_QUADS);
    glNormal3f(0.0f, 1.0f, 0.0f);
    glVertex3f(-2.0f, 0.0f, -4.0f);
    glVertex3f(2.0f, 0.0f, -4.0f);
    glVertex3f(2.0f, 0.0f, 4.0f);
    glVertex3f(-2.0f, 0.0f, 4.0f);

    glEnd();
}

// Draws the door for accessing the other room seperated by the middle wall
void drawDoor()
{
    // Set door material properties for consistent lighting
    GLfloat door_ambient[] = { 0.2f, 0.2f, 0.2f, 1.0f };
    GLfloat door_diffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };    // White
    GLfloat door_specular[] = { 0.1f, 0.1f, 0.1f, 1.0f };
    GLfloat door_shininess[] = { 5.0f };

    glMaterialfv(GL_FRONT, GL_AMBIENT, door_ambient);
    glMaterialfv(GL_FRONT, GL_DIFFUSE, door_diffuse);
    glMaterialfv(GL_FRONT, GL_SPECULAR, door_specular);
    glMaterialf(GL_FRONT, GL_SHININESS, door_shininess[0]);

    // Push Matrix is used to contain transformations
    glPushMatrix();

    glTranslatef(-0.2f, 0.0f, 0.0f);
    glRotatef(doorAngle, 0.0f, 1.0f, 0.0f);

    // Draw the door
    glBegin(GL_QUADS);
    glNormal3f(0.0f, 0.0f, -1.0f);
    glVertex3f(0.0f, 0.0f, 0.0f);
    glVertex3f(0.4f, 0.0f, 0.0f);
    glVertex3f(0.4f, 0.4f, 0.0f);
    glVertex3f(0.0f, 0.4f, 0.0f);
    glEnd();

    glPopMatrix();
}

// Display Teapot Functions
void drawTeapots()
{
    // Red Diffuse Teapot
    glPushMatrix();
    // Material setter per teapot
    setDiffuse();
    glTranslatef(-0.2f, 0.15f, 1.0f);
    glutSolidTeapot(0.05f);
    glPopMatrix();

    // Blue Glossy Teapot
    glPushMatrix();
    setGlossy();
    glTranslatef(0.0f, 0.15f, 1.0f);
    glutSolidTeapot(0.05f);
    glPopMatrix();

    // Metallic Teapot
    glPushMatrix();
    setMetallic();
    glTranslatef(0.2f, 0.15f, 1.0f);
    glutSolidTeapot(0.05f);
    glPopMatrix();
}

// Draws the shaded table beneath the teapots
void drawCube()
{
    // Set table material properties for consistent lighting and shading
    GLfloat table_ambient[] = { 0.2f, 0.2f, 0.2f, 1.0f };
    GLfloat table_diffuse[] = { 0.75f, 0.75f, 0.0f, 1.0f }; // Gold
    GLfloat table_specular[] = { 0.3f, 0.1f, 0.1f, 1.0f };
    GLfloat table_shininess[] = { 15.0f };

    glMaterialfv(GL_FRONT, GL_AMBIENT, table_ambient);
    glMaterialfv(GL_FRONT, GL_DIFFUSE, table_diffuse);
    glMaterialfv(GL_FRONT, GL_SPECULAR, table_specular);
    glMaterialf(GL_FRONT, GL_SHININESS, table_shininess[0]);

    glBegin(GL_QUADS);

    // Top Face
    glNormal3f(0.0f, 1.0f, 0.0f);
    glVertex3f(1.0f, 1.0f, -1.0f);
    glVertex3f(-1.0f, 1.0f, -1.0f);
    glVertex3f(-1.0f, 1.0f, 1.0f);
    glVertex3f(1.0f, 1.0f, 1.0f);

    // Bottom Face
    glNormal3f(0.0f, -1.0f, 0.0f);
    glVertex3f(1.0f, -1.0f, 1.0f);
    glVertex3f(-1.0f, -1.0f, 1.0f);
    glVertex3f(-1.0f, -1.0f, -1.0f);
    glVertex3f(1.0f, -1.0f, -1.0f);

    // Front Face
    glNormal3f(0.0f, 0.0f, 1.0f);
    glVertex3f(1.0f, 1.0f, 1.0f);
    glVertex3f(-1.0f, 1.0f, 1.0f);
    glVertex3f(-1.0f, -1.0f, 1.0f);
    glVertex3f(1.0f, -1.0f, 1.0f);

    // Back Face
    glNormal3f(0.0f, 0.0f, -1.0f);
    glVertex3f(1.0f, 1.0f, -1.0f);
    glVertex3f(-1.0f, 1.0f, -1.0f);
    glVertex3f(-1.0f, -1.0f, -1.0f);
    glVertex3f(1.0f, -1.0f, -1.0f);

    // Left Face
    glNormal3f(1.0f, 0.0f, 0.0f);
    glVertex3f(1.0f, 1.0f, -1.0f);
    glVertex3f(1.0f, -1.0f, -1.0f);
    glVertex3f(1.0f, -1.0f, 1.0f);
    glVertex3f(1.0f, 1.0f, 1.0f);

    // Right Face
    glNormal3f(-1.0f, 0.0f, 0.0f);
    glVertex3f(-1.0f, 1.0f, -1.0f);
    glVertex3f(-1.0f, -1.0f, -1.0f);
    glVertex3f(-1.0f, -1.0f, 1.0f);
    glVertex3f(-1.0f, 1.0f, 1.0f);

    glEnd();	// End of drawing cube
}

// Import and Draw "Rose and Vase"
void drawImportRose()
{
    // Material Properties for Rose and Vase
    // (mat_diffuse dropped to use default model coloring)
    GLfloat mat_ambient[] = { 0.1f, 0.1f, 0.1f, 1.0f };
    GLfloat mat_specular[] = { 0.5f, 0.5f, 0.5f, 1.0f };
    GLfloat mat_shininess[] = { 30.0f };

    glMaterialfv(GL_FRONT, GL_AMBIENT, mat_ambient);
    glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
    glMaterialf(GL_FRONT, GL_SHININESS, mat_shininess[0]);

    // Rose vase - imported
    glPushMatrix();
    glScalef(0.05, 0.05, 0.05);
    drawmodel_rosevase();
    glPopMatrix();
}

// Import and Draw "Dolphins"
void drawImportDolphins()
{
    GLfloat mat_ambient[] = { 0.1f, 0.1f, 0.1f, 1.0f };
    GLfloat mat_specular[] = { 0.5f, 0.5f, 0.5f, 1.0f };
    GLfloat mat_shininess[] = { 30.0f };

    glMaterialfv(GL_FRONT, GL_AMBIENT, mat_ambient);
    glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
    glMaterialf(GL_FRONT, GL_SHININESS, mat_shininess[0]);

    // Dolphins - imported
    glPushMatrix();
    glScalef(0.05, 0.05, 0.05);
    drawmodel_dolphins();
    glPopMatrix();
}

// Import and Draw "Al"
void drawImportAl()
{
    GLfloat mat_ambient[] = { 0.1f, 0.1f, 0.1f, 1.0f };
    GLfloat mat_specular[] = { 0.5f, 0.5f, 0.5f, 1.0f };
    GLfloat mat_shininess[] = { 30.0f };

    glMaterialfv(GL_FRONT, GL_AMBIENT, mat_ambient);
    glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
    glMaterialf(GL_FRONT, GL_SHININESS, mat_shininess[0]);

    // Al - imported
    glPushMatrix();
    glScalef(0.05, 0.05, 0.05);
    drawmodel_al();
    glPopMatrix();
}

// Import and Draw "Porsche"
void drawImportPorsche()
{
    GLfloat mat_ambient[] = { 0.1f, 0.1f, 0.1f, 1.0f };
    GLfloat mat_specular[] = { 0.5f, 0.5f, 0.5f, 1.0f };
    GLfloat mat_shininess[] = { 30.0f };

    glMaterialfv(GL_FRONT, GL_AMBIENT, mat_ambient);
    glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
    glMaterialf(GL_FRONT, GL_SHININESS, mat_shininess[0]);

    // Porsche - imported
    glPushMatrix();
    glScalef(0.05, 0.05, 0.05);
    drawmodel_porsche();
    glPopMatrix();
}

// Display function to display the whole environment
void display(void)
{
    // Update door function called at every loop to animate it
    updateDoor();
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // Calls camera position per loop
    glLoadIdentity();
    gluLookAt(eye[0], eye[1], eye[2],
        center[0], center[1], center[2],
        up[0], up[1], up[2]);

    // Display fixed distant lighting
    glLightfv(GL_LIGHT0, GL_POSITION, light0_position);
    glLightfv(GL_LIGHT1, GL_POSITION, light1_position);

    // Calls environment display functions created before
    drawFloor();
    drawCeiling();
    drawWalls();
    drawDoor();
    drawTeapots();

    // Transforms and displays table
    glPushMatrix();
    glTranslatef(0.0f, 0.05f, 1.0f);
    glScalef(0.3f, 0.05f, 0.15f);
    drawCube();
    glPopMatrix();

    // Displays Rose object using display lists and performs transformations
    glPushMatrix();
    glColor3f(0.0, 0.5, 1.0);
    glTranslatef(0.25, 0.25, -2.0);
    glScalef(4.0, 4.0, 4.0);
    // External objects will be spinning
    glRotatef(spin, 0, 1, 0);
    glCallList(importRose);
    glPopMatrix();

    // Displays Dolphin Group object using display lists and performs transformations
    glPushMatrix();
    glColor3f(1.0, 0.5, 1.0);
    glTranslatef(-0.25, 0.25, -2.0);
    glScalef(4.0, 4.0, 4.0);
    glRotatef(spin, 0, 1, 0);
    glCallList(importDolph);
    glPopMatrix();

    // Displays Al object using display lists and performs transformations
    glPushMatrix();
    glColor3f(0.0, 0.75, 0.0);
    glTranslatef(0.25, 0.25, -3.0);
    glScalef(4.0, 4.0, 4.0);
    glRotatef(spin, 0, 1, 0);
    glCallList(importAl);
    glPopMatrix();

    // Displays Porsche object using display lists and performs transformations
    glPushMatrix();
    glColor3f(1.0, 0.5, 0.0);
    glTranslatef(-0.25, 0.25, -3.0);
    glScalef(4.0, 4.0, 4.0);
    glRotatef(spin, 0, 1, 0);
    glCallList(importPorsche);
    glPopMatrix(); 

    glutSwapBuffers();

    // Function that starts animating door if door angle is not equal to target angle
    if (doorAngle != targetAngle)
        glutPostRedisplay();
}

// Main function
void main(int argc, char** argv)
{
    // Window setup function calls
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
    glutInitWindowSize(windowWidth, windowHeight);
    glutInitWindowPosition(50, 50);
    glutCreateWindow("Navigator");

    // Initializer, display, and keyboard functions called
    init();
    glutDisplayFunc(display);
    glutSpecialFunc(specialKeys);
    glutKeyboardFunc(keyboard);

    // Display list for each imported object
    importRose = glGenLists(2);
    glNewList(importRose, GL_COMPILE);
    drawImportRose();
    glEndList();

    importDolph = glGenLists(2);
    glNewList(importDolph, GL_COMPILE);
    drawImportDolphins();
    glEndList();

    importAl = glGenLists(2);
    glNewList(importAl, GL_COMPILE);
    drawImportAl();
    glEndList();

    importPorsche = glGenLists(2);
    glNewList(importPorsche, GL_COMPILE);
    drawImportPorsche();
    glEndList();

    // Timer and loop function called
    glutTimerFunc(0, TimerRotate, 0);
    glutMainLoop();
}