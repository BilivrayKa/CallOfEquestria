package net.bilivrayka.callofequestria.entity.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

import java.util.Set;

public class FloatingBlockRenderer extends EntityRenderer<FloatingBlockEntity> {

    //private BlockState state;
    public static final Logger LOGGER = LogUtils.getLogger();

    public FloatingBlockRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(FloatingBlockEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
        Set<String> tags = entity.getTags();
        BlockState state = null;
        for (String tag : tags) {
            if(tag.toString() != null){
                ResourceLocation blockId = new ResourceLocation(tag);
                Block block = BuiltInRegistries.BLOCK.get(blockId);
                state = block.defaultBlockState();
            }
        }
        if (state != null) {

        } else {
            state = Blocks.AIR.defaultBlockState();
        }
        poseStack.pushPose();
        poseStack.translate(-0.5, 0, -0.5); // Центрируем блок
        blockRenderer.renderSingleBlock(state, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(FloatingBlockEntity entity) {
        return null;
    }
}
