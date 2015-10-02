attribute vec2 a_position;
varying vec2 v_uv;
void main() {
	v_uv.xy = a_position.xy*0.5+0.5;
	gl_Position = vec4(a_position.x, a_position.y, 0.0, 1.0);
}
