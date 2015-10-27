#ifdef normalFlag
varying vec3 v_normal;
#endif //normalFlag

void main() {
	#if defined(normalFlag) 
		vec3 normal = v_normal * 0.5 + 0.5;
	#else
		vec3 normal = vec3(0.0);
	#endif // normalFlag

	gl_FragColor = vec4(normal.x, normal.y, normal.z, gl_FragCoord.z);
}
