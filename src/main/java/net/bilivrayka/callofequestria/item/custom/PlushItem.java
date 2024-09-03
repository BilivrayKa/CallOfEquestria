package net.bilivrayka.callofequestria.item.custom;

import net.bilivrayka.callofequestria.sound.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.Random;

public class PlushItem extends BlockItem {

    private static final Random rnd = new Random();
    protected final SoundEvent sound2;
    protected final SoundEvent sound3;
    protected final SoundEvent stereo_sound2;
    protected final SoundEvent stereo_sound3;

    public PlushItem(SoundEvent sound2, SoundEvent sound3,
                     SoundEvent stereoSound2, SoundEvent stereoSound3, Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
        this.sound2 = sound2;
        this.sound3 = sound3;
        this.stereo_sound2 = stereoSound2;
        this.stereo_sound3 = stereoSound3;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        // У игроков звуки воспроизводятся разные
        //мб потом пофикшу
        float pitch = rnd.nextFloat(1,1.15f);
        SoundEvent sound = ModSounds.SQUEE1.get();
        SoundEvent stereoSound = ModSounds.STEREO_SQUEE1.get();
        switch (rnd.nextInt(3)+1){
            case 2 :
                sound = ModSounds.SQUEE2.get();
                stereoSound = ModSounds.STEREO_SQUEE2.get();
                break;
            case 3 :
                sound = ModSounds.SQUEE3.get();
                stereoSound = ModSounds.STEREO_SQUEE3.get();
                break;

        }
        switch (rnd.nextInt(3)+1){
            case 2 :
                sound = sound2;
                stereoSound = stereo_sound2;
                break;
            case 3 :
                sound = sound3;
                stereoSound = stereo_sound3;
                break;

        }
        if(!pLevel.isClientSide){
            pLevel.playSound(pPlayer, pPlayer.getOnPos(),
                    sound, SoundSource.PLAYERS, 1.0F, pitch);

        }
        pLevel.playLocalSound(pPlayer.getOnPos(),stereoSound,SoundSource.PLAYERS, 1.0f, pitch,true);
        pPlayer.getCooldowns().addCooldown(this, 10);
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot slot, Entity entity) {
        return slot == EquipmentSlot.HEAD;
    }
}
