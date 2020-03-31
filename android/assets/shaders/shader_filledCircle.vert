attribute vec4 a_color;
attribute vec3 a_position;
attribute vec2 a_texCoord0;
uniform mat4 u_projTrans;
uniform float r;
uniform vec4 c;
varying vec4 v_color;
varying vec4 v_c;
varying vec2 v_texCoord0;
varying float v_r;

void main(){
    v_r = r;
    v_c = c;
    v_color = a_color;
    v_texCoord0 = a_texCoord0;
    gl_Position = u_projTrans * vec4(a_position, 1.);
}