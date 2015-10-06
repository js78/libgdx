#version 130

uniform sampler2D u_texture;
uniform sampler2D u_lensColor;
uniform int u_samples;
uniform float u_dispersal;
uniform float u_haloWidth;
uniform float u_distortion;

varying vec2 v_uv;

const int MAX_SAMPLES = 64;

/*----------------------------------------------------------------------------*/
vec4 textureDistorted(
	sampler2D tex, 
	vec2 texcoord, 
	vec2 direction,
	vec3 distortion 
) {
	return vec4(
		texture2D(tex, texcoord + direction * distortion.r).r,
		texture2D(tex, texcoord + direction * distortion.g).g,
		texture2D(tex, texcoord + direction * distortion.b).b,
		1.0
	);
}

/*----------------------------------------------------------------------------*/
void main() {
	vec2 texcoord = -v_uv + vec2(1.0); // flip texcoordoords
	vec2 texelSize = 1.0 / vec2(textureSize(u_texture, 0));
	
	vec2 ghostVec = (vec2(0.5) - texcoord) * u_dispersal;
	vec2 haloVec = normalize(ghostVec) * u_haloWidth;
	
	vec3 distortion = vec3(-texelSize.x * u_distortion, 0.0, texelSize.x * u_distortion);

// sample ghosts:
	vec4 result = vec4(0.0);
	for (int i = 0; i < u_samples; ++i) {
		vec2 offset = fract(texcoord + ghostVec * float(i));
		
		float weight = length(vec2(0.5) - offset) / length(vec2(0.5));
		weight = pow(1.0 - weight, 10.0);
	
		result += textureDistorted(
			u_texture,
			offset,
			normalize(ghostVec),
			distortion
		) * weight;
	}
	
	result *= texture2D(u_lensColor, vec2((length(vec2(0.5) - texcoord) / length(vec2(0.5))), 0.0) );

//	sample halo:
	float weight = length(vec2(0.5) - fract(texcoord + haloVec)) / length(vec2(0.5));
	weight = pow(1.0 - weight, 10.0);
	result += textureDistorted(
		u_texture,
		fract(texcoord + haloVec),
		normalize(ghostVec),
		distortion
	) * weight;
	
	gl_FragColor = result;
}