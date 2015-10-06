#version 130
#define MAX_BLUR_RADIUS 4096

uniform sampler2D u_texture;
uniform int u_radius;
uniform vec2 u_direction; // (1,0)/(0,1) for x/y pass

varying vec2 v_uv;

vec4 incrementalGauss1D(
	sampler2D srcTex, 
	vec2 srcTexelSize, 
	vec2 origin,
	int radius,
	vec2 direction
) {

	int nSamples = clamp(u_radius, 1, int(MAX_BLUR_RADIUS)) / 2;
	
	if (nSamples == 0)
		return texture2D(srcTex, origin);
	
	float SIGMA = float(u_radius) / 8.0;
	float sig2 = SIGMA * SIGMA;
	const float TWO_PI	= 6.2831853071795;
	const float E			= 2.7182818284590;
		
//	set up incremental counter:
	vec3 gaussInc;
	gaussInc.x = 1.0 / (sqrt(TWO_PI) * SIGMA);
	gaussInc.y = exp(-0.5 / sig2);
	gaussInc.z = gaussInc.y * gaussInc.y;
	
//	accumulate results:
	vec4 result = texture2D(srcTex, origin) * gaussInc.x;	
	for (int i = 1; i < nSamples; ++i) {
		gaussInc.xy *= gaussInc.yz;
		
		vec2 offset = float(i) * direction * srcTexelSize;
		result += texture2D(srcTex, origin - offset) * gaussInc.x;
		result += texture2D(srcTex, origin + offset) * gaussInc.x;
	}
	
	return result;
}

void main() {
	vec2 texelSize = 1.0 / vec2(textureSize(u_texture, 0));
	gl_FragColor = incrementalGauss1D(u_texture, texelSize, v_uv, u_radius, u_direction); 
}