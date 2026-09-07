#version 150

in vec4 vertexColor;
in vec2 texCoord0;
in vec3 Normal;

out vec4 FragColor;

uniform sampler2D Sampler0;
uniform vec4 ColorModulator;
uniform float SmoothAmount;

void main() {
    vec4 texColor = texture(Sampler0, texCoord0);
    vec4 baseColor = vertexColor * texColor * ColorModulator;
    
    // Apply smoothness based on uniform
    vec4 smoothedColor = mix(baseColor, vec4(1.0), SmoothAmount * 0.1);
    
    // Anti-aliasing at edges
    float alpha = smoothedColor.a;
    if (alpha < 0.01) {
        discard;
    }
    
    // Smooth edges
    smoothedColor.a = smoothstep(0.0, 0.1, alpha);
    
    FragColor = smoothedColor;
}
