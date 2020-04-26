attribute vec4 a_color;
 attribute vec3 a_position;
 attribute vec2 a_texCoord0;
 uniform mat4 u_projTrans;
 uniform float progress;
 uniform vec2 uv;
 varying float v_progress;
 varying vec4 v_color;
 varying vec2 v_uv;
 varying vec2 v_texCoord0;

 void main(){
     v_progress = progress;
     v_color = a_color;
     v_uv = uv;
     v_texCoord0 = a_texCoord0;
     gl_Position = u_projTrans * vec4(a_position, 1.);
 }