precision mediump float;
varying vec4 v_color;
varying vec2 v_texCoord0;
varying float v_intensity;
uniform sampler2D u_sampler2D;
void main(){
    float dst = sqrt(pow(v_texCoord0.x - .5,2.0) + pow(v_texCoord0.y -.5,2.0))-.4;
    if(dst<0.)
        dst=0.;
    //vec4 color=texture2D(u_sampler2D, vec2(.5 + (v_texCoord0.x - .5)* (1 - .5 * dst * v_intensity), .5 + (v_texCoord0.y - .5) * (1 - .5 * dst * v_intensity))) * v_color;
    vec4 color=texture2D(u_sampler2D, vec2(.5 + (v_texCoord0.x - .5)* (1. - .5 * dst * v_intensity), .5 + (v_texCoord0.y - .5) * (1. - .5 * dst * v_intensity))) * v_color;
    //vec4 color=vec4(1,1,1,1);
    gl_FragColor = color;
}
