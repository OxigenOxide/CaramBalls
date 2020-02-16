attribute vec4 a_color;
attribute vec3 a_position;
attribute vec2 a_texCoord0;
uniform mat4 u_projTrans;
uniform float random0;
uniform float random1;
uniform float height;
varying vec4 v_color;
varying vec2 v_texCoord0;
varying float v_random0;
varying float v_random1;
varying float v_height;

void main(){
    v_random0=random0;
    v_random1=random1;
    v_height=height;
    v_color = a_color;
    v_texCoord0 = a_texCoord0;
    gl_Position = u_projTrans * vec4(a_position, 1.);
}