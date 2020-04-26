attribute vec4 a_color;
attribute vec3 a_position;
attribute vec2 a_texCoord0;
uniform mat4 u_projTrans;
uniform vec4 color_0;
uniform vec4 color_1;
uniform vec4 color_2;
uniform vec4 color_3;
varying vec4 v_color;
varying vec4 v_color_0;
varying vec4 v_color_1;
varying vec4 v_color_2;
varying vec4 v_color_3;
varying vec2 v_texCoord0;


void main(){
    v_color = a_color;
    v_color_0 = color_0;
    v_color_1 = color_1;
    v_color_2 = color_2;
    v_color_3 = color_3;
    v_texCoord0 = a_texCoord0;
    gl_Position = u_projTrans * vec4(a_position, 1.);
}