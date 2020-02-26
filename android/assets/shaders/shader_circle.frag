precision mediump float;
varying vec4 v_color;
varying vec2 v_texCoord0;
varying float v_r;
varying float v_width;
uniform sampler2D u_sampler2D;

void main(){
    vec4 color = vec4(1.,1.,1.,0.);

    float dst = sqrt(pow(float(.5 - v_texCoord0.x), 2.) + pow(float(.5 - v_texCoord0.y), 2.));

    if(dst < v_r && dst > v_r - v_width)
        color.a = 1.;

    gl_FragColor = color;
}
