package net.bilivrayka.callofequestria.item.custom;

import net.bilivrayka.callofequestria.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.Vec3;

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
        pPlayer.getCooldowns().addCooldown(this, 10);
        if(!pLevel.isClientSide){

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
            pLevel.playSound(pPlayer, pPlayer.getOnPos(),
                    sound, SoundSource.PLAYERS, 1.0F, pitch);

        }
        pLevel.playLocalSound(pPlayer.getOnPos(),stereoSound,SoundSource.PLAYERS, 1.0f, pitch,true);
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot slot, Entity entity) {
        return slot == EquipmentSlot.HEAD;
    }
}
