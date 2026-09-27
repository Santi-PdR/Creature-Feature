package net.atired.creaturefeature.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.atired.creaturefeature.CreatureFeature;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.client.event.RegisterShadersEvent;

import java.io.IOException;
import java.util.function.BiFunction;
import java.util.function.Function;

@EventBusSubscriber(modid = CreatureFeature.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CFRenderTypes extends RenderStateShard {
    private CFRenderTypes() {
        super("creaturefeature", () -> {}, () -> {});
    }

    public static ShaderInstance ARMOR_EVIL_SHADER_INSTANCE = null;
    public static ShaderInstance getRendertypeArmorCutoutEvilCullShader(){return ARMOR_EVIL_SHADER_INSTANCE;}
    public static final RenderStateShard.ShaderStateShard RENDERTYPE_ARMOR_CUTOUT_EVIL_CULL_SHADER = new RenderStateShard.
            ShaderStateShard(CFRenderTypes::getRendertypeArmorCutoutEvilCullShader);

    public static BiFunction<ResourceLocation,Integer,RenderType> ARMOR_CUTOUT_EVIL_CULL = Util.memoize((p_297924_,typed) -> {
        return createArmorCutoutEvilCull("armor_cutout_evil_cull", p_297924_, false,typed);
    });
    public static ResourceLocation[] TRIM_OVERLAYS = {
            CreatureFeature.getId("textures/trim_overlays/none.png"),
            CreatureFeature.getId("textures/trim_overlays/test_0.png"),
            CreatureFeature.getId("textures/trim_overlays/test_1.png"),
            CreatureFeature.getId("textures/trim_overlays/test_2.png"),
            CreatureFeature.getId("textures/trim_overlays/test_3.png")} ;
    private static RenderType createArmorCutoutEvilCull(String name, ResourceLocation id, boolean equalDepthTest,int overlaid) {
        RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder().setShaderState(RENDERTYPE_ARMOR_CUTOUT_EVIL_CULL_SHADER)
                .setTextureState(new RenderStateShard.EmptyTextureStateShard(()->{
                    TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
                    texturemanager.getTexture(id).setFilter(false, false);
                    RenderSystem.setShaderTexture(0, id);
                    RenderSystem.setShaderTexture(4,Minecraft.getInstance().getTextureManager().getTexture(TRIM_OVERLAYS[overlaid]).getId());
                },()->{}))
                .setTransparencyState(NO_TRANSPARENCY)
                .setCullState(NO_CULL)
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                .setDepthTestState(equalDepthTest ? EQUAL_DEPTH_TEST : LEQUAL_DEPTH_TEST).createCompositeState(true);
        return RenderType.create(name, DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, false, rendertype$compositestate);
    }


    public static ShaderInstance MONOCHROME_SHADER_INSTANCE = null;
    public static ShaderInstance getMonochromeShaderInstance(){return MONOCHROME_SHADER_INSTANCE;}
    public static final RenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_MONOCHROME_CULL_SHADER = new RenderStateShard.ShaderStateShard
            (CFRenderTypes::getMonochromeShaderInstance);
    public static final Function<ResourceLocation, RenderType> ENTITY_MONOCHROME_CULL = Util.memoize(
            p_286169_ -> {
                RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
                        .setShaderState(RENDERTYPE_ENTITY_MONOCHROME_CULL_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(p_286169_, false, false))
                        .setCullState(NO_CULL)
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setLightmapState(LIGHTMAP)
                        .setOverlayState(RenderStateShard.OVERLAY)
                        .createCompositeState(true);
                return RenderType.create("entity_monochrome", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, false, rendertype$compositestate);
            }
    );

    public static RenderType entityMonochromeCull(ResourceLocation location) {
        return ENTITY_MONOCHROME_CULL.apply(location);
    }
    public static ShaderInstance STAR_SHADER_INSTANCE = null;
    public static ShaderInstance getStarShaderInstance(){return STAR_SHADER_INSTANCE;}
    public static final RenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_STAR_CULL_SHADER = new RenderStateShard.ShaderStateShard
            (CFRenderTypes::getStarShaderInstance);
    public static final Function<ResourceLocation, RenderType> ENTITY_STAR_CULL = Util.memoize(
            p_286169_ -> {
                RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
                        .setShaderState(RENDERTYPE_ENTITY_STAR_CULL_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(p_286169_, false, false))
                        .setCullState(NO_CULL)
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setLightmapState(LIGHTMAP)
                        .setOverlayState(RenderStateShard.OVERLAY)
                        .createCompositeState(true);
                return RenderType.create("entity_star", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, false, rendertype$compositestate);
            }
    );
    public static ShaderInstance BLOSSOM_SHADER_INSTANCE = null;
    public static ShaderInstance getBlossomShaderInstance(){return BLOSSOM_SHADER_INSTANCE;}
    public static final RenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_BLOSSOM_CULL_SHADER = new RenderStateShard.ShaderStateShard
            (CFRenderTypes::getBlossomShaderInstance);
    public static final Function<ResourceLocation, RenderType> ENTITY_BLOSSOM_CULL = Util.memoize(
            p_286169_ -> {
                RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
                        .setShaderState(RENDERTYPE_ENTITY_BLOSSOM_CULL_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(p_286169_, false, false))
                        .setCullState(NO_CULL)
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setLightmapState(LIGHTMAP)
                        .setOverlayState(RenderStateShard.OVERLAY)
                        .createCompositeState(true);
                return RenderType.create("entity_blossom", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, false, rendertype$compositestate);
            }
    );

    public static RenderType entityStarCull(ResourceLocation location) {
        return ENTITY_STAR_CULL.apply(location);
    }
    public static RenderType entityBlossomCull(ResourceLocation location) {
        return ENTITY_BLOSSOM_CULL.apply(location);
    }


    public static ShaderInstance SILK_SHADER_INSTANCE = null;
    public static ShaderInstance getSilkShaderInstance(){return SILK_SHADER_INSTANCE;}
    public static final RenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_SILK_CULL_SHADER = new RenderStateShard.ShaderStateShard
            (CFRenderTypes::getSilkShaderInstance);
    private static final ResourceLocation SILK =CreatureFeature.getId("textures/entity/silk.png");
    private static final ResourceLocation CRIT =CreatureFeature.getId("textures/entity/crit2.png");
    public static final BiFunction<ResourceLocation,Boolean, RenderType> ENTITY_SILK_CULL = Util.memoize(
            (p_286169_,bool) -> {
                RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
                        .setShaderState(RENDERTYPE_ENTITY_SILK_CULL_SHADER)
                        .setTextureState(new RenderStateShard.EmptyTextureStateShard(()->{
                            TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
                            texturemanager.getTexture(p_286169_).setFilter(false, false);
                            RenderSystem.setShaderTexture(0, p_286169_);
                            if(!bool){
                                RenderSystem.setShaderTexture(4,Minecraft.getInstance().getTextureManager().getTexture(SILK).getId());
                            }else{
                                RenderSystem.setShaderTexture(4,Minecraft.getInstance().getTextureManager().getTexture(CRIT).getId());
                            }
                        },()->{}))
                        .setCullState(CULL)
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setLightmapState(NO_LIGHTMAP)
                        .setOverlayState(RenderStateShard.OVERLAY)
                        .createCompositeState(true);
                return RenderType.create("entity_silk", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, false, rendertype$compositestate);
            }
    );

    public static RenderType entitySilkCull(ResourceLocation location) {
        return ENTITY_SILK_CULL.apply(location,false);
    }
    public static RenderType entityCritCull(ResourceLocation location) {
        return ENTITY_SILK_CULL.apply(location,true);
    }
    public static ShaderInstance FRIEND_SHADER_INSTANCE = null;
    public static ShaderInstance getFriendShaderInstance(){return FRIEND_SHADER_INSTANCE;}
    public static final RenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_FRIEND_CUTOUT_SHADER = new RenderStateShard.ShaderStateShard
            (CFRenderTypes::getFriendShaderInstance);
    public static final Function<ResourceLocation, RenderType> ENTITY_FRIEND_CUTOUT = Util.memoize(
            p_286169_ -> {
                RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
                        .setShaderState(RENDERTYPE_ENTITY_FRIEND_CUTOUT_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(p_286169_, false, false))
                        .setCullState(NO_CULL)
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setLightmapState(NO_LIGHTMAP)
                        .setOverlayState(RenderStateShard.OVERLAY)
                        .createCompositeState(true);
                return RenderType.create("entity_friend", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, false, rendertype$compositestate);
            }
    );
    public static RenderType entityFriendCutout(ResourceLocation location) {
        return ENTITY_FRIEND_CUTOUT.apply(location);
    }
    public static ShaderInstance OUTLINED_SHADER_INSTANCE = null;
    public static ShaderInstance getOutlinedShaderInstance(){return OUTLINED_SHADER_INSTANCE;}
    public static final RenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_OUTLINED_CUTOUT_SHADER = new RenderStateShard.ShaderStateShard
            (CFRenderTypes::getOutlinedShaderInstance);
    public static final Function<ResourceLocation, RenderType> ENTITY_OUTLINED_CUTOUT = Util.memoize(
            p_286169_ -> {
                RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
                        .setShaderState(RENDERTYPE_ENTITY_OUTLINED_CUTOUT_SHADER)
                        .setTextureState(new RenderStateShard.EmptyTextureStateShard(()->{
                            if(Minecraft.getInstance().getMainRenderTarget()!=null)
                                RenderSystem.setShaderTexture(0,Minecraft.getInstance().getMainRenderTarget().getDepthTextureId());
                        },()->{}))
                        .setCullState(NO_CULL)
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setLightmapState(LIGHTMAP)
                        .setOverlayState(RenderStateShard.OVERLAY)
                        .createCompositeState(true);
                return RenderType.create("entity_outlined", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, false, rendertype$compositestate);
            }
    );
    public static RenderType entityOutlinedCutout(ResourceLocation location) {
        return ENTITY_OUTLINED_CUTOUT.apply(location);
    }
    public static ShaderInstance FEND_SHADER_INSTANCE = null;
    public static ShaderInstance getFendShaderInstance(){return FEND_SHADER_INSTANCE;}
    public static final RenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_FEND_CUTOUT_SHADER = new RenderStateShard.ShaderStateShard
            (CFRenderTypes::getFendShaderInstance);
    public static final Function<ResourceLocation, RenderType> ENTITY_FEND_CUTOUT = Util.memoize(
            p_286169_ -> {
                RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
                        .setShaderState(RENDERTYPE_ENTITY_FEND_CUTOUT_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(p_286169_, false, false))
                        .setCullState(NO_CULL)
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setLightmapState(LIGHTMAP)
                        .setOverlayState(RenderStateShard.OVERLAY)
                        .createCompositeState(true);
                return RenderType.create("entity_fend", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, false, rendertype$compositestate);
            }
    );

    public static RenderType entityFendCutout(ResourceLocation location) {
        return ENTITY_FEND_CUTOUT.apply(location);
    }
    public static ShaderInstance AMBUSH_SHADER_INSTANCE = null;
    public static ShaderInstance getAmbushShaderInstance(){return AMBUSH_SHADER_INSTANCE;}
    public static final RenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_AMBUSH_CUTOUT_SHADER = new RenderStateShard.ShaderStateShard
            (CFRenderTypes::getAmbushShaderInstance);
    public static final Function<ResourceLocation, RenderType> ENTITY_AMBUSH_CUTOUT = Util.memoize(
            p_286169_ -> {
                RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
                        .setShaderState(RENDERTYPE_ENTITY_AMBUSH_CUTOUT_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(p_286169_, false, false))
                        .setCullState(NO_CULL)
                        .setLightmapState(LIGHTMAP)
                        .setOverlayState(RenderStateShard.OVERLAY)
                        .createCompositeState(true);
                return RenderType.create("entity_ambush", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, false, rendertype$compositestate);
            }
    );
    public static final Function<ResourceLocation, RenderType> ENTITY_AMBUSH_UNLIT_CUTOUT = Util.memoize(
            p_286169_ -> {
                RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
                        .setShaderState(RENDERTYPE_ENTITY_AMBUSH_CUTOUT_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(p_286169_, false, false))
                        .setCullState(NO_CULL)
                        .setLightmapState(NO_LIGHTMAP)
                        .setOverlayState(RenderStateShard.OVERLAY)
                        .createCompositeState(true);
                return RenderType.create("entity_ambush", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, false, rendertype$compositestate);
            }
    );
    public static RenderType entityAmbushCutout(ResourceLocation location) {
        return ENTITY_AMBUSH_CUTOUT.apply(location);
    }
    public static RenderType entityUnlitAmbushCutout(ResourceLocation location) {
        return ENTITY_AMBUSH_UNLIT_CUTOUT.apply(location);
    }
    @SubscribeEvent
    public static void loadShaders(RegisterShadersEvent registerShadersEvent) throws IOException {
        registerShadersEvent.registerShader(
                new ShaderInstance(registerShadersEvent.getResourceProvider(), CreatureFeature.getId("rendertype_entity_outlined"), DefaultVertexFormat.NEW_ENTITY)
                ,(a)->{OUTLINED_SHADER_INSTANCE=a;});
        registerShadersEvent.registerShader(
                new ShaderInstance(registerShadersEvent.getResourceProvider(), CreatureFeature.getId("rendertype_entity_ambush"), DefaultVertexFormat.NEW_ENTITY)
                ,(a)->{AMBUSH_SHADER_INSTANCE=a;});
        registerShadersEvent.registerShader(
                new ShaderInstance(registerShadersEvent.getResourceProvider(), CreatureFeature.getId("rendertype_entity_friend"), DefaultVertexFormat.NEW_ENTITY)
                ,(a)->{FRIEND_SHADER_INSTANCE=a;});
        registerShadersEvent.registerShader(
                new ShaderInstance(registerShadersEvent.getResourceProvider(), CreatureFeature.getId("rendertype_entity_fend"), DefaultVertexFormat.NEW_ENTITY)
                ,(a)->{FEND_SHADER_INSTANCE=a;});
        registerShadersEvent.registerShader(
                new ShaderInstance(registerShadersEvent.getResourceProvider(), CreatureFeature.getId("rendertype_entity_silk"), DefaultVertexFormat.NEW_ENTITY)
                ,(a)->{SILK_SHADER_INSTANCE=a;});
        registerShadersEvent.registerShader(
                new ShaderInstance(registerShadersEvent.getResourceProvider(), CreatureFeature.getId("rendertype_armor_cutout_evil_cull"), DefaultVertexFormat.NEW_ENTITY)
                ,(a)->{ARMOR_EVIL_SHADER_INSTANCE=a;});
        registerShadersEvent.registerShader(
                new ShaderInstance(registerShadersEvent.getResourceProvider(), CreatureFeature.getId("rendertype_entity_blossom"), DefaultVertexFormat.NEW_ENTITY)
                ,(a)->{BLOSSOM_SHADER_INSTANCE=a;});
        registerShadersEvent.registerShader(
                new ShaderInstance(registerShadersEvent.getResourceProvider(), CreatureFeature.getId("rendertype_entity_star"), DefaultVertexFormat.NEW_ENTITY)
                ,(a)->{STAR_SHADER_INSTANCE=a;});
        registerShadersEvent.registerShader(
                new ShaderInstance(registerShadersEvent.getResourceProvider(), CreatureFeature.getId("rendertype_entity_monochrome"), DefaultVertexFormat.NEW_ENTITY)
                ,(a)->{MONOCHROME_SHADER_INSTANCE=a;});
    }
}
