package net.atired.creaturefeature.client.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/** Compatibility renderer for the rotated particle quads used by Creature Feature. */
public abstract class CFTextureParticle extends TextureSheetParticle {
    protected CFTextureParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z);
    }

    protected CFTextureParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);
    }

    protected void renderRotatedQuad(VertexConsumer buffer, Camera camera, Quaternionf rotation, float partialTick) {
        Vec3 cameraPos = camera.getPosition();
        float x = (float)(Mth.lerp(partialTick, this.xo, this.x) - cameraPos.x());
        float y = (float)(Mth.lerp(partialTick, this.yo, this.y) - cameraPos.y());
        float z = (float)(Mth.lerp(partialTick, this.zo, this.z) - cameraPos.z());
        float scale = 0.1F * this.getQuadSize(partialTick);
        int light = this.getLightColor(partialTick);
        emit(buffer, rotation, x, y, z, scale, -1, -1, this.getU0(), this.getV1(), light);
        emit(buffer, rotation, x, y, z, scale, -1,  1, this.getU0(), this.getV0(), light);
        emit(buffer, rotation, x, y, z, scale,  1,  1, this.getU1(), this.getV0(), light);
        emit(buffer, rotation, x, y, z, scale,  1, -1, this.getU1(), this.getV1(), light);
    }

    private void emit(VertexConsumer buffer, Quaternionf rotation, float x, float y, float z,
                      float scale, float px, float py, float u, float v, int light) {
        Vector3f corner = new Vector3f(px, py, 0).mul(scale);
        rotation.transform(corner);
        buffer.vertex(x + corner.x(), y + corner.y(), z + corner.z())
                .uv(u, v)
                .color((int)(this.rCol * 255.0F), (int)(this.gCol * 255.0F),
                        (int)(this.bCol * 255.0F), (int)(this.alpha * 255.0F))
                .uv2(light >> 16 & 65535, light & 65535)
                .endVertex();
    }
}
