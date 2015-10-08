uniform sampler2D u_texture;
uniform sampler2D u_mainTexture;
uniform sampler2D u_lensDirtTexture;
uniform sampler2D u_lensStarTexture;
uniform mat3 u_lensStarMatrix; // camera rotation metric for starburst
uniform int u_flareOnly;

varying vec2 v_uv;

void main() {
	vec4 lensMod = texture2D(u_lensDirtTexture, v_uv) + vec4(0.2);
	vec2 lensStarTexcoord = (u_lensStarMatrix * vec3(v_uv, 1.0)).xy;
	lensMod += texture2D(u_lensStarTexture, lensStarTexcoord);
	vec4 lensFlare = texture2D(u_texture, v_uv) * lensMod;
	gl_FragColor = texture2D(u_mainTexture, v_uv) + lensFlare;
}