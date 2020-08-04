precision mediump float;
varying vec4 v_color;
varying vec2 v_texCoord0;
varying vec2 v_uv;
varying float v_progress;
uniform sampler2D u_sampler2D;

#define PI 3.14

float atan2(float y, float x){
    return x == 0.0 ? sign(y) * PI / 2 : atan(y, x);
}

void main(){
    vec4 color = texture2D(u_sampler2D, v_texCoord0) * v_color;

    float ang = atan2(v_texCoord0.y - v_uv.y, v_texCoord0.x - v_uv.x);

    if(ang > PI * (v_progress * 2.0 - .5))
        color.a = 0.;
    else if(ang > PI * (v_progress * 2.0 - .5 - 2.) && ang < -.5 * PI)
        color.a = 0.;
    else if(color.a>0)
        color.a = v_progress;

    gl_FragColor = color;
}
