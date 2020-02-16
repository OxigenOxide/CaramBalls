attribute vec4 a_color;
attribute vec3 a_position;
attribute vec2 a_texCoord0;
uniform mat4 u_projTrans;
varying vec4 v_color;
varying vec2 v_texCoord0;

uniform float radius;
varying float v_radius;

uniform vec2 screenSize;
varying vec2 v_screenSize;

uniform vec2 center;
varying vec2 v_center;

uniform vec4 color0;
uniform vec4 color1;
uniform vec4 color2;
uniform vec4 color3;
varying vec4 v_color0;
varying vec4 v_color1;
varying vec4 v_color2;
varying vec4 v_color3;


void main(){
v_color = a_color;

    v_screenSize=screenSize;

    v_color0 = color0;
    v_color1 = color1;
        v_color2 = color2;
        v_color3 = color3;
    v_center = center;
    v_radius = radius;

    v_texCoord0 = a_texCoord0;
    gl_Position = u_projTrans * vec4(a_position, 1.);
}