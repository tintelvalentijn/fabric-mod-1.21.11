package com.example.polar.client.gui.shader;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.RenderPhase;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.example.polar.client.PolarClientMod;

public class GuiShader {
    private static int shaderProgram = -1;
    private static int vertexShader = -1;
    private static int fragmentShader = -1;
    private static boolean initialized = false;
    
    private static final String VERTEX_SHADER = "#version 150\n" +
        "\n" +
        "in vec3 Position;\n" +
        "in vec4 Color;\n" +
        "\n" +
        "out vec4 vertexColor;\n" +
        "\n" +
        "uniform mat4 ProjMat;\n" +
        "uniform mat4 ModelViewMat;\n" +
        "\n" +
        "void main()\n" +
        "{\n" +
        "    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);\n" +
        "    vertexColor = Color;\n" +
        "}";
    
    private static final String FRAGMENT_SHADER = "#version 150\n" +
        "\n" +
        "in vec4 vertexColor;\n" +
        "out vec4 FragColor;\n" +
        "\n" +
        "void main()\n" +
        "{\n" +
        "    FragColor = vertexColor;\n" +
        "}";
    
    public static void init() {
        if (initialized) return;
        
        try {
            vertexShader = compileShader(VERTEX_SHADER, GL20.GL_VERTEX_SHADER);
            fragmentShader = compileShader(FRAGMENT_SHADER, GL20.GL_FRAGMENT_SHADER);
            shaderProgram = GL20.glCreateProgram();
            
            GL20.glAttachShader(shaderProgram, vertexShader);
            GL20.glAttachShader(shaderProgram, fragmentShader);
            GL20.glLinkProgram(shaderProgram);
            
            if (GL20.glGetProgrami(shaderProgram, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
                PolarClientMod.LOGGER.error("Failed to link shader program: " + GL20.glGetProgramInfoLog(shaderProgram));
                return;
            }
            
            initialized = true;
            PolarClientMod.LOGGER.info("GUI Shader initialized successfully!");
        } catch (Exception e) {
            PolarClientMod.LOGGER.error("Failed to initialize GUI shader", e);
        }
    }
    
    private static int compileShader(String source, int type) {
        int shader = GL20.glCreateShader(type);
        GL20.glShaderSource(shader, source);
        GL20.glCompileShader(shader);
        
        if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException("Failed to compile shader: " + GL20.glGetShaderInfoLog(shader));
        }
        
        return shader;
    }
    
    public static void use() {
        if (shaderProgram != -1) {
            GL20.glUseProgram(shaderProgram);
        }
    }
    
    public static void stop() {
        GL20.glUseProgram(0);
    }
    
    public static void setFloat(String name, float value) {
        if (shaderProgram != -1) {
            int location = GL20.glGetUniformLocation(shaderProgram, name);
            if (location != -1) {
                GL20.glUniform1f(location, value);
            }
        }
    }
    
    public static void cleanup() {
        if (shaderProgram != -1) {
            GL20.glDeleteProgram(shaderProgram);
            GL20.glDeleteShader(vertexShader);
            GL20.glDeleteShader(fragmentShader);
            shaderProgram = -1;
            initialized = false;
        }
    }
    
    public static boolean isInitialized() {
        return initialized;
    }
}
