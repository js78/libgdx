#version 130

const int MAX_KERNEL_SIZE = 128;

// u_texture must contain normal (r,g,b) and depth (a)
uniform sampler2D u_texture;
uniform sampler2D u_noiseTexture;
uniform mat4 u_projectionMatrix;
uniform int u_kernelSize;
uniform vec3 u_kernelOffsets[MAX_KERNEL_SIZE];
uniform float u_radius;
uniform float u_power;

uniform float u_zNear;
uniform float u_zFar;

noperspective in vec3 v_viewRay; // required
noperspective in vec2 v_uv;

float linearizeDepth(float depth, mat4 projMatrix) {
	return projMatrix[3][2] / (depth - projMatrix[2][2]);
}

float linearizeDepth2(float depth) {
	depth = depth * 2.0 - 1.0;
    return 2.0 * u_zNear * u_zFar / (u_zFar + u_zNear - depth * (u_zFar - u_zNear));
}

float ssao(mat3 kernelBasis, vec3 originPos, float radius) {
	float occlusion = 0.0;
	for (int i = 0; i < u_kernelSize; ++i) {
		// get sample position:
		vec3 samplePos = kernelBasis * u_kernelOffsets[i];
		samplePos = samplePos * radius + originPos;
		
		// project sample position:
		vec4 offset = u_projectionMatrix * vec4(samplePos, 1.0);
		offset.xy /= offset.w; // only need xy
		offset.xy = offset.xy * 0.5 + 0.5; // scale/bias to texcoords
		
		// get sample depth:
		float sampleDepth = texture2D(u_texture, offset.xy).a;
		sampleDepth = linearizeDepth(sampleDepth, u_projectionMatrix);
		
		float rangeCheck = smoothstep(0.0, 1.0, radius / abs(originPos.z - sampleDepth));
		occlusion += rangeCheck * step(sampleDepth, samplePos.z);
	}
	
	occlusion = 1.0 - (occlusion / float(u_kernelSize));
	return pow(occlusion, u_power);
}

/*----------------------------------------------------------------------------*/
void main() {
	// get noise texture coords:
	vec2 noiseTexCoords = vec2(textureSize(u_texture, 0)) / vec2(textureSize(u_noiseTexture, 0));
	noiseTexCoords *= v_uv;
	
	// get view space origin:
	float originDepth = texture2D(u_texture, v_uv).a;
	
	originDepth = linearizeDepth(originDepth, u_projectionMatrix);
	gl_FragColor = vec4(originDepth*0.5+0.5, 0.0, 0.0, 1.0);
	vec3 originPos = v_viewRay * originDepth;

	// get view space normal:
	vec3 normal = texture2D(u_texture, v_uv).rgb * 2.0 - 1.0;
	normal = normalize(normal);
	
	// construct kernel basis matrix:
	vec3 rvec = texture2D(u_noiseTexture, noiseTexCoords).rgb * 2.0 - 1.0;
	vec3 tangent = normalize(rvec - normal * dot(rvec, normal));
	vec3 bitangent = cross(tangent, normal);
	mat3 kernelBasis = mat3(tangent, bitangent, normal);
	
	//gl_FragColor = vec4(ssao(kernelBasis, originPos, u_radius));
}