//#version 130

// Attributes
attribute vec3 a_position;
attribute vec3 a_color;
attribute vec3 a_normal;
attribute vec2 a_texCoord;

// Vertex shader output
varying vec3 v_position;
varying vec3 v_color;
varying vec3 v_normal;
varying vec2 v_texCoord;

// Transformation matrices
uniform mat4 u_modelMatrix;
uniform mat4 u_viewMatrix;
uniform mat4 u_projectionMatrix;
uniform mat4 u_inverseTransposeMatrix;

void main() {
    v_position  = mat3(u_modelMatrix) * a_position;
    v_color     = a_color;
    v_normal    = mat3(u_inverseTransposeMatrix) * a_normal;
    v_texCoord  = a_texCoord;

    gl_Position = u_projectionMatrix * u_viewMatrix * u_modelMatrix * vec4(a_position, 1.0f);
}
