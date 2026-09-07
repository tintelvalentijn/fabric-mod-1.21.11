package com.example.polar.client.modules;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import com.example.polar.client.PolarClientMod;

public class MovementModule {
    private static KeyBinding sprintKeybind;
    private static boolean sprintActive = false;
    
    public static void init() {
        // Register sprint keybinding
        sprintKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.polar-mod.sprint",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_CONTROL,
            "category.polar-mod"
        ));
        
        // Register tick callback for sprint
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!PolarClientMod.CONFIG.sprintEnabled) {
                return;
            }
            
            if (sprintKeybind.isPressed()) {
                sprintActive = true;
                if (client.player != null) {
                    client.player.setSprinting(true);
                }
            } else {
                sprintActive = false;
                if (client.player != null) {
                    client.player.setSprinting(false);
                }
            }
        });
        
        PolarClientMod.LOGGER.info("Movement Module initialized!");
    }
    
    public static boolean isSprintActive() {
        return sprintActive;
    }
}
