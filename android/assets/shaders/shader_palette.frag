precision mediump float;
varying vec4 v_color;
varying vec2 v_texCoord0;
uniform sampler2D u_sampler2D;
varying vec4 v_color0;
varying vec4 v_color1;
varying vec4 v_color2;
varying vec4 v_color3;

void main(){
    vec4 color = texture2D(u_sampler2D, v_texCoord0) * v_color;

    if(color.a>0.0){
        if(color.r==1.0)
            color=v_color3;
        else if(color.r>.65&&color.r<.70) // agree on 170/255
            color=v_color2;
        else if (color.r==0.0)
            color=v_color0;
        else if (color.r>.24&&color.r<.31) // agree on 77/255
            color=v_color1;
    }

    gl_FragColor = color;
}
