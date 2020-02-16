attribute vec4 a_color;
attribute vec3 a_position;
attribute vec2 a_texCoord0;
uniform mat4 u_projTrans;
uniform float fade;
uniform float dir;
varying float v_dir;
uniform vec2 screenSize;
varying vec4 v_color;
varying vec2 v_texCoord0;
varying vec2 v_screenSize;
varying float v_fade;

void main(){
    v_fade=fade;
    v_dir=dir;
    v_screenSize=screenSize;
    v_color = a_color;
    v_texCoord0 = a_texCoord0;
    gl_Position = u_projTrans * vec4(a_position, 1.);
}