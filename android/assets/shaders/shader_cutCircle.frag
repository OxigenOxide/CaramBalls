precision mediump float;
varying vec4 v_color;
varying vec2 v_texCoord0;
varying float v_r;
varying vec2 v_mid;
uniform sampler2D u_sampler2D;

void main(){
    vec4 color = texture2D(u_sampler2D, v_texCoord0) * v_color;

    float dst = sqrt(pow(float(v_mid.x - v_texCoord0.x), 2.) + pow(float(v_mid.y - v_texCoord0.y), 2.));

    if(dst > v_r)
        color.a = 0;

    gl_FragColor = color;
}
