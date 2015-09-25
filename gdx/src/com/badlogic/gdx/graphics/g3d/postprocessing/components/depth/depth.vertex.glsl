attribute vec3 a_position;
uniform mat4 u_projViewWorldTrans;

void main() {
	gl_Position = u_projViewWorldTrans * vec4(a_position, 1.0);
}
