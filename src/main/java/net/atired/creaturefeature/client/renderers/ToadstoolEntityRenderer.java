package net.atired.creaturefeature.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.atired.creaturefeature.CreatureFeature;
import net.atired.creaturefeature.entity.ToadstoolEntity;
import net.atired.creaturefeature.entity.ToadstoolStoneProjectile;
import net.atired.creaturefeature.client.renderers.models.ToadstoolEntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;

public class ToadstoolEntityRenderer extends MobRenderer<ToadstoolEntity, ToadstoolEntityModel<ToadstoolEntity>> {
    private static final ResourceLocation TEXTURE = CreatureFeature.getId("textures/entity/toadstool.png");

    public ToadstoolEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new ToadstoolEntityModel<>(context.bakeLayer(ToadstoolEntityModel.LAYER_LOCATION)), 0.65F);
    }

    @Override
    public ResourceLocation getTextureLocation(ToadstoolEntity entity) {
        return TEXTURE;
    }

    public static class StoneProjectileRenderer extends EntityRenderer<ToadstoolStoneProjectile> {
        private final net.minecraft.client.renderer.block.BlockRenderDispatcher blocks;

        public StoneProjectileRenderer(EntityRendererProvider.Context context) {
            super(context);
            this.blocks = context.getBlockRenderDispatcher();
        }

        @Override
        public void render(ToadstoolStoneProjectile entity, float yaw, float partialTick, PoseStack poseStack,
                           MultiBufferSource buffer, int packedLight) {
            poseStack.pushPose();
            poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(180.0F - entity.getYRot()));
            poseStack.translate(-0.5D, 0.0D, -0.5D);
            blocks.renderSingleBlock(net.atired.creaturefeature.init.CFBlockInit.RUNIC_STONE_BRICKS.get().defaultBlockState(), poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
            poseStack.translate(0.0D, 1.0D, 0.0D);
            blocks.renderSingleBlock(Blocks.CHISELED_STONE_BRICKS.defaultBlockState(), poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
            poseStack.translate(0.0D, 1.0D, 0.0D);
            blocks.renderSingleBlock(net.atired.creaturefeature.init.CFBlockInit.RUNIC_STONE_BRICKS.get().defaultBlockState(), poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
            super.render(entity, yaw, partialTick, poseStack, buffer, packedLight);
        }

        @Override
        public ResourceLocation getTextureLocation(ToadstoolStoneProjectile entity) {
            return net.minecraft.client.renderer.texture.TextureAtlas.LOCATION_BLOCKS;
        }
    }
}
