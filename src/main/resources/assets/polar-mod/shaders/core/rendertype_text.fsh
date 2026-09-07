#version 150

in vec4 vertexColor;
in vec2 texCoord0;

out vec4 FragColor;

uniform sampler2D Sampler0;
uniform vec4 ColorModulator;

void main() {
    vec4 texColor = texture(Sampler0, texCoord0);
    vec4 finalColor = vertexColor * texColor * ColorModulator;
    
    // Apply smoothing and anti-aliasing
    if (finalColor.a < 0.1) {
        discard;
    }
    
    FragColor = finalColor;
}
