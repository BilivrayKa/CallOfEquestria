package net.bilivrayka.callofequestria.event;

import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;

public class AdvancementRewardHandler{

    public static void giveAdvancement(ServerPlayer player, ResourceLocation name) {
        MinecraftServer server = player.getServer();
        ServerAdvancementManager advancementManager = server.getAdvancements();
        var playerAdvancements = player.getAdvancements();

        Advancement advancement = advancementManager.getAdvancement(name);
        playerAdvancements.award(advancement, "requirement");
    }
}
