attribute vec3 a_position;
uniform mat4 u_projViewWorldTrans;

#ifdef normalFlag
	attribute vec3 a_normal;
	uniform mat3 u_normalMatrix;
	varying vec3 v_normal;
#endif // normalFlag

void main() {
	#if defined(normalFlag)
		vec3 normal = normalize(u_normalMatrix * a_normal);
		v_normal = normal;
	#endif // normalFlag

	gl_Position = u_projViewWorldTrans * vec4(a_position, 1.0);
}
