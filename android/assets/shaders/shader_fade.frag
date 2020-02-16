precision mediump float;
varying vec4 v_color;
varying vec2 v_texCoord0;
varying float v_fade;
varying float v_dir;
varying vec2 v_screenSize;
uniform sampler2D u_sampler2D;


void main(){
    ivec2 v_xy = ivec2(v_texCoord0.x * v_screenSize.x, v_texCoord0.y * v_screenSize.y);

    vec4 color = vec4(0.,0.,0.,0.);
    float dst=18.;
    ivec2 v_c=ivec2(float(int((float(v_xy.x))/dst+.5))*dst, float(int((float(v_xy.y))/dst+.5))*dst);

    float r;
    if(v_dir<0.)
        r=(v_fade-float(v_xy.y)/v_screenSize.y)*12.8;
    else
        r=(v_fade-(1.-float(v_xy.y)/v_screenSize.y))*12.8;

        if(sqrt(pow(float(v_c.x-v_xy.x), 2.)+pow(float(v_c.y-v_xy.y), 2.))<r)
            color.a=1.;


    gl_FragColor = color;
}
