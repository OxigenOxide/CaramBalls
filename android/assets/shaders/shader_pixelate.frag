precision mediump float;
varying vec4 v_color;
varying vec2 v_texCoord0;
varying vec2 v_texDim;
uniform sampler2D u_sampler2D;
void main(){
    gl_FragColor = texture2D(u_sampler2D, vec2((floor(v_texCoord0.x * v_texDim.x) + 0.5) / v_texDim.x, (floor(v_texCoord0.y * v_texDim.y) + 0.5) / v_texDim.y)) * v_color;
}
