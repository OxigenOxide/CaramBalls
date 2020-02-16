precision mediump float;
varying vec4 v_color;
varying vec2 v_texCoord0;
varying float v_angle;
varying float v_intensity;
uniform sampler2D u_sampler2D;
void main(){
    vec4 color=vec4(0,0,0,0);
    float cx=-(cos(v_angle));
    float cy=-(sin(v_angle));
    if(cx<0.)
        cx=0.;
    if(cy<0.)
        cy=0.;
    color.a = cy + (1-v_texCoord0.y) * sin(v_angle) + cx + v_texCoord0.x * cos(v_angle);
    color.a *= v_intensity;

    //color.a=int(color.a*20./v_intensity)/(20./v_intensity);
    color.a=int(color.a*25.)/(25.);

    gl_FragColor = color;
}