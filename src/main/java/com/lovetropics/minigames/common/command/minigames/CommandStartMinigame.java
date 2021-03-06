package com.lovetropics.minigames.common.command.minigames;

import com.lovetropics.minigames.common.minigames.MinigameManager;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;

import static net.minecraft.command.Commands.literal;

public class CommandStartMinigame {
	public static void register(final CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(
			literal("minigame")
			.then(literal("start").requires(s -> s.hasPermissionLevel(2))
			.executes(c -> CommandMinigame.executeMinigameAction(() ->
					MinigameManager.getInstance().start(), c.getSource())))
		);
	}
}
