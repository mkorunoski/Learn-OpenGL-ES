#version 140

precision mediump float;

varying vec4 v_position;
varying vec4 v_color;
varying vec4 v_normal;

// ************************
// Ambient light parameters
// ************************
uniform vec4 al_color;

// ************************
// Diffuse light parameters
// ************************
uniform vec4 dl_color;
uniform vec4 dl_position;
uniform float dl_attenuation;

void main() {
    vec4 light_vector = normalize(dl_position - v_position);
    float diffuse = max(dot(normalize(v_normal), light_vector), 0.0f);
    float distance = length(dl_position - v_position);
    diffuse = diffuse * (1.0f / (1.0f + dl_attenuation * distance * distance));

    gl_FragColor = vec4(1.0f, 1.0f, 1.0f, 1.0f) * al_color * dl_color * diffuse;
}
