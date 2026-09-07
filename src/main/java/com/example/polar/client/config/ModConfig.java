package com.example.polar.client.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger("polar-mod/config");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_DIR = Paths.get("config/polar-mod");
    private static final Path CONFIG_FILE = CONFIG_DIR.resolve("config.json");
    
    // Client settings
    public int accentColorR = 100;
    public int accentColorG = 150;
    public int accentColorB = 255;
    public float guiTransparency = 0.8f;
    public float textTransparency = 1.0f;
    public String guiKeybind = "G";
    
    // Render settings
    public boolean showNametagsEnabled = true;
    public boolean showHealthEnabled = true;
    
    // Movement settings
    public boolean sprintEnabled = true;
    public String sprintKeybind = "LEFT_CONTROL";
    
    public void load() {
        try {
            if (Files.exists(CONFIG_FILE)) {
                String json = Files.readString(CONFIG_FILE);
                ModConfig loaded = GSON.fromJson(json, ModConfig.class);
                if (loaded != null) {
                    this.accentColorR = loaded.accentColorR;
                    this.accentColorG = loaded.accentColorG;
                    this.accentColorB = loaded.accentColorB;
                    this.guiTransparency = loaded.guiTransparency;
                    this.textTransparency = loaded.textTransparency;
                    this.guiKeybind = loaded.guiKeybind;
                    this.showNametagsEnabled = loaded.showNametagsEnabled;
                    this.showHealthEnabled = loaded.showHealthEnabled;
                    this.sprintEnabled = loaded.sprintEnabled;
                    this.sprintKeybind = loaded.sprintKeybind;
                    LOGGER.info("Loaded config from {}", CONFIG_FILE);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Failed to load config", e);
        }
    }
    
    public void save() {
        try {
            Files.createDirectories(CONFIG_DIR);
            String json = GSON.toJson(this);
            Files.writeString(CONFIG_FILE, json);
            LOGGER.info("Saved config to {}", CONFIG_FILE);
        } catch (IOException e) {
            LOGGER.error("Failed to save config", e);
        }
    }
    
    public int getAccentColor() {
        return (accentColorR << 16) | (accentColorG << 8) | accentColorB;
    }
}
