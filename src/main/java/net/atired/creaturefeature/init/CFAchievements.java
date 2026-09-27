package net.atired.creaturefeature.init;

import net.atired.creaturefeature.CreatureFeature;
import net.atired.creaturefeature.misc.DummyTrigger;

public final class CFAchievements {
    private CFAchievements() {}

    public static final DummyTrigger SLEEPY = trigger("sleepy");
    public static final DummyTrigger FRIENDLESS = trigger("friendless");
    public static final DummyTrigger PUNCH_EVERYONE = trigger("punch_everyone");
    public static final DummyTrigger GYAS = trigger("gyas");
    public static final DummyTrigger SINISTER = trigger("sinister");
    public static final DummyTrigger PILL = trigger("pill");
    public static final DummyTrigger RENOVATION = trigger("renovation");
    public static final DummyTrigger NOBODY = trigger("nobody");

    private static DummyTrigger trigger(String path) {
        return new DummyTrigger(CreatureFeature.getId(path));
    }

    public static void register() {
        net.minecraft.advancements.CriteriaTriggers.register(SLEEPY);
        net.minecraft.advancements.CriteriaTriggers.register(FRIENDLESS);
        net.minecraft.advancements.CriteriaTriggers.register(PUNCH_EVERYONE);
        net.minecraft.advancements.CriteriaTriggers.register(GYAS);
        net.minecraft.advancements.CriteriaTriggers.register(SINISTER);
        net.minecraft.advancements.CriteriaTriggers.register(PILL);
        net.minecraft.advancements.CriteriaTriggers.register(RENOVATION);
        net.minecraft.advancements.CriteriaTriggers.register(NOBODY);
    }
}
