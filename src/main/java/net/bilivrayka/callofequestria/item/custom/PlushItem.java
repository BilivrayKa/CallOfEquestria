package net.bilivrayka.callofequestria.item.custom;

import net.bilivrayka.callofequestria.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
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

    public PlushItem(SoundEvent sound2, SoundEvent sound3, Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
        this.sound2 = sound2;
        this.sound3 = sound3;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        pPlayer.getCooldowns().addCooldown(this, 10);
        float pitch = rnd.nextFloat(1,1.15f);
        SoundEvent sound = ModSounds.STEREO_SQUEE1.get();

        switch (rnd.nextInt(3)+1){
            case 2 :
                sound = sound2;
                break;
            case 3 :
                sound = sound3;
                break;

        }
        /*
        pLevel.playSound(null, pPlayer.getX(),pPlayer.getY()+1,pPlayer.getZ(),
                sound, SoundSource.PLAYERS, 1.0F, pitch);

         */

        // если вдруг захочу зарегать все звуки дважды, моно и стерео
        // это позволит плюшам предметам воспроизводить звук в игроке, чтобы от звука нельзя было убежать
        // но впизду это делать сейчас
        if(!pLevel.isClientSide){
            pLevel.playSound(pPlayer, pPlayer.getOnPos(),
                    sound, SoundSource.PLAYERS, 1.0F, pitch);
        }
        //pPlayer.playSound(sound,1.0f, pitch);
        pLevel.playLocalSound(pPlayer.getOnPos(),sound,SoundSource.PLAYERS, 1.0f, pitch,true);


        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot slot, Entity entity) {
        return slot == EquipmentSlot.HEAD; // Разрешаем экипировку только на голову
    }
}
