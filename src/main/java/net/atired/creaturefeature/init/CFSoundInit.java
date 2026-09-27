package net.atired.creaturefeature.init;

import net.atired.creaturefeature.CreatureFeature;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;

public class CFSoundInit {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, CreatureFeature.MODID);
    public static final RegistryObject<SoundEvent> NOTHING = SOUND_EVENTS.register(
            "music_disc.nothing",
            // Takes in the registry name
            () -> SoundEvent.createVariableRangeEvent(CreatureFeature.getId("music_disc.nothing"))
    );
    public static final RegistryObject<SoundEvent> NEW_AGE_NEVERMORE = SOUND_EVENTS.register(
            "music_disc.newagenevermore",
            // Takes in the registry name
            () -> SoundEvent.createVariableRangeEvent(CreatureFeature.getId("music_disc.newagenevermore"))
    );
    public static final RegistryObject<SoundEvent> SINISTER_HURT = SOUND_EVENTS.register(
            "entity.sinister_hurt",
            // Takes in the registry name
            () -> SoundEvent.createVariableRangeEvent(CreatureFeature.getId("entity.sinister_hurt"))
    );
    public static final RegistryObject<SoundEvent> SINISTER_DIE = SOUND_EVENTS.register(
            "entity.sinister_die",
            // Takes in the registry name
            () -> SoundEvent.createVariableRangeEvent(CreatureFeature.getId("entity.sinister_die"))
    );
    //SOURCED FROM FIEND FOLIO REHEATED!!! PLAY IT IT'S FUCKING AWESOME
    public static final RegistryObject<SoundEvent> FIEND_HURT = SOUND_EVENTS.register(
            "entity.fiend_hurt",
            // Takes in the registry name
            () -> SoundEvent.createVariableRangeEvent(CreatureFeature.getId("entity.fiend_hurt"))
    );
    public static final RegistryObject<SoundEvent> FIEND_DIE = SOUND_EVENTS.register(
            "entity.fiend_die",
            // Takes in the registry name
            () -> SoundEvent.createVariableRangeEvent(CreatureFeature.getId("entity.fiend_die"))
    );
    public static final RegistryObject<SoundEvent> SHOT = SOUND_EVENTS.register(
            "entity.shot",
            // Takes in the registry name
            () -> SoundEvent.createVariableRangeEvent(CreatureFeature.getId("entity.shot"))
    );
    public static final RegistryObject<SoundEvent> SG_RETREAT = SOUND_EVENTS.register(
            "entity.stained_glass_retreat",
            // Takes in the registry name
            () -> SoundEvent.createVariableRangeEvent(CreatureFeature.getId("entity.stained_glass_retreat"))
    );
    public static final RegistryObject<SoundEvent> SG_DIE = SOUND_EVENTS.register(
            "entity.stained_glass_die",
            // Takes in the registry name
            () -> SoundEvent.createVariableRangeEvent(CreatureFeature.getId("entity.stained_glass_die"))
    );
    public static final RegistryObject<SoundEvent> COA_HURT = SOUND_EVENTS.register(
            "entity.coa_hurt",
            // Takes in the registry name
            () -> SoundEvent.createVariableRangeEvent(CreatureFeature.getId("entity.coa_hurt"))
    );
    public static final RegistryObject<SoundEvent> FRIEND = SOUND_EVENTS.register(
            "entity.friend",
            // Takes in the registry name
            () -> SoundEvent.createVariableRangeEvent(CreatureFeature.getId("entity.friend"))
    );
    public static final RegistryObject<SoundEvent> FRIEND_HURT = SOUND_EVENTS.register(
            "entity.friend_hurt",
            // Takes in the registry name
            () -> SoundEvent.createVariableRangeEvent(CreatureFeature.getId("entity.friend_hurt"))
    );
}
