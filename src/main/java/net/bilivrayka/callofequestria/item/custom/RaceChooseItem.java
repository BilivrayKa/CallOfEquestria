package net.bilivrayka.callofequestria.item.custom;

import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.gui.RaceChooseScreen;
import net.bilivrayka.callofequestria.magic.PlayerRaceDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.UUID;


public class RaceChooseItem extends Item {
    public RaceChooseItem(Properties pProperties) {
        super(pProperties);
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide) {
            Minecraft.getInstance().setScreen(new RaceChooseScreen());
        }
        return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pUsedHand), pLevel.isClientSide());
    }
}
