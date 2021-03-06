package com.sandbox;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class LineRenderer implements DebugRenderer.Renderer {
  public static final Logger LOGGER = LogManager.getLogger();
  private final ArrayList<Line> lines;

  public LineRenderer() {
    this.lines = new ArrayList<>();
  }

  public void addLine(Line line) {
    if (line == null) {
      return;
    }
    lines.add(line);
    LOGGER.info("Adding line from " + line.getStart() + " " + line.getEnd());
  }

  public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
    Vec3d camera = new Vec3d(cameraX, cameraY, cameraZ);
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferBuilder = tessellator.getBuffer();

    RenderSystem.disableTexture();
    RenderSystem.disableBlend();
    RenderSystem.lineWidth(3.0F);

    bufferBuilder.begin(3, VertexFormats.POSITION_COLOR);

    for (Line line : lines) {
      Vec3d s = line.getStart().subtract(camera);
      Vec3d e = line.getEnd().subtract(camera);
      Vector4f c = line.getColor();

      bufferBuilder.vertex(s.x, s.y, s.z).color(c.getX(), c.getY(), c.getZ(), c.getW()).next();
      bufferBuilder.vertex(e.x, e.y, e.z).color(c.getX(), c.getY(), c.getZ(), c.getW()).next();
    }

    tessellator.draw();

    RenderSystem.lineWidth(1.0F);
    RenderSystem.enableBlend();
    RenderSystem.enableTexture();
  }
}
