//#version 130
precision mediump float;

const vec4 white = vec4(1.0f, 1.0f, 1.0f, 1.0f);

varying vec3 v_position;
varying vec3 v_color;
varying vec3 v_normal;
varying vec2 v_texCoord;

uniform sampler2D u_texture;

// Ambient light parameters
uniform vec4 al_color;

// Diffuse light parameters
uniform vec4 dl_color;
uniform vec4 dl_position;
uniform float dl_attenuation;

void main() {
    vec3 light_vector = normalize(vec3(dl_position) - v_position);
    float diff = max(dot(normalize(v_normal), light_vector), 0.0f);
    float distance = length(vec3(dl_position) - v_position);
    diff = diff * (1.0f / (1.0f + dl_attenuation * distance * distance));
    vec4 diffuse_color = dl_color * diff;

    gl_FragColor = white * texture2D(u_texture, v_texCoord) * (al_color + diffuse_color);
}
