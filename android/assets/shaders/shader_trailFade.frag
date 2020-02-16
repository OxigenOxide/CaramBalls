precision mediump float;
varying vec4 v_color;
varying vec2 v_texCoord0;
varying float v_intensity;
uniform sampler2D u_sampler2D;
void main(){
    vec4 color=texture2D(u_sampler2D, vec2(v_texCoord0)) * v_color;
    if(color.a>.0)
        color.a-=.1f;
    gl_FragColor = color;
}
