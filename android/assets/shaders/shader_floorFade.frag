precision mediump float;
uniform sampler2D u_sampler2D;
varying vec4 v_color;
varying vec2 v_texCoord0;

varying vec2 v_center;
varying float v_radius;
varying vec2 v_screenSize;
varying vec4 v_color0;
varying vec4 v_color1;
varying vec4 v_color2;
varying vec4 v_color3;


void main(){
/*
    vec4 color = texture2D(u_sampler2D, v_texCoord0) * v_color;

    ivec2 v_xy = ivec2(v_texCoord0.x * v_screenSize.x, v_texCoord0.y * v_screenSize.y);

    ivec2 iv_center= ivec2(v_center); // android cant take it when subtracting ivec2 from vec2

     if(sqrt(pow(float(iv_center.x-v_xy.x), 2.)+pow(float(iv_center.y-v_xy.y), 2.))>v_radius){
            color.a=0.;
     }
     else{
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

    */
    // TODO: do this

        vec4 color = texture2D(u_sampler2D, v_texCoord0) * v_color;

        vec2 v_xy = vec2(v_texCoord0.x * v_screenSize.x, v_texCoord0.y * v_screenSize.y);

         if(sqrt(pow(float(v_center.x - v_xy.x), 2.) + pow(float(v_center.y - v_xy.y), 2.)) > v_radius){
                color.a = 0.;
         }
         else{
             if(color.r == 1.0)
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
