precision mediump float;
varying vec4 v_color;
varying vec2 v_texCoord0;
varying float v_random0;
varying float v_random1;
varying float v_height;
uniform sampler2D u_sampler2D;

void main(){
    vec4 color = vec4(0.,0.,0.,0.);
    //if(int(v_texCoord0.y*192.)%2==v_random0 && int(v_texCoord0.x*108.)%2==v_random1 )
    //    color.a=1.0;

    float x0=v_texCoord0.y * v_height;
    float x1=v_texCoord0.x * 108.;
    float div=2.;
        if(floor(x0 - 2. * floor(x0/2.)) == v_random0
        && floor(x1 - 2. * floor(x1/2.)) == v_random1)
                color.a=1.0;

                //color.a=1.0;

//color.a=1.0;
    //if(int(v_texCoord0.y*192+v_random)%50==1)
        //color.a=1.0;
    //color.a=1.0;
    gl_FragColor = color;
}
