package com.example.polar.client.shader;

import net.minecraft.client.render.RenderSystem;
import com.example.polar.client.PolarClientMod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ShaderManager {
    private static final Map<String, ShaderProgram> shaders = new HashMap<>();
    private static ShaderProgram currentShader = null;

    public static void initializeShaders() {
        try {
            String textVertexShader = loadShaderFile("/assets/polar-mod/shaders/core/rendertype_text.vsh");
            String textFragmentShader = loadShaderFile("/assets/polar-mod/shaders/core/rendertype_text.fsh");
            ShaderProgram textShader = ShaderProgram.create("text", textVertexShader, textFragmentShader);
            shaders.put("text", textShader);

            String guiVertexShader = loadShaderFile("/assets/polar-mod/shaders/core/rendertype_gui.vsh");
            String guiFragmentShader = loadShaderFile("/assets/polar-mod/shaders/core/rendertype_gui.fsh");
            ShaderProgram guiShader = ShaderProgram.create("gui", guiVertexShader, guiFragmentShader);
            shaders.put("gui", guiShader);

            PolarClientMod.LOGGER.info("All shaders initialized successfully");
        } catch (IOException e) {
            PolarClientMod.LOGGER.error("Failed to initialize shaders", e);
        }
    }

    private static String loadShaderFile(String path) throws IOException {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(ShaderManager.class.getResourceAsStream(path)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        }
        return result.toString();
    }

    public static ShaderProgram getShader(String name) {
        return shaders.getOrDefault(name, null);
    }

    public static void useShader(String name) {
        ShaderProgram shader = getShader(name);
        if (shader != null) {
            shader.use();
            currentShader = shader;
        }
    }

    public static void stopShader() {
        if (currentShader != null) {
            currentShader.stop();
            currentShader = null;
        }
    }

    public static ShaderProgram getCurrentShader() {
        return currentShader;
    }

    public static void cleanup() {
        for (ShaderProgram shader : shaders.values()) {
            shader.delete();
        }
        shaders.clear();
    }
}
