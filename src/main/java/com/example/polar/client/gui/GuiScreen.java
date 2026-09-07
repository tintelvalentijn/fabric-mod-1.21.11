package com.example.polar.client.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import com.example.polar.client.PolarClientMod;

public class GuiScreen extends Screen {
    private int guiX;
    private int guiY;
    private int guiWidth = 300;
    private int guiHeight = 400;
    private float scrollOffset = 0;
    private SliderWidget redSlider;
    private SliderWidget greenSlider;
    private SliderWidget blueSlider;
    private SliderWidget guiTransparencySlider;
    private SliderWidget textTransparencySlider;
    
    public GuiScreen() {
        super(Text.literal("Polar Mod GUI"));
    }
    
    @Override
    protected void init() {
        super.init();
        this.guiX = (this.width - this.guiWidth) / 2;
        this.guiY = (this.height - this.guiHeight) / 2;
        
        // Initialize sliders
        this.redSlider = new SliderWidget(this.guiX + 20, this.guiY + 60, 260, 20, "Red", PolarClientMod.CONFIG.accentColorR / 255.0);
        this.greenSlider = new SliderWidget(this.guiX + 20, this.guiY + 90, 260, 20, "Green", PolarClientMod.CONFIG.accentColorG / 255.0);
        this.blueSlider = new SliderWidget(this.guiX + 20, this.guiY + 120, 260, 20, "Blue", PolarClientMod.CONFIG.accentColorB / 255.0);
        this.guiTransparencySlider = new SliderWidget(this.guiX + 20, this.guiY + 160, 260, 20, "GUI Transparency", PolarClientMod.CONFIG.guiTransparency);
        this.textTransparencySlider = new SliderWidget(this.guiX + 20, this.guiY + 190, 260, 20, "Text Transparency", PolarClientMod.CONFIG.textTransparency);
        
        // Add buttons
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Save"), (button) -> this.save())
            .dimensions(this.guiX + 20, this.guiY + this.guiHeight - 40, 130, 20)
            .build());
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Close"), (button) -> this.close())
            .dimensions(this.guiX + 150, this.guiY + this.guiHeight - 40, 130, 20)
            .build());
    }
    
    private void save() {
        PolarClientMod.CONFIG.accentColorR = (int)(this.redSlider.getValue() * 255);
        PolarClientMod.CONFIG.accentColorG = (int)(this.greenSlider.getValue() * 255);
        PolarClientMod.CONFIG.accentColorB = (int)(this.blueSlider.getValue() * 255);
        PolarClientMod.CONFIG.guiTransparency = (float)this.guiTransparencySlider.getValue();
        PolarClientMod.CONFIG.textTransparency = (float)this.textTransparencySlider.getValue();
        PolarClientMod.CONFIG.save();
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Draw semi-transparent background
        int bgColor = (int)(PolarClientMod.CONFIG.guiTransparency * 200) << 24 | 0x1a1a1a;
        context.fill(this.guiX, this.guiY, this.guiX + this.guiWidth, this.guiY + this.guiHeight, bgColor);
        
        // Draw border with accent color
        int accentColor = PolarClientMod.CONFIG.getAccentColor() | 0xFF000000;
        context.fill(this.guiX - 2, this.guiY - 2, this.guiX + this.guiWidth + 2, this.guiY + 2, accentColor);
        context.fill(this.guiX - 2, this.guiY + this.guiHeight - 2, this.guiX + this.guiWidth + 2, this.guiY + this.guiHeight + 2, accentColor);
        context.fill(this.guiX - 2, this.guiY, this.guiX + 2, this.guiY + this.guiHeight, accentColor);
        context.fill(this.guiX + this.guiWidth - 2, this.guiY, this.guiX + this.guiWidth + 2, this.guiY + this.guiHeight, accentColor);
        
        // Draw title
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("POLAR MOD"), this.guiX + this.guiWidth / 2, this.guiY + 15, 0xFFFFFF);
        
        super.render(context, mouseX, mouseY, delta);
        
        // Render sliders
        this.redSlider.render(context, mouseX, mouseY);
        this.greenSlider.render(context, mouseX, mouseY);
        this.blueSlider.render(context, mouseX, mouseY);
        this.guiTransparencySlider.render(context, mouseX, mouseY);
        this.textTransparencySlider.render(context, mouseX, mouseY);
    }
    
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (this.isMouseInGuiArea(mouseX, mouseY)) {
            this.scrollOffset = MathHelper.clamp(this.scrollOffset - verticalAmount * 10, 0, 100);
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }
    
    private boolean isMouseInGuiArea(double mouseX, double mouseY) {
        return mouseX >= this.guiX && mouseX <= this.guiX + this.guiWidth && 
               mouseY >= this.guiY && mouseY <= this.guiY + this.guiHeight;
    }
    
    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
