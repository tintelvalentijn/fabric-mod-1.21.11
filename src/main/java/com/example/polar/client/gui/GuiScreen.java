package com.example.polar.client.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import com.example.polar.client.PolarClientMod;
import com.example.polar.client.gui.shader.GuiShader;
import com.example.polar.client.gui.shader.ShaderRenderer;
import com.example.polar.client.shader.ShaderManager;
import com.example.polar.client.font.FontManager;

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
        GuiShader.init();
        ShaderManager.initializeShaders();
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
        // Use custom shader for GUI rendering
        ShaderManager.useShader("gui");
        
        // Draw smooth background with rounded corners
        int bgColor = (int)(PolarClientMod.CONFIG.guiTransparency * 200) << 24 | 0x1a1a1a;
        ShaderRenderer.drawRoundedRectangle(this.guiX - 5, this.guiY - 5, this.guiWidth + 10, this.guiHeight + 10, 10, bgColor);
        
        // Draw gradient header
        int accentColor = PolarClientMod.CONFIG.getAccentColor() | 0xFF000000;
        ShaderRenderer.drawSmoothGradient(this.guiX, this.guiY, this.guiWidth, 40, accentColor, (accentColor & 0x00FFFFFF) | 0x80000000);
        
        ShaderManager.stopShader();
        
        // Draw title with Xuong font if available
        if (FontManager.isXuongFontLoaded()) {
            context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("POLAR MOD"), this.guiX + this.guiWidth / 2, this.guiY + 15, 0xFFFFFF);
        } else {
            context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("POLAR MOD"), this.guiX + this.guiWidth / 2, this.guiY + 15, 0xFFFFFF);
        }
        
        // Draw labels
        context.drawTextWithShadow(this.textRenderer, Text.literal("Accent Color (RGB)"), this.guiX + 20, this.guiY + 45, 0xFFFFFF);
        context.drawTextWithShadow(this.textRenderer, Text.literal("Transparency"), this.guiX + 20, this.guiY + 145, 0xFFFFFF);
        
        super.render(context, mouseX, mouseY, delta);
        
        // Render sliders with shader
        ShaderManager.useShader("gui");
        this.redSlider.render(context, mouseX, mouseY);
        this.greenSlider.render(context, mouseX, mouseY);
        this.blueSlider.render(context, mouseX, mouseY);
        this.guiTransparencySlider.render(context, mouseX, mouseY);
        this.textTransparencySlider.render(context, mouseX, mouseY);
        ShaderManager.stopShader();
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
    
    @Override
    public void close() {
        GuiShader.cleanup();
        ShaderManager.cleanup();
        super.close();
    }
}
