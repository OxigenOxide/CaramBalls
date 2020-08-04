attribute vec4 a_color;
attribute vec3 a_position;
attribute vec2 a_texCoord0;
uniform mat4 u_projTrans;
uniform float r;
uniform vec2 mid;
varying vec4 v_color;
varying vec2 v_texCoord0;
varying vec2 v_mid;
varying float v_r;

void main(){
    v_r = r;
    v_color = a_color;
    v_mid = mid;
    v_texCoord0 = a_texCoord0;
    gl_Position = u_projTrans * vec4(a_position, 1.);
}