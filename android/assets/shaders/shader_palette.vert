attribute vec4 a_color;
attribute vec3 a_position;
attribute vec2 a_texCoord0;
uniform mat4 u_projTrans;
uniform vec4 color0;
uniform vec4 color1;
uniform vec4 color2;
uniform vec4 color3;
varying vec4 v_color;
varying vec4 v_color0;
varying vec4 v_color1;
varying vec4 v_color2;
varying vec4 v_color3;
varying vec2 v_texCoord0;

void main(){
    v_color = a_color;
    v_color0 = color0;
    v_color1 = color1;
    v_color2 = color2;
    v_color3 = color3;
    v_texCoord0 = a_texCoord0;
    gl_Position = u_projTrans * vec4(a_position, 1.);
}