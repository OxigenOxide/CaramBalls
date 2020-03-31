precision mediump float;
varying vec4 v_color;
varying vec4 v_c;
varying vec2 v_texCoord0;
varying float v_r;
uniform sampler2D u_sampler2D;

void main(){
    vec4 color = vec4(v_c);

    float dst = sqrt(pow(float(.5 - v_texCoord0.x), 2.) + pow(float(.5 - v_texCoord0.y), 2.));

    if(dst > v_r)
        color.a = 0.;

    gl_FragColor = color;
}
