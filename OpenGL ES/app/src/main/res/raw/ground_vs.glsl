//#version 130

// Attributes
attribute vec4 a_position;
attribute vec4 a_color;
attribute vec4 a_normal;
attribute vec2 a_texCoord;

// Vertex shader output
varying vec4 v_position;
varying vec4 v_color;
varying vec4 v_normal;
varying vec2 v_texCoord;

// Transformation matrices
uniform mat4 u_modelMatrix;
uniform mat4 u_viewMatrix;
uniform mat4 u_projectionMatrix;
uniform mat4 u_inverseTransposeMatrix;


void main() {
    gl_Position = u_projectionMatrix * u_viewMatrix * u_modelMatrix * a_position;

    v_position  = u_modelMatrix * a_position;
    v_color     = a_color;
    v_normal    = u_inverseTransposeMatrix * a_normal;
    v_texCoord  = a_texCoord;
}
