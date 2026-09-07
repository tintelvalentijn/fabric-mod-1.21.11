package com.example.polar.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class SprintMixin {
    @Inject(method = "setSprinting", at = @At("HEAD"), cancellable = true)
    private void onSetSprinting(boolean sprinting, CallbackInfo ci) {
        // This allows the sprint module to control sprinting
    }
}
