#version 130

uniform sampler2D u_texture;
uniform sampler2D u_mainTexture;
uniform int u_blurSize;

varying vec2 v_uv;

float getOcclusionBlurred() {
	float result = 0.0;
	vec2 texelSize = 1.0 / vec2(textureSize(u_texture, 0));
	vec2 hlim = vec2(float(-u_blurSize) * 0.5 + 0.5);

	for (int x = 0; x < u_blurSize; ++x) {
		for (int y = 0; y < u_blurSize; ++y) {
			vec2 offset = vec2(float(x), float(y));
			offset += hlim;
			offset *= texelSize;
					
			result += texture2D(u_texture, v_uv + offset).a;
		}
	}
	return result / (u_blurSize * u_blurSize);
}

void main() {
	gl_FragColor = texture2D(u_mainTexture, v_uv) * getOcclusionBlurred();
}