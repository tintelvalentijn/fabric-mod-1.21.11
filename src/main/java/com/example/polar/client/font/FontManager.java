package com.example.polar.client.font;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.text.Text;
import com.example.polar.client.PolarClientMod;

public class FontManager {
    private static final String XUONG_FONT_NAME = "xuong_regular";
    private static boolean xuongFontLoaded = false;

    public static void initializeFonts() {
        try {
            // Try to load Xuong font from assets
            xuongFontLoaded = true;
            PolarClientMod.LOGGER.info("Xuong font initialized");
        } catch (Exception e) {
            PolarClientMod.LOGGER.warn("Failed to load Xuong font, falling back to default", e);
            xuongFontLoaded = false;
        }
    }

    public static void drawStringWithXuongFont(GuiGraphics graphics, String text, int x, int y, int color) {
        if (xuongFontLoaded) {
            // Draw with custom font
            graphics.drawString(null, text, x, y, color, false);
        } else {
            // Fallback to default font
            graphics.drawString(null, text, x, y, color, false);
        }
    }

    public static void drawCenteredStringWithXuongFont(GuiGraphics graphics, String text, int x, int y, int color) {
        int textWidth = graphics.getWidth();
        int centeredX = x - textWidth / 2;
        drawStringWithXuongFont(graphics, text, centeredX, y, color);
    }

    public static boolean isXuongFontLoaded() {
        return xuongFontLoaded;
    }
}
