#version 140

// Attributes
attribute vec4 a_position;
attribute vec4 a_color;
attribute vec4 a_normal;

// Vertex shader output
varying vec4 v_position;
varying vec4 v_color;
varying vec4 v_normal;

// Transformation matrices
uniform mat4 u_modelMatrix;
uniform mat4 u_viewMatrix;
uniform mat4 u_projectionMatrix;
uniform mat4 u_inverseTransposeMatrix;

// Can't set the uniform values via UBO because of exception that glBufferData() was throwing.
// layout (std140) uniform AmbientLight {
//     vec4 al_Color;
// };

void main() {
    gl_Position = u_projectionMatrix * u_viewMatrix * u_modelMatrix * a_position;

    v_position  = u_modelMatrix * a_position;
    v_color     = a_color;
    v_normal    = u_inverseTransposeMatrix * a_normal;
}
