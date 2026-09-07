package com.example.polar.client.gui.shader;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;

public class ShaderRenderer {
    
    public static void drawRoundedRectangle(float x, float y, float width, float height, float radius, int color) {
        // Draw main rectangle
        drawRectangle(x + radius, y, width - 2 * radius, height, color);
        drawRectangle(x, y + radius, width, height - 2 * radius, color);
        
        // Draw rounded corners (approximated with smaller rectangles)
        drawCircleSegment(x + radius, y + radius, radius, color);
        drawCircleSegment(x + width - radius, y + radius, radius, color);
        drawCircleSegment(x + radius, y + height - radius, radius, color);
        drawCircleSegment(x + width - radius, y + height - radius, radius, color);
    }
    
    public static void drawRectangle(float x, float y, float width, float height, int color) {
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = (color & 0xFF) / 255.0f;
        float a = ((color >> 24) & 0xFF) / 255.0f;
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        buffer.vertex(x, y + height, 0).color(r, g, b, a).next();
        buffer.vertex(x + width, y + height, 0).color(r, g, b, a).next();
        buffer.vertex(x + width, y, 0).color(r, g, b, a).next();
        buffer.vertex(x, y, 0).color(r, g, b, a).next();
        
        tessellator.draw();
    }
    
    public static void drawCircleSegment(float x, float y, float radius, int color) {
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = (color & 0xFF) / 255.0f;
        float a = ((color >> 24) & 0xFF) / 255.0f;
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        
        int segments = 20;
        buffer.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        
        buffer.vertex(x, y, 0).color(r, g, b, a).next();
        
        for (int i = 0; i <= segments; i++) {
            double angle = (Math.PI / 2.0) * (i / (double) segments);
            float vx = (float) (x + Math.cos(angle) * radius);
            float vy = (float) (y + Math.sin(angle) * radius);
            buffer.vertex(vx, vy, 0).color(r, g, b, a).next();
        }
        
        tessellator.draw();
    }
    
    public static void drawSmoothGradient(float x, float y, float width, float height, int colorStart, int colorEnd) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        
        float r1 = ((colorStart >> 16) & 0xFF) / 255.0f;
        float g1 = ((colorStart >> 8) & 0xFF) / 255.0f;
        float b1 = (colorStart & 0xFF) / 255.0f;
        float a1 = ((colorStart >> 24) & 0xFF) / 255.0f;
        
        float r2 = ((colorEnd >> 16) & 0xFF) / 255.0f;
        float g2 = ((colorEnd >> 8) & 0xFF) / 255.0f;
        float b2 = (colorEnd & 0xFF) / 255.0f;
        float a2 = ((colorEnd >> 24) & 0xFF) / 255.0f;
        
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        buffer.vertex(x, y + height, 0).color(r1, g1, b1, a1).next();
        buffer.vertex(x + width, y + height, 0).color(r2, g2, b2, a2).next();
        buffer.vertex(x + width, y, 0).color(r2, g2, b2, a2).next();
        buffer.vertex(x, y, 0).color(r1, g1, b1, a1).next();
        
        tessellator.draw();
    }
}
