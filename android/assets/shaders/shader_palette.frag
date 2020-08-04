precision mediump float;
varying vec4 v_color;
varying vec2 v_texCoord0;
uniform sampler2D u_sampler2D;
varying vec4 v_color_0;
varying vec4 v_color_1;
varying vec4 v_color_2;
varying vec4 v_color_3;

// NO REAL PERFORMACE IMPACT

void main(){
    vec4 color = texture2D(u_sampler2D, v_texCoord0) * v_color;

    // gl_FragColor = texture2D(u_palette, vec2(texture2D(u_sampler2D,v_texCoord0).r, 0)); // idea

    if(color.a > 0.0){
        if(color.r == 1.0)
            color.rgb = v_color_3.rgb;
        else if(color.r > .65 && color.r < .70) // agree on 170/255
            color.rgb = v_color_2.rgb;
        else if (color.r == 0.0)
            color.rgb = v_color_0.rgb;
        else if (color.r > .24 && color.r < .31) // agree on 77/255
            color.rgb = v_color_1.rgb;
    }

    gl_FragColor = color;
}
