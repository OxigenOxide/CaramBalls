precision mediump float;
varying vec4 v_color;
varying vec2 v_texCoord0;
varying vec4 v_c;
uniform sampler2D u_sampler2D;
void main(){
    vec4 color = texture2D(u_sampler2D, v_texCoord0) * v_color;
    float a_inv = (1.0 - v_c.a);
    if(color.a > 0.){
        color = vec4(v_c.r * v_c.a + color.r * a_inv, v_c.g * v_c.a + color.g * a_inv, v_c.b * v_c.a + color.b * a_inv, 1);
    }
    gl_FragColor = color;
}
