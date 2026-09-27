package net.atired.creaturefeature.mixin;

import net.atired.creaturefeature.accessors.LivingEntityGoopAccessor;
import net.atired.creaturefeature.accessors.PostChainDepthPassAccessor;
import net.atired.creaturefeature.client.CFClientProxy;
import net.atired.creaturefeature.client.CFRenderTypes;
import net.atired.creaturefeature.client.CreatureFeatureClient;
import net.atired.creaturefeature.accessors.GameRendererResourceManagerAccessor;
import net.atired.creaturefeature.accessors.PlayerBrainrotAccessor;
import net.atired.creaturefeature.client.renderers.FriendEntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class CFGameRendererMixin implements GameRendererResourceManagerAccessor {
    @Shadow @Final private ResourceManager resourceManager;
    @Inject(method = "renderLevel",at= @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V",ordinal = 2,shift= At.Shift.BEFORE))
    private void renderDepthCF(float partialTick, long nanoTime, com.mojang.blaze3d.vertex.PoseStack poseStack, CallbackInfo ci) {
        if(CreatureFeatureClient.RABIES_TARGET!=null){

        }

    }
    //
    @Inject(method = "render",at= @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V",ordinal = 0,shift= At.Shift.BEFORE))
    private void renderCFDepth(float partialTick, long nanoTime, boolean renderLevel, CallbackInfo ci){
        if (CreatureFeatureClient.SUN_TARGET2 == null || CreatureFeatureClient.SUN == null) return;
        CreatureFeatureClient.SUN_TARGET2.copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
        if(CreatureFeatureClient.SUN instanceof PostChainDepthPassAccessor accessor2){
            for(PostPass pass : accessor2.getDemPostPasses()){
                pass.getEffect().setSampler("DiffuseDepthSampler",CreatureFeatureClient.SUN_TARGET2::getDepthTextureId);

            }
        }
    }

        @Inject(method = "render",at= @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderTarget;bindWrite(Z)V",ordinal = 0,shift= At.Shift.BEFORE))
    private void renderCF(float partialTick, long nanoTime, boolean renderLevel, CallbackInfo ci){
        if(Minecraft.getInstance().levelRenderer!=null&&CreatureFeatureClient.RABIES_TARGET!=null&&
                CreatureFeatureClient.MINEDFLAYER !=null&&CreatureFeatureClient.FRIEND != null&&
                CreatureFeatureClient.SUN != null&&CreatureFeatureClient.FRIEND_TARGET != null&&
                CreatureFeatureClient.SUN_TARGET != null&&Minecraft.getInstance().player!=null&&
                Minecraft.getInstance().gameRenderer instanceof GameRendererResourceManagerAccessor accessor){

            PostChain chain2 = CreatureFeatureClient.FRIEND;
            chain2.process(partialTick);
            chain2 = CreatureFeatureClient.SUN;
            setUniform(chain2, "GameTime", Minecraft.getInstance().level.getGameTime()%24000);
            chain2.process(partialTick);
            if(Minecraft.getInstance().player instanceof PlayerBrainrotAccessor accessor1&&
                    Minecraft.getInstance().player instanceof LivingEntityGoopAccessor accessor2&&(accessor1.getBrainrot()>0.01f||accessor2.getGoop()>0.01f||CreatureFeatureClient.PROXY.flayed2>0)){
                PostChain chain = CreatureFeatureClient.MINEDFLAYER;
                setUniform(chain, "GameTime", Minecraft.getInstance().level.getGameTime()%24000);
                setUniform(chain, "FadeInTest", Math.max(Math.max(accessor1.getBrainrot(),net.atired.creaturefeature.misc.CFMath.clamp(accessor2.getGoop(),0.0f,0.33f)),Math.min(1.0f,CreatureFeatureClient.PROXY.flayed2*1.1f)));
                chain.process(partialTick);
            }
            if(Minecraft.getInstance().player instanceof PlayerBrainrotAccessor accessor1&&(accessor1.getRabies()>0.01f||CreatureFeatureClient.PROXY.rabies2>0)){

                PostChain chain = CreatureFeatureClient.RABIES;
                setUniform(chain, "GameTime", Minecraft.getInstance().level.getGameTime()%24000);
                setUniform(chain, "FadeInTest", Math.max(accessor1.getRabies(),Math.min(1.0f,CreatureFeatureClient.PROXY.rabies2*1.1f)));

                chain.process(partialTick);
            }
            if(CreatureFeatureClient.PROXY!=null&&CreatureFeatureClient.PROXY.gasLeak>0.0f){

                PostChain chain = CreatureFeatureClient.HAZE;
                setUniform(chain, "GameTime", Minecraft.getInstance().level.getGameTime()%24000);
                setUniform(chain, "FadeInTest", CreatureFeatureClient.PROXY.gasLeak);
                chain.process(partialTick);
            }
            if(CreatureFeatureClient.PROXY!=null&&CreatureFeatureClient.PROXY.bacterial>0.0f){

                PostChain chain = CreatureFeatureClient.BACTE;
                setUniform(chain, "GameTime", Minecraft.getInstance().level.getGameTime()%24000);
                setUniform(chain, "FadeInTest", CreatureFeatureClient.PROXY.bacterial);
                chain.process(partialTick);
            }
            if(CreatureFeatureClient.PROXY!=null&&CreatureFeatureClient.PROXY.manShader>0.0f){

                PostChain chain = CreatureFeatureClient.MAN;
                setUniform(chain, "GameTime", Minecraft.getInstance().level.getGameTime()%24000);
                setUniform(chain, "FadeInTest", CreatureFeatureClient.PROXY.manShader);
                chain.process(partialTick);
            }
            if(CreatureFeatureClient.PROXY!=null&&(CreatureFeatureClient.PROXY.fiendish>0.0f||CreatureFeatureClient.PROXY.fiendish2>0)){

                PostChain chain = CreatureFeatureClient.FIEND;
                setUniform(chain, "GameTime", Minecraft.getInstance().level.getGameTime()%24000);
                setUniform(chain, "FadeInTest", Math.max(CreatureFeatureClient.PROXY.fiendish,Math.min(1.0f,CreatureFeatureClient.PROXY.fiendish2*1.1f)));
                chain.process(partialTick);
            }
            if(CreatureFeatureClient.PROXY!=null&&CreatureFeatureClient.PROXY.eebyDeebyNess>0.0f){

                PostChain chain = CreatureFeatureClient.SLEEP;
                setUniform(chain, "GameTime", Minecraft.getInstance().level.getGameTime()%24000);
                setUniform(chain, "FadeInTest", CreatureFeatureClient.PROXY.eebyDeebyNess);
                chain.process(partialTick);
            }
            CreatureFeatureClient.FRIEND_TARGET.clear(Minecraft.ON_OSX);
            CreatureFeatureClient.SUN_TARGET.clear(Minecraft.ON_OSX);
            Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
        }
    }

    private static void setUniform(PostChain chain, String name, float value) {
        if (chain instanceof PostChainDepthPassAccessor accessor) {
            accessor.getDemPostPasses().forEach(pass -> pass.getEffect().safeGetUniform(name).set(value));
        }
    }
    @Override
    public ResourceManager creaturefeature$myPrecious() {
        return this.resourceManager;
    }
}
