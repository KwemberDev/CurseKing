package curseking.mobs.soundhelper;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class MovingEntitySound extends MovingSound {
    private final Entity entity;

    public MovingEntitySound(Entity entity, SoundEvent sound) {
        super(sound, SoundCategory.HOSTILE);
        this.entity = entity;
        this.repeat = true;
        this.repeatDelay = 0;
        this.xPosF = (float) entity.posX;
        this.yPosF = (float) entity.posY;
        this.zPosF = (float) entity.posZ;
        this.pitch = 2.0f;
        this.volume = 3.0f;
    }

    @Override
    public void update() {
        if (entity.isDead) {
            this.donePlaying = true;
        } else {
            this.xPosF = (float) entity.posX;
            this.yPosF = (float) entity.posY;
            this.zPosF = (float) entity.posZ;
        }
    }
}