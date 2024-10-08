package net.bilivrayka.callofequestria.entity.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class MagicProjectileRenderer extends EntityRenderer<MagicProjectile> {
    private float tick;
    public MagicProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.tick = 40;
    }

    @Override
    public void render(MagicProjectile entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0, 0.0, 0.0);
        Vec3 lookVec = entity.getLookAngle();
        poseStack.mulPoseMatrix(createRotationMatrix(lookVec));
        ItemStack itemStack = entity.getItem();
        Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY , poseStack, bufferSource, entity.level(),0);
        poseStack.popPose();
        tick -= 1;
        if(tick <= 0){
            entity.level().addParticle(ParticleTypes.WITCH,
                    entity.position().x, entity.position().y + 0.5, entity.position().z,
                    0.5,0.5,0.5);
            tick = 10;
        }
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    private Matrix4f createRotationMatrix(Vec3 lookVec) {
        Vec3 vec3 = (new Vec3(lookVec.x, lookVec.y, lookVec.z));
        double d0 = vec3.horizontalDistance();
        float yaw = (float)(Mth.atan2(vec3.x, vec3.z) * 57.2957763671875);
        float pitch = (float)(Mth.atan2(vec3.y, d0) * 57.2957763671875);
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.rotate((float)Math.toRadians(-yaw), 0.0F, 1.0F, 0.0F);
        matrix.rotate((float)Math.toRadians(pitch), 1.0F, 0.0F, 0.0F);

        return matrix;
    }
    @Override
    public ResourceLocation getTextureLocation(MagicProjectile entity) {
        return new ResourceLocation("modid", "textures/entity/magic_projectile.png");
    }
}
