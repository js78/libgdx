uniform sampler2D u_texture;
uniform vec4 u_scale;
uniform vec4 u_bias;
varying vec2 v_uv;

void main() {	
	gl_FragColor = max(vec4(0.0), texture2D(u_texture, v_uv) + u_bias) * u_scale;
}