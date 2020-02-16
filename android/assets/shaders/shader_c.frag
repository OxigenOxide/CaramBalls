precision mediump float;
varying vec4 v_color;
varying vec2 v_texCoord0;
varying vec4 v_c;
uniform sampler2D u_sampler2D;
void main(){
    vec4 color = texture2D(u_sampler2D, v_texCoord0) * v_color;
    if(color.a>0.){
    color = vec4(v_c.r, v_c.g, v_c.b, v_c.a);
    }
    gl_FragColor = color;
}
