attribute vec4 a_color;
attribute vec3 a_position;
attribute vec2 a_texCoord0;
uniform mat4 u_projTrans;
uniform float time;
uniform vec2 texSize;
varying vec4 v_color;
varying vec2 v_texCoord0;
varying float v_time;
varying vec2 v_texSize;

void main(){
    v_time=time;
    v_texSize=texSize;
    v_color = a_color;
    v_texCoord0 = a_texCoord0;
    gl_Position = u_projTrans * vec4(a_position, 1.);
}