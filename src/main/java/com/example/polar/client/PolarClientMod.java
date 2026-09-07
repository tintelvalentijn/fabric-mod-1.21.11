package com.example.polar.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import com.example.polar.client.config.ModConfig;
import com.example.polar.client.modules.ClientModule;
import com.example.polar.client.modules.RenderModule;
import com.example.polar.client.modules.MovementModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class PolarClientMod implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("polar-mod/client");
    public static ModConfig CONFIG;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing Polar Client Mod!");
        
        // Initialize config
        CONFIG = new ModConfig();
        CONFIG.load();
        
        // Initialize modules
        ClientModule.init();
        RenderModule.init();
        MovementModule.init();
        
        LOGGER.info("Polar Client Mod initialized successfully!");
    }
}
