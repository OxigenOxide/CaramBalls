precision mediump float;
varying vec4 v_color;
varying vec2 v_texCoord0;
uniform sampler2D u_sampler2D;
varying vec4 v_newColor;

void main(){
    vec4 color = texture2D(u_sampler2D, v_texCoord0) * v_color;
    if(color.a > 0.0f){
      color = vec4(color.x * (1.0f - v_newColor.a) + v_newColor.x * v_newColor.a,color.y * (1.0f - v_newColor.a) + v_newColor.y * v_newColor.a,color.z * (1.0f - v_newColor.a) + v_newColor.z * v_newColor.a,1.0f);
    }

    gl_FragColor = color;
}