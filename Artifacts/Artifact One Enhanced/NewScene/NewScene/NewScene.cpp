/**
 * @file NewScene.cpp
 * @brief Interactive OpenGL scene with navigation, collision, and object rendering.
 *
 * @author Stewart Withrow
 * @course CS 330 / CS 499 Capstone
 * @date September 21, 2025
 */

#include <math.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>
#include "glut.h"
#include "Camera.h"
#include "Door.h"

extern "C"
{
#include "glm.h"
}

// Window dimensions and aspect ratio variables
int windowWidth;
int windowHeight;
float aspect;

// Constant variables for movement and rotation speeds of the camera
const double MOVE_SPEED = 0.1;
const double ROTATE_SPEED = 3.1415 / 16;

// Global spin value for animated objects
float spin = 0.0f;

// Pointers to direct to imported models
GLMmodel* pmodel0 = NULL;
GLMmodel* pmodel1 = NULL;
GLMmodel* pmodel2 = NULL;
GLMmodel* pmodel3 = NULL;

// Variables to store display commands from the imported objects
GLuint importRose;
GLuint importDolph;
GLuint importAl;
GLuint importPorsche;

// Lighting properties for two light sources
const GLfloat light0_ambient[] = { 0.2f, 0.2f, 0.2f, 1.0f };
const GLfloat light0_diffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };
const GLfloat light0_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
const GLfloat light0_position[] = { 1.0f, 1.0f, 2.0f, 0.0f };

const GLfloat light1_ambient[] = { 0.1f, 0.1f, 0.1f, 1.0f };
const GLfloat light1_diffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };
const GLfloat light1_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
const GLfloat light1_position[] = { -1.0f, 0.5f, -1.0f, 0.0f };

// Camera and door class instances
Camera camera(0.0, 0.2, 2.0,   // eye
              0.0, 0.2, 0.0,   // center
              0.0, 1.0, 0.0);  // up
Door door;

/**
* @brief Sets up OpenGL lighting for the scene.
*/
void setupLighting()
{
    glEnable(GL_LIGHTING);
    glEnable(GL_COLOR_MATERIAL);
    glEnable(GL_NORMALIZE);

    glLightfv(GL_LIGHT0, GL_AMBIENT, light0_ambient);
    glLightfv(GL_LIGHT0, GL_DIFFUSE, light0_diffuse);
    glLightfv(GL_LIGHT0, GL_SPECULAR, light0_specular);
    glLightfv(GL_LIGHT0, GL_POSITION, light0_position);
    glEnable(GL_LIGHT0);

    glLightfv(GL_LIGHT1, GL_AMBIENT, light1_ambient);
    glLightfv(GL_LIGHT1, GL_DIFFUSE, light1_diffuse);
    glLightfv(GL_LIGHT1, GL_SPECULAR, light1_specular);
    glLightfv(GL_LIGHT1, GL_POSITION, light1_position);
    glEnable(GL_LIGHT1);
}

/**
 * @brief Sets material properties for a diffuse (matte) surface.
 */
void setDiffuse()
{
    GLfloat diffuse_amb[] = { 0.2f, 0.2f, 0.2f, 1.0f };
    GLfloat diffuse_dif[] = { 0.8f, 0.0f, 0.0f, 1.0f };     // Red
    GLfloat diffuse_spec[] = { 0.0f, 0.0f, 0.0f, 1.0f };    // No specularity
    GLfloat diffuse_shin[] = { 0.0f };                      // No shininess

    glMaterialfv(GL_FRONT, GL_AMBIENT, diffuse_amb);
    glMaterialfv(GL_FRONT, GL_DIFFUSE, diffuse_dif);
    glMaterialfv(GL_FRONT, GL_SPECULAR, diffuse_spec);
    glMaterialf(GL_FRONT, GL_SHININESS, diffuse_shin[0]);
}

/**
 * @brief Sets material properties for a specular (glossy) surface.
 */
void setGlossy()
{
    GLfloat glossy_amb[] = { 0.2f, 0.2f, 0.2f, 1.0f };
    GLfloat glossy_dif[] = { 0.0f, 0.0f, 0.8f, 1.0f };          // Blue
    GLfloat glossy_spec[] = { 0.775f, 0.775f, 0.775f, 1.0f };   // High specularity
    GLfloat glossy_shin[] = { 50.0f };                          // High shine

    glMaterialfv(GL_FRONT, GL_AMBIENT, glossy_amb);
    glMaterialfv(GL_FRONT, GL_DIFFUSE, glossy_dif);
    glMaterialfv(GL_FRONT, GL_SPECULAR, glossy_spec);
    glMaterialf(GL_FRONT, GL_SHININESS, glossy_shin[0]);
}

/**
 * @brief Sets material properties for a metallic surface.
 */
void setMetallic()
{
    GLfloat metallic_amb[] = { 0.3f, 0.3f, 0.3f, 1.0f };    // Higher Ambience
    GLfloat metallic_dif[] = { 0.4f, 0.4f, 0.4f, 1.0f };    // Gray
    GLfloat metallic_spec[] = { 0.9f, 0.9f, 0.9f, 1.0f };   // High specularity
    GLfloat metallic_shin[] = { 125.0f };                   // Higher shine

    glMaterialfv(GL_FRONT, GL_AMBIENT, metallic_amb);
    glMaterialfv(GL_FRONT, GL_DIFFUSE, metallic_dif);
    glMaterialfv(GL_FRONT, GL_SPECULAR, metallic_spec);
    glMaterialf(GL_FRONT, GL_SHININESS, metallic_shin[0]);
}

/**
 * @brief Initializes OpenGL projection, lighting, and depth test.
 */
void init(void)
{
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(45.0, aspect, 0.1, 100.0);

    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();

    setupLighting();
    glEnable(GL_DEPTH_TEST);
}

/**
 * @brief Functions to load and draw the four imported models.
 * (Rose & Vase, Dolphins, Al, Porsche)
 */
void drawmodel_rosevase(void)
{
    if (!pmodel0)
    {
        // Model file assigned to model pointer
        pmodel0 = glmReadOBJ((char*)"data/rose+vase.obj");
        if (!pmodel0) exit(0);

        glmUnitize(pmodel0);
        glmFacetNormals(pmodel0);
        glmVertexNormals(pmodel0, 90.0);
    }

    glmDraw(pmodel0, GLM_SMOOTH | GLM_MATERIAL);
}

void drawmodel_dolphins(void)
{
    if (!pmodel1)
    {
        // Model file assigned to model pointer
        pmodel1 = glmReadOBJ((char*)"data/dolphins.obj");
        if (!pmodel1) exit(0);

        glmUnitize(pmodel1);
        glmFacetNormals(pmodel1);
        glmVertexNormals(pmodel1, 90.0);
    }

    glmDraw(pmodel1, GLM_SMOOTH | GLM_MATERIAL);
}

void drawmodel_al(void)
{
    if (!pmodel2)
    {
        // Model file assigned to model pointer
        pmodel2 = glmReadOBJ((char*)"data/al.obj");
        if (!pmodel2) exit(0);

        glmUnitize(pmodel2);
        glmFacetNormals(pmodel2);
        glmVertexNormals(pmodel2, 90.0);
    }

    glmDraw(pmodel2, GLM_SMOOTH | GLM_MATERIAL);
}

void drawmodel_porsche(void)
{
    if (!pmodel3)
    {
        // Model file assigned to model pointer
        pmodel3 = glmReadOBJ((char*)"data/porsche.obj");
        if (!pmodel3) exit(0);

        glmUnitize(pmodel3);
        glmFacetNormals(pmodel3);
        glmVertexNormals(pmodel3, 90.0);
    }

    glmDraw(pmodel3, GLM_SMOOTH | GLM_MATERIAL);
}

/**
 * @brief Timer callback to update the spin value for animated objects.
 * @param value: Required by GLUT timer callback.
 */
void TimerRotate(int value)
{
    spin = spin + 4;
    if (spin > 360.0)
        spin = spin - 360.0;

    glutPostRedisplay();
    glutTimerFunc(30, TimerRotate, 0);
}

/**
 * @brief Handles special key input (arrow keys) for camera rotation.
 * @param key: The special key pressed.
 * @param x: Required by GLUT callback signature.
 * @param y: Required by GLUT callback signature.
 */
void specialKeys(int key, int x, int y)
{
    switch (key)
    {
    case GLUT_KEY_LEFT: camera.rotateLeft(ROTATE_SPEED); break;
    case GLUT_KEY_RIGHT: camera.rotateRight(ROTATE_SPEED); break;
    case GLUT_KEY_UP:    camera.lookUp(ROTATE_SPEED); break;
    case GLUT_KEY_DOWN:  camera.lookDown(ROTATE_SPEED); break;
	default: break;
    }

    glutPostRedisplay();
}

/**
 * @brief Handles standard key input for camera movement and actions.
 * @param key The key pressed.
 * @param x: Required by GLUT callback signature.
 * @param y: Required by GLUT callback signature.
 */
void keyboard(unsigned char key, int x, int y)
{
    switch (key) 
    {
    case 'w': camera.forward(MOVE_SPEED); break;
    case 's': camera.backward(MOVE_SPEED); break;
    case 'a': camera.moveLeft(MOVE_SPEED); break;
    case 'd': camera.moveRight(MOVE_SPEED); break;
    case 'e': door.toggle(); break;
	case ' ': camera.jump(); break;
	case 27: exit(0);   //ESC key
	default: break;
    }

    glutPostRedisplay();
}

/**
 * @brief Draws all environment walls with lighting and material properties.
 */
void drawWalls()
{
    GLfloat wall_ambient[] = { 0.2f, 0.2f, 0.2f, 1.0f };
    GLfloat wall_diffuse[] = { 0.7f, 0.7f, 0.7f, 1.0f }; // Light grey
    GLfloat wall_specular[] = { 0.1f, 0.1f, 0.1f, 1.0f };
    GLfloat wall_shininess[] = { 15.0f };

    glMaterialfv(GL_FRONT, GL_AMBIENT, wall_ambient);
    glMaterialfv(GL_FRONT, GL_DIFFUSE, wall_diffuse);
    glMaterialfv(GL_FRONT, GL_SPECULAR, wall_specular);
    glMaterialf(GL_FRONT, GL_SHININESS, wall_shininess[0]);

    // ===== Front wall =====
    glBegin(GL_QUADS);
    glNormal3f(0.0f, 0.0f, 1.0f);
    glVertex3f(-2.0f, 0.0f, -4.0f);
    glVertex3f(2.0f, 0.0f, -4.0f);
    glVertex3f(2.0f, 0.5f, -4.0f);
    glVertex3f(-2.0f, 0.5f, -4.0f);
    glEnd();

	// ===== Back wall with window =====

    //Left of window
    glBegin(GL_QUADS);
    glNormal3f(0.0f, 0.0f, -1.0f);
    glVertex3f(-2.0f, 0.0f, 4.0f);
    glVertex3f(-0.3f, 0.0f, 4.0f);
    glVertex3f(-0.3f, 0.5f, 4.0f);
    glVertex3f(-2.0f, 0.5f, 4.0f);
    glEnd();

    // Right of window
    glBegin(GL_QUADS);
    glNormal3f(0.0f, 0.0f, -1.0f);
    glVertex3f(0.3f, 0.0f, 4.0f);
    glVertex3f(2.0f, 0.0f, 4.0f);
    glVertex3f(2.0f, 0.5f, 4.0f);
    glVertex3f(0.3f, 0.5f, 4.0f);
    glEnd();

    // Below window
    glBegin(GL_QUADS);
    glNormal3f(0.0f, 0.0f, -1.0f);
    glVertex3f(-0.3f, 0.0f, 4.0f);
    glVertex3f(0.3f, 0.0f, 4.0f);
    glVertex3f(0.3f, 0.15f, 4.0f);
    glVertex3f(-0.3f, 0.15f, 4.0f);
    glEnd();

    // Above window
    glBegin(GL_QUADS);
    glNormal3f(0.0f, 0.0f, -1.0f);
    glVertex3f(-0.3f, 0.35f, 4.0f);
    glVertex3f(0.3f, 0.35f, 4.0f);
    glVertex3f(0.3f, 0.5f, 4.0f);
    glVertex3f(-0.3f, 0.5f, 4.0f);
    glEnd();

    // ===== Left wall =====
    glBegin(GL_QUADS);
    glNormal3f(1.0f, 0.0f, 0.0f);
    glVertex3f(-2.0f, 0.0f, -4.0f);
    glVertex3f(-2.0f, 0.0f, 4.0f);
    glVertex3f(-2.0f, 0.5f, 4.0f);
    glVertex3f(-2.0f, 0.5f, -4.0f);
    glEnd();

    // ===== Right wall =====
    glBegin(GL_QUADS);
    glNormal3f(-1.0f, 0.0f, 0.0f);
    glVertex3f(2.0f, 0.0f, -4.0f);
    glVertex3f(2.0f, 0.0f, 4.0f);
    glVertex3f(2.0f, 0.5f, 4.0f);
    glVertex3f(2.0f, 0.5f, -4.0f);
    glEnd();

    // ===== Middle wall =====

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

/**
 * @brief Draws the environment ceiling.
 */
void drawCeiling()
{
    GLfloat ceiling_ambient[] = { 0.3f, 0.3f, 0.3f, 1.0f };
    GLfloat ceiling_diffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f }; // White
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

/**
 * @brief Draws the environment floor.
 */
void drawFloor()
{
    GLfloat floor_ambient[] = { 0.2f, 0.2f, 0.1f, 1.0f };
    GLfloat floor_diffuse[] = { 0.5f, 0.3f, 0.1f, 1.0f }; // Wood brown
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

/**
 * @brief Draws the door and its knob, applying rotation if open.
 */
void drawDoor()
{
    GLfloat door_ambient[] = { 0.2f, 0.2f, 0.2f, 1.0f };
    GLfloat door_diffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };    // White
    GLfloat door_specular[] = { 0.1f, 0.1f, 0.1f, 1.0f };
    GLfloat door_shininess[] = { 5.0f };

    glMaterialfv(GL_FRONT, GL_AMBIENT, door_ambient);
    glMaterialfv(GL_FRONT, GL_DIFFUSE, door_diffuse);
    glMaterialfv(GL_FRONT, GL_SPECULAR, door_specular);
    glMaterialf(GL_FRONT, GL_SHININESS, door_shininess[0]);

    glPushMatrix();

    glTranslatef(-0.2f, 0.0f, 0.0f);
    glRotatef(door.getAngle(), 0.0f, 1.0f, 0.0f);

    // ===== Door =====
    glBegin(GL_QUADS);
    glNormal3f(0.0f, 0.0f, -1.0f);
    glVertex3f(0.0f, 0.0f, 0.0f);
    glVertex3f(0.4f, 0.0f, 0.0f);
    glVertex3f(0.4f, 0.4f, 0.0f);
    glVertex3f(0.0f, 0.4f, 0.0f);
    glEnd();

    // ===== Door knob =====
    glPushMatrix();
    glTranslatef(0.35f, 0.2f, 0.03f);
    glColor3f(0.8f, 0.6f, 0.2f);    // Brass 
    glutSolidSphere(0.025f, 16, 16);
    glPopMatrix();

    glPopMatrix();
}

/**
 * @brief Draws three teapots with different material properties.
 */
void drawTeapots()
{
    // ===== Red diffuse teapot =====
    glPushMatrix();
    setDiffuse();
    glTranslatef(-0.2f, 0.15f, 1.0f);
    glutSolidTeapot(0.05f);
    glPopMatrix();

    // ===== Blue glossy teapot =====
    glPushMatrix();
    setGlossy();
    glTranslatef(0.0f, 0.15f, 1.0f);
    glutSolidTeapot(0.05f);
    glPopMatrix();

    // ===== Metallic teapot =====
    glPushMatrix();
    setMetallic();
    glTranslatef(0.2f, 0.15f, 1.0f);
    glutSolidTeapot(0.05f);
    glPopMatrix();
}

/**
 * @brief Draws a cube representing the table under the teapots.
 */
void drawTable()
{
    GLfloat table_ambient[] = { 0.3f, 0.2f, 0.1f, 1.0f };
	GLfloat table_diffuse[] = { 0.6f, 0.4f, 0.2f, 1.0f };   // Wood brown
    GLfloat table_specular[] = { 0.2f, 0.15f, 0.1f, 1.0f };
    GLfloat table_shininess[] = { 12.0f };

    glMaterialfv(GL_FRONT, GL_AMBIENT, table_ambient);
    glMaterialfv(GL_FRONT, GL_DIFFUSE, table_diffuse);
    glMaterialfv(GL_FRONT, GL_SPECULAR, table_specular);
    glMaterialf(GL_FRONT, GL_SHININESS, table_shininess[0]);

    glBegin(GL_QUADS);

    // ===== Top face =====
    glNormal3f(0.0f, 1.0f, 0.0f);
    glVertex3f(1.0f, 1.0f, -1.0f);
    glVertex3f(-1.0f, 1.0f, -1.0f);
    glVertex3f(-1.0f, 1.0f, 1.0f);
    glVertex3f(1.0f, 1.0f, 1.0f);

    // ===== Bottom face =====
    glNormal3f(0.0f, -1.0f, 0.0f);
    glVertex3f(1.0f, -1.0f, 1.0f);
    glVertex3f(-1.0f, -1.0f, 1.0f);
    glVertex3f(-1.0f, -1.0f, -1.0f);
    glVertex3f(1.0f, -1.0f, -1.0f);

    // ===== Front face =====
    glNormal3f(0.0f, 0.0f, 1.0f);
    glVertex3f(1.0f, 1.0f, 1.0f);
    glVertex3f(-1.0f, 1.0f, 1.0f);
    glVertex3f(-1.0f, -1.0f, 1.0f);
    glVertex3f(1.0f, -1.0f, 1.0f);

    // ===== Back face =====
    glNormal3f(0.0f, 0.0f, -1.0f);
    glVertex3f(1.0f, 1.0f, -1.0f);
    glVertex3f(-1.0f, 1.0f, -1.0f);
    glVertex3f(-1.0f, -1.0f, -1.0f);
    glVertex3f(1.0f, -1.0f, -1.0f);

    // ===== Left face =====
    glNormal3f(1.0f, 0.0f, 0.0f);
    glVertex3f(1.0f, 1.0f, -1.0f);
    glVertex3f(1.0f, -1.0f, -1.0f);
    glVertex3f(1.0f, -1.0f, 1.0f);
    glVertex3f(1.0f, 1.0f, 1.0f);

    // ===== Right Face =====
    glNormal3f(-1.0f, 0.0f, 0.0f);
    glVertex3f(-1.0f, 1.0f, -1.0f);
    glVertex3f(-1.0f, -1.0f, -1.0f);
    glVertex3f(-1.0f, -1.0f, 1.0f);
    glVertex3f(-1.0f, 1.0f, 1.0f);

    glEnd();
}

/**
 * @brief Functions that set up material and draws the imported objects.
 * (Rose & Vase, Dolphins, Al, Porsche)
 */
void drawImportRose()
{
    GLfloat mat_ambient[] = { 0.1f, 0.1f, 0.1f, 1.0f };
    GLfloat mat_specular[] = { 0.5f, 0.5f, 0.5f, 1.0f };
    GLfloat mat_shininess[] = { 30.0f };

    glMaterialfv(GL_FRONT, GL_AMBIENT, mat_ambient);
    glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
    glMaterialf(GL_FRONT, GL_SHININESS, mat_shininess[0]);

    glPushMatrix();
    glScalef(0.05, 0.05, 0.05);
    drawmodel_rosevase();
    glPopMatrix();
}

void drawImportDolphins()
{
    GLfloat mat_ambient[] = { 0.1f, 0.1f, 0.1f, 1.0f };
    GLfloat mat_specular[] = { 0.5f, 0.5f, 0.5f, 1.0f };
    GLfloat mat_shininess[] = { 30.0f };

    glMaterialfv(GL_FRONT, GL_AMBIENT, mat_ambient);
    glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
    glMaterialf(GL_FRONT, GL_SHININESS, mat_shininess[0]);

    glPushMatrix();
    glScalef(0.05, 0.05, 0.05);
    drawmodel_dolphins();
    glPopMatrix();
}

void drawImportAl()
{
    GLfloat mat_ambient[] = { 0.1f, 0.1f, 0.1f, 1.0f };
    GLfloat mat_specular[] = { 0.5f, 0.5f, 0.5f, 1.0f };
    GLfloat mat_shininess[] = { 30.0f };

    glMaterialfv(GL_FRONT, GL_AMBIENT, mat_ambient);
    glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
    glMaterialf(GL_FRONT, GL_SHININESS, mat_shininess[0]);

    glPushMatrix();
    glScalef(0.05, 0.05, 0.05);
    drawmodel_al();
    glPopMatrix();
}

void drawImportPorsche()
{
    GLfloat mat_ambient[] = { 0.1f, 0.1f, 0.1f, 1.0f };
    GLfloat mat_specular[] = { 0.5f, 0.5f, 0.5f, 1.0f };
    GLfloat mat_shininess[] = { 30.0f };

    glMaterialfv(GL_FRONT, GL_AMBIENT, mat_ambient);
    glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
    glMaterialf(GL_FRONT, GL_SHININESS, mat_shininess[0]);

    glPushMatrix();
    glScalef(0.05, 0.05, 0.05);
    drawmodel_porsche();
    glPopMatrix();
}

/**
 * @brief Main display function for the scene which updates every frame as needed.
 */
void display(void)
{
    camera.updateGravityAndJump();

    door.update();
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    glLoadIdentity();
    gluLookAt(camera.getEye()[0], camera.getEye()[1], camera.getEye()[2],
        camera.getCenter()[0], camera.getCenter()[1], camera.getCenter()[2],
        camera.getUp()[0], camera.getUp()[1], camera.getUp()[2]);

    glLightfv(GL_LIGHT0, GL_POSITION, light0_position);
    glLightfv(GL_LIGHT1, GL_POSITION, light1_position);

    drawFloor();
    drawCeiling();
    drawWalls();
    drawDoor();
    drawTeapots();

    glPushMatrix();
    glTranslatef(0.0f, 0.05f, 1.0f);
    glScalef(0.3f, 0.05f, 0.15f);
    drawTable();
    glPopMatrix();

    glPushMatrix();
    glTranslatef(0.25, 0.25, -2.0);
    glScalef(4.0, 4.0, 4.0);
    glRotatef(spin, 0, 1, 0);
    glCallList(importRose);
    glPopMatrix();

    glPushMatrix();
    glTranslatef(-0.25, 0.25, -2.0);
    glScalef(4.0, 4.0, 4.0);
    glRotatef(spin, 0, 1, 0);
    glCallList(importDolph);
    glPopMatrix();

    glPushMatrix();
    glTranslatef(0.25, 0.25, -3.0);
    glScalef(4.0, 4.0, 4.0);
    glRotatef(spin, 0, 1, 0);
    glCallList(importAl);
    glPopMatrix();

    glPushMatrix();
    glTranslatef(-0.25, 0.25, -3.0);
    glScalef(4.0, 4.0, 4.0);
    glRotatef(spin, 0, 1, 0);
    glCallList(importPorsche);
    glPopMatrix();

    glutSwapBuffers();

    if (!door.isOpen() && door.getAngle() != 0.0 ||
        door.isOpen() && door.getAngle() != 90.0) {
        glutPostRedisplay();
    }
}

/**
 * @brief Creates display lists for each imported object.
 */
void importObjects()
{
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
}

/**
 * @brief Program entry point. Initializes window, OpenGL, and main loop.
 */
int main(int argc, char** argv)
{
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);

    int screenWidth = glutGet(GLUT_SCREEN_WIDTH);
    int screenHeight = glutGet(GLUT_SCREEN_HEIGHT);
    const float targetAspect = 16.0f / 9.0f;

    windowWidth = screenWidth * 0.8;
    windowHeight = int(windowWidth / targetAspect);

    if (windowHeight > screenHeight * 0.8) {
        windowHeight = screenHeight * 0.8;
        windowWidth = int(windowHeight * targetAspect);
    }

    aspect = static_cast<float>(windowWidth) / static_cast<float>(windowHeight);

    int windowPosX = (screenWidth - windowWidth) / 2;
    int windowPosY = (screenHeight - windowHeight) / 2;

    glutInitWindowSize(windowWidth, windowHeight);
    glutInitWindowPosition(windowPosX, windowPosY);

    glutCreateWindow("A New Scene");

    init();
    glutDisplayFunc(display);
    glutSpecialFunc(specialKeys);
    glutKeyboardFunc(keyboard);
    importObjects();
    glutTimerFunc(0, TimerRotate, 0);
    glutMainLoop();
}