package com.example.polar.client.shader;

import net.minecraft.client.render.RenderSystem;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import com.example.polar.client.PolarClientMod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ShaderProgram {
    private int programId;
    private int vertexId;
    private int fragmentId;
    private String name;

    public ShaderProgram(String name) {
        this.name = name;
    }

    public static ShaderProgram create(String name, String vertexCode, String fragmentCode) throws IOException {
        ShaderProgram program = new ShaderProgram(name);
        program.compile(vertexCode, fragmentCode);
        return program;
    }

    public void compile(String vertexCode, String fragmentCode) throws IOException {
        RenderSystem.assertOnRenderThread();

        try {
            vertexId = compileShader(vertexCode, GL20.GL_VERTEX_SHADER);
            fragmentId = compileShader(fragmentCode, GL20.GL_FRAGMENT_SHADER);
            programId = GL20.glCreateProgram();

            GL20.glAttachShader(programId, vertexId);
            GL20.glAttachShader(programId, fragmentId);
            GL20.glLinkProgram(programId);

            if (GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == 0) {
                throw new RuntimeException("Shader program " + name + " failed to link: " + GL20.glGetProgramInfoLog(programId));
            }

            GL20.glDetachShader(programId, vertexId);
            GL20.glDetachShader(programId, fragmentId);

            PolarClientMod.LOGGER.info("Shader program '{}' compiled successfully", name);
        } catch (Exception e) {
            PolarClientMod.LOGGER.error("Failed to compile shader program '{}': {}", name, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static int compileShader(String code, int type) {
        int shader = GL20.glCreateShader(type);
        GL20.glShaderSource(shader, code);
        GL20.glCompileShader(shader);

        if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == 0) {
            String error = GL20.glGetShaderInfoLog(shader);
            GL20.glDeleteShader(shader);
            throw new RuntimeException("Shader compilation failed: " + error);
        }

        return shader;
    }

    public void use() {
        RenderSystem.assertOnRenderThread();
        GL20.glUseProgram(programId);
    }

    public void stop() {
        RenderSystem.assertOnRenderThread();
        GL20.glUseProgram(0);
    }

    public void setUniform1f(String name, float value) {
        int location = GL20.glGetUniformLocation(programId, name);
        if (location != -1) {
            GL20.glUniform1f(location, value);
        }
    }

    public void setUniform2f(String name, float x, float y) {
        int location = GL20.glGetUniformLocation(programId, name);
        if (location != -1) {
            GL20.glUniform2f(location, x, y);
        }
    }

    public void setUniform3f(String name, float x, float y, float z) {
        int location = GL20.glGetUniformLocation(programId, name);
        if (location != -1) {
            GL20.glUniform3f(location, x, y, z);
        }
    }

    public void setUniform4f(String name, float x, float y, float z, float w) {
        int location = GL20.glGetUniformLocation(programId, name);
        if (location != -1) {
            GL20.glUniform4f(location, x, y, z, w);
        }
    }

    public void setUniform1i(String name, int value) {
        int location = GL20.glGetUniformLocation(programId, name);
        if (location != -1) {
            GL20.glUniform1i(location, value);
        }
    }

    public void delete() {
        RenderSystem.assertOnRenderThread();
        GL20.glDeleteShader(vertexId);
        GL20.glDeleteShader(fragmentId);
        GL20.glDeleteProgram(programId);
    }

    public int getId() {
        return programId;
    }

    public String getName() {
        return name;
    }
}
