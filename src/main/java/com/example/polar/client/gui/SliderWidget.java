package com.example.polar.client.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class SliderWidget {
    private int x;
    private int y;
    private int width;
    private int height;
    private String label;
    private double value;
    private boolean dragging = false;
    
    public SliderWidget(int x, int y, int width, int height, String label, double initialValue) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
        this.value = MathHelper.clamp(initialValue, 0, 1);
    }
    
    public void render(DrawContext context, int mouseX, int mouseY) {
        // Draw background
        context.fill(this.x, this.y, this.x + this.width, this.y + this.height, 0xFF2a2a2a);
        
        // Draw filled portion
        int filledWidth = (int)(this.width * this.value);
        context.fill(this.x, this.y, this.x + filledWidth, this.y + this.height, 0xFF6496ff);
        
        // Draw label
        // Note: You'd need access to textRenderer to render text here
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
