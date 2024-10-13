package net.bilivrayka.callofequestria.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.bilivrayka.callofequestria.networking.ModMessages;
import net.bilivrayka.callofequestria.networking.packet.RaceS2CPacket;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class RaceChanger
{
    private static final SuggestionProvider<CommandSourceStack> RACE_SUGGESTIONS = (context, builder) -> {
        return net.minecraft.commands.SharedSuggestionProvider.suggest(new String[]{"Unicorn", "Pegasus", "EarthPony", "None"}, builder);
    };
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("ponified")
                        .requires(source -> source.hasPermission(1))
                        .then(Commands.literal("setrace")
                                .then(Commands.argument("player", EntityArgument.players())
                                        .then(Commands.argument("race", StringArgumentType.word())
                                                .suggests(RACE_SUGGESTIONS)
                                                .executes(context -> {
                                                    return setRace(context, EntityArgument.getPlayers(context, "player"), StringArgumentType.getString(context, "race"));
                                                })
                                        )
                                )
                        )
        );
    }
    private static int setRace(CommandContext<CommandSourceStack> context, Collection<ServerPlayer> players, String race) {
        for (ServerPlayer player : players) {
            if (!race.equals("Unicorn") && !race.equals("Pegasus") && !race.equals("EarthPony") && !race.equals("None")) {
                context.getSource().sendFailure(Component.literal("That race does not exist... Bruh..."));
                return 0;
            }
            int chosenRace;
            switch (race) {
                case "None":
                    chosenRace = 0;
                    break;
                case "EarthPony":
                    chosenRace = 1;
                    break;
                case "Pegasus":
                    chosenRace = 2;
                    break;
                case "Unicorn":
                    chosenRace = 3;
                    break;
                default:
                    chosenRace = 0;
            }
            ModMessages.sendToPlayer(new RaceS2CPacket(chosenRace), player);
            context.getSource().sendSuccess(() -> Component.literal("Race changed to: " + race), true);
        }
        return 1;
    }
}