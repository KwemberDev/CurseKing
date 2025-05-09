package curseking.config;

import curseking.CurseKing;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.common.Mod;

@Config(modid = CurseKing.MODID)
public class CurseKingConfig {

    @Config.Name("Default (Build-in) Curse Settings")
    public static final DefaultCurses defaultCurses = new DefaultCurses();

    @Config.Name("Default (Build-in) Blessing Settings")
    public static final DefaultBlessings defaultBlessings = new DefaultBlessings();

    @Config.Name("Mob Settings")
    public static final MobSettings mobSettings = new MobSettings();

    @Mod.EventBusSubscriber
    public static class DefaultCurses {

        @Config.Name("Curse Decay Health Decrease [INT]")
        public int DecayHealthDecrease = 4;

        @Config.Name("Curse Sloth MovementSpeed Decrease [FLOAT]")
        public double SlothMovementSpeedDecrease = 0.15;

        @Config.Name("Curse Sloth AttackSpeed Decrease [FLOAT]")
        public double SlothAttackSpeedDecrease = 0.1;

        @Config.Name("Curse Starving Hunger Decrease Amount [INT]")
        public int StarvingHungerDecrease = 1;

        @Config.Name("Curse Starving Saturation Decrease Amount [FLOAT]")
        public double StarvingSaturationDecrease = 1;

        @Config.Name("Curse Starving Trigger Timing [TICKS/INT] (once every {amnt} of ticks)")
        public int StarvingTriggerTiming = 300;

    }

    @Mod.EventBusSubscriber
    public static class DefaultBlessings {

        @Config.Name("Blessing IronSkin Health Increase [INT]")
        public int IronSkinHealthIncrease = 4;

        @Config.Name("Blessing Nimble MovementSpeed Increase [FLOAT]")
        public double NimbleMovementSpeedIncrease = 0.10;

        @Config.Name("Blessing Nimble AttackSpeed Increase [FLOAT]")
        public double NimbleAttackSpeedIncrease = 0.10;

        @Config.Name("Blessing Satiated Extra Hunger Fill [INT]")
        public int ExtraHungerFillIncrease = 1;

        @Config.Name("Blessing Satiated Extra Saturation FIll [FLOAT]")
        public double ExtraSaturationFillIncrease = 1;
    }

    @Mod.EventBusSubscriber
    public static class MobSettings {

        @Config.Name("Mob Cursed Spawn Chance")
        @Config.Comment("The chance a mob is cursed upon spawn")
        public float mobCursedSpawnChance = 0.02F;

        @Config.Name("Curse Chance Upon Cursed Mob Kill")
        @Config.Comment("The Chance to be cursed when killing a cursed mob")
        public float cursedMobKillChance = 0.5F;

        @Config.Name("Aqua Regius Boss stats.")
        public BossStats aquaRegiusBossStats = new BossStats();

        @Config.Name("The Fallen Health")
        public float TheFallenHealth = 60F;

        @Config.Name("The Fallen Movement Speed")
        public float TheFallenMovementSpeed = 0.33F;

        @Config.Name("The Fallen Attack Damage")
        public float TheFallenAttackDamage = 8F;

        @Config.Name("The Fallen Spawn Chance")
        public int TheFallenSpawnWeight = 50;

    }

    @Mod.EventBusSubscriber
    public static class BossStats {
        @Config.Name("Aqua Regius Health")
        public float aquaRegiusHealth = 300;

        @Config.Name("Aqua Regius Primary Attack Damage")
        public float primaryBossAttackDamage = 12F;

        @Config.Name("Aqua Regius Secondary Attack Damage")
        public float secondaryBossAttackDamage = 20F;

        @Config.Name("Aqua Regius Default (non-attacking) Movement Speed")
        public float aquaRegiusSpeed = 0.5F;

        @Config.Name("Aqua Regius Attacking Movement Speed")
        public float aquaRegiusAttackMovementSpeed = 2F;
    }
}
