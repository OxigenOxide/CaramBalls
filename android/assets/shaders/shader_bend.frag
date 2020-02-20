precision mediump float;
varying vec4 v_color;
varying vec2 v_texCoord0;
uniform sampler2D u_sampler2D;
varying float v_time;
varying float v_size;
varying float v_ampFactor;
varying vec2 v_texSize;

void main() {
    float invprox = 2.0 / v_size *abs(v_texCoord0.x - v_size * .5);
    float prox = 1.0 - invprox;
    //vec2 wavyCoord= vec2(v_texCoord0.x, v_texCoord0.y + prox * v_ampFactor * 5.0 / v_texSize.y * sin(v_time * .5 + v_texCoord0.x * v_texSize.x * .4));
    vec2 wavyCoord= vec2(v_texCoord0.x, v_texCoord0.y + prox * v_ampFactor * 5.0 / v_texSize.y * sin(v_time + v_texCoord0.x*16));
    vec4 color = texture2D(u_sampler2D, wavyCoord) * v_color;
    gl_FragColor = color;
}
