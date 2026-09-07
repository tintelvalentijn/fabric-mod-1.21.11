package com.example.polar.client.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;

import com.example.polar.client.gui.shader.ShaderRenderer;

public class SliderWidget {
    private int x;
    private int y;
    private int width;
    private int height;
    private String label;
    private double value;
    private boolean dragging = false;
    private float animationProgress = 0;
    
    public SliderWidget(int x, int y, int width, int height, String label, double initialValue) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
        this.value = MathHelper.clamp(initialValue, 0, 1);
    }
    
    public void render(DrawContext context, int mouseX, int mouseY) {
        // Update animation
        boolean hovered = this.isMouseOver(mouseX, mouseY);
        if (hovered) {
            this.animationProgress = Math.min(1, this.animationProgress + 0.1f);
        } else {
            this.animationProgress = Math.max(0, this.animationProgress - 0.1f);
        }
        
        // Draw background with rounded corners
        ShaderRenderer.drawRoundedRectangle(this.x, this.y, this.width, this.height, 3, 0xFF2a2a2a);
        
        // Draw filled portion with smooth animation
        int filledWidth = (int)(this.width * this.value);
        int sliderColor = 0xFF6496ff;
        int hoverColor = 0xFF7aabff;
        
        // Interpolate colors based on hover state
        int r = (int) MathHelper.lerp(this.animationProgress, (sliderColor >> 16) & 0xFF, (hoverColor >> 16) & 0xFF);
        int g = (int) MathHelper.lerp(this.animationProgress, (sliderColor >> 8) & 0xFF, (hoverColor >> 8) & 0xFF);
        int b = (int) MathHelper.lerp(this.animationProgress, sliderColor & 0xFF, hoverColor & 0xFF);
        int displayColor = 0xFF000000 | (r << 16) | (g << 8) | b;
        
        ShaderRenderer.drawRoundedRectangle(this.x, this.y, filledWidth, this.height, 3, displayColor);
        
        // Draw slider label and value
        String valueText = String.format("%.0f%%", this.value * 100);
        context.drawTextWithShadow(context.getMatrices(), context.getMatrices().peek().getModel().getValue(), this.label + ": " + valueText, this.x, this.y - 10, 0xFFFFFF);
    }
    
    public boolean mouseScrolled(double mouseX, double mouseY, double verticalAmount) {
        if (this.isMouseOver(mouseX, mouseY)) {
            this.value = MathHelper.clamp(this.value + verticalAmount * 0.01, 0, 1);
            return true;
        }
        return false;
    }
    
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isMouseOver(mouseX, mouseY) && button == 0) {
            this.dragging = true;
            this.updateValue(mouseX);
            return true;
        }
        return false;
    }
    
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.dragging) {
            this.dragging = false;
            return true;
        }
        return false;
    }
    
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.dragging) {
            this.updateValue(mouseX);
            return true;
        }
        return false;
    }
    
    private void updateValue(double mouseX) {
        this.value = MathHelper.clamp((mouseX - this.x) / (double)this.width, 0, 1);
    }
    
    private boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && 
               mouseY >= this.y && mouseY <= this.y + this.height;
    }
    
    public double getValue() {
        return this.value;
    }
    
    public void setValue(double value) {
        this.value = MathHelper.clamp(value, 0, 1);
    }
}
