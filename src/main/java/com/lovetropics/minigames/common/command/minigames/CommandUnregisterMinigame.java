package com.lovetropics.minigames.common.command.minigames;

import com.lovetropics.minigames.common.minigames.MinigameManager;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;

import static net.minecraft.command.Commands.literal;

public class CommandUnregisterMinigame {
	public static void register(final CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(
			literal("minigame")
			.then(literal("unregister")
			.requires(s -> s.getEntity() instanceof ServerPlayerEntity)
			.executes(c -> CommandMinigame.executeMinigameAction(() ->
					MinigameManager.getInstance().unregisterFor((ServerPlayerEntity) c.getSource().getEntity()), c.getSource())))
		);
	}
}
