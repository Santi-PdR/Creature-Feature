package net.atired.creaturefeature.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.atired.creaturefeature.CreatureFeature;
import net.atired.creaturefeature.client.renderers.models.PathogenEntityModel;
import net.atired.creaturefeature.entity.PathogenesisEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class PathogenEntityRenderer extends MobRenderer<PathogenesisEntity, PathogenEntityModel<PathogenesisEntity>> {
    private static final ResourceLocation PATHOGEN_LOCATION = CreatureFeature.getId("textures/entity/pathogen.png");

    public PathogenEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new PathogenEntityModel<>(context.bakeLayer(PathogenEntityModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(PathogenesisEntity machinationEntity) {
        return PATHOGEN_LOCATION;
    }

    public void vertex(PoseStack.Pose pose, VertexConsumer consumer, double x, double y, double z, float u, float v, int normalX, int normalY, int normalZ, int packedLight, float alpha) {
        consumer.vertex(pose.pose(), (float)x, (float)y, (float)z).color(1.0f,1.0f,1.0f,alpha).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(pose.normal(), (float)normalX, (float)normalZ, (float)normalY).endVertex();
    }

}
