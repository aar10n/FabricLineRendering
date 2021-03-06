package com.sandbox;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class Sandbox implements ModInitializer {
  public static final Logger LOGGER = LogManager.getLogger();

  private Vec3d start;
  private Vec3d end;

  @Override
  public void onInitialize() {
    System.out.println("Hello Fabric world!");

    LineRenderer lineRenderer = new LineRenderer();
    WorldRenderEvents.BEFORE_DEBUG_RENDER.register(ctx -> {
      Vec3d pos = ctx.camera().getPos();
      lineRenderer.render(ctx.matrixStack(), ctx.consumers(), pos.x, pos.y, pos.z);
    });

    CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> {
      dispatcher.register(
        CommandManager.literal("record").executes(ctx -> {
          if (start == null) {
            start = ctx.getSource().getPosition();
            ctx.getSource().sendFeedback(new LiteralText("Position 1: " + start.toString()), true);
          } else {
            end = ctx.getSource().getPosition();
            ctx.getSource().sendFeedback(new LiteralText("Position 2: " + end.toString()), true);

            Line line = new Line(start, end, Line.RED);
            lineRenderer.addLine(line);

            start = null;
            end = null;
          }

          return 1;
        })
      );
    }));
  }
}
