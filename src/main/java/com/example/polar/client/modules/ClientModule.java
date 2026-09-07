package com.example.polar.client.modules;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import com.example.polar.client.PolarClientMod;
import com.example.polar.client.gui.GuiScreen;

public class ClientModule {
    private static KeyBinding guiKeybind;
    private static GuiScreen guiScreen;
    private static boolean guiOpen = false;
    
    public static void init() {
        // Register keybinding - RIGHT_SHIFT
        guiKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.polar-mod.gui",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.polar-mod"
        ));
        
        // Register tick callback
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (guiKeybind.wasPressed()) {
                guiOpen = !guiOpen;
                if (guiOpen) {
                    guiScreen = new GuiScreen();
                    client.setScreen(guiScreen);
                }
            }
        });
        
        PolarClientMod.LOGGER.info("Client Module initialized with RIGHT_SHIFT keybind!");
    }
    
    public static void openGui() {
        guiOpen = true;
    }
    
    public static void closeGui() {
        guiOpen = false;
    }
}
