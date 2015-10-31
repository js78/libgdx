uniform float u_tanHalfFov;
uniform float u_aspectRatio;

attribute vec2 a_position;

varying vec2 v_uv;
varying vec3 v_viewRay;

void main() {
	v_uv.xy = a_position.xy * 0.5 + 0.5;
	v_viewRay = vec3(
		a_position.x * u_tanHalfFov * u_aspectRatio,
		a_position.y * u_tanHalfFov, 
		1.0
	);
	
	gl_Position = vec4(a_position.x, a_position.y, 0.0, 1.0);
}
