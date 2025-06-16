package curseking.eventhandlers.mobeventhandlers;

import curseking.CurseDataProvider;
import curseking.CurseKing;
import curseking.ICurseData;
import curseking.ModPotions;
import curseking.config.CurseKingConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static curseking.eventhandlers.PlayerEventHandler.sendCurseDataToClient;

@Mod.EventBusSubscriber(modid = CurseKing.MODID)
public class MobEventHandler {

    private static final float cursedMobSpawnChance = CurseKingConfig.mobSettings.mobCursedSpawnChance;

    @SubscribeEvent
    public void onMobSpawn(LivingSpawnEvent.SpecialSpawn event) {
        if (!(event.getEntityLiving() instanceof IMob)) return;
        EntityLivingBase mob = event.getEntityLiving();

        // chance for a mob to be cursed on spawn.
        if (mob.world.rand.nextFloat() < CurseKingConfig.mobSettings.mobCursedSpawnChance) {
            int rand = Minecraft.getMinecraft().world.rand.nextInt(3);
            if (rand == 0) {
                PotionEffect effect = new PotionEffect(ModPotions.cursedSloth, Integer.MAX_VALUE, 0, false, false);
                mob.addPotionEffect(effect);
            }
            if (rand == 1) {
                PotionEffect effect = new PotionEffect(ModPotions.cursedDecay, Integer.MAX_VALUE, 0, false, false);
                mob.addPotionEffect(effect);
            }
            if (rand == 2) {
                PotionEffect effect = new PotionEffect(ModPotions.cursedStarving, Integer.MAX_VALUE, 0, false, false);
                mob.addPotionEffect(effect);
            }
        }
    }

    @SubscribeEvent
    public void onMobDeath(LivingDeathEvent event) {
        if (!(event.getEntityLiving() instanceof IMob)) return;

        EntityLivingBase mob = event.getEntityLiving();
        DamageSource source = event.getSource();

        if (!(source.getTrueSource() instanceof EntityPlayer)) return;

        EntityPlayer player = (EntityPlayer) source.getTrueSource();

        boolean hasCurse = mob.isPotionActive(ModPotions.cursedDecay)
                || mob.isPotionActive(ModPotions.cursedSloth)
                || mob.isPotionActive(ModPotions.cursedStarving);

        if (!hasCurse) return;

        ICurseData data = player.getCapability(CurseDataProvider.CURSE_DATA_CAP, null);
        if (data == null) return;

        if (Minecraft.getMinecraft().world.rand.nextFloat() < CurseKingConfig.mobSettings.cursedMobKillChance) {

            List<String> allCurses = Arrays.asList("curse_decay", "curse_starving", "curse_sloth");
            List<String> available = allCurses.stream()
                    .filter(c -> !data.hasCurse(c))
                    .collect(Collectors.toList());

            if (!available.isEmpty()) {
                String chosen = available.get(player.world.rand.nextInt(available.size()));
                data.addCurse(chosen);
                sendCurseDataToClient((EntityPlayerMP) player);

                player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 40, 0, false, false));

                player.sendStatusMessage(new TextComponentString(TextFormatting.DARK_GRAY + "Your soul feels heavy, you have been cursed."), false);

                player.world.playSound(
                        null,
                        player.posX, player.posY, player.posZ,
                        SoundEvents.ENTITY_WITHER_HURT,
                        SoundCategory.PLAYERS,
                        1.0F, // volume
                        0.5F  // pitch
                );
            }
        }
    }
}
