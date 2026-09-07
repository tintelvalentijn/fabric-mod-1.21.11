package com.example.polar.client.modules;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import com.example.polar.client.PolarClientMod;

public class RenderModule {
    private static boolean enabled = true;
    
    public static void init() {
        WorldRenderEvents.AFTER_ENTITIES.register((context) -> {
            if (!enabled || !PolarClientMod.CONFIG.showNametagsEnabled) {
                return;
            }
            
            var client = context.matrixStack();
            var world = context.world();
            
            // Render nametags for nearby players
            if (world != null && world.getPlayers() != null) {
                world.getPlayers().forEach(player -> {
                    if (player != context.camera().getFocusedEntity()) {
                        renderPlayerNametag(client, player, context.consumers());
                    }
                });
            }
        });
        
        PolarClientMod.LOGGER.info("Render Module initialized!");
    }
    
    private static void renderPlayerNametag(MatrixStack matrixStack, PlayerEntity player, VertexConsumerProvider consumers) {
        if (PolarClientMod.CONFIG.showHealthEnabled) {
            float health = player.getHealth();
            float maxHealth = player.getMaxHealth();
            Text text = Text.literal(player.getName().getString() + " " + (int)health + "/" + (int)maxHealth + " ❤");
            
            Vec3d pos = player.getPos();
            matrixStack.push();
            matrixStack.translate(pos.x, pos.y + player.getHeight() + 0.5, pos.z);
            // Rendering logic would go here
            matrixStack.pop();
        }
    }
    
    public static void setEnabled(boolean enabled) {
        RenderModule.enabled = enabled;
    }
}
