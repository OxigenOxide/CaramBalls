precision mediump float;
varying vec4 v_color;
varying vec2 v_texCoord0;
uniform sampler2D u_sampler2D;
varying float v_time;
varying vec2 v_texSize;

void main() {
    float invprox = 2.0*abs(v_texCoord0.x - .5);
    float prox = 1.0 - invprox;
    vec2 wavyCoord= vec2(v_texCoord0.x, v_texCoord0.y + prox * 3.0 / v_texSize.y * sin(v_time * .5 + v_texCoord0.x * v_texSize.x * .4));
    vec4 color = texture2D(u_sampler2D, wavyCoord) * v_color;
    //color = vec4(1,1,1,1);
    //vec4 color = vec4(1,1,1,1);
    gl_FragColor = color;
}
