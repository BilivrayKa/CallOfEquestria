package net.bilivrayka.callofequestria.event;

import com.mojang.logging.LogUtils;
import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.util.logging.LogManager;

public class AdvancementRewardHandler{



    public static void onAdvancement(AdvancementEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();

        if (event.getAdvancement().getId().getPath().equals("dash")) {
            AdvancementProgress progress = player.getAdvancements().getOrStartProgress(event.getAdvancement());

            if (progress.isDone()) {
                ItemStack rewardItem = new ItemStack(ModItems.PLUSH_RAINBOW_DASH.get());
                player.addItem(rewardItem);
            }
        }
    }
}
