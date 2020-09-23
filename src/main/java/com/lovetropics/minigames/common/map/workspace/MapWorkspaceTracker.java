package com.lovetropics.minigames.common.map.workspace;

import com.lovetropics.minigames.Constants;
import com.lovetropics.minigames.common.network.LTNetwork;
import com.lovetropics.minigames.common.network.map.SetWorkspaceMessage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = Constants.MODID)
public final class MapWorkspaceTracker {
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		PlayerEntity player = event.getPlayer();
		if (player instanceof ServerPlayerEntity) {
			trySendWorkspace((ServerPlayerEntity) player, player.dimension);
		}
	}

	@SubscribeEvent
	public static void onChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
		PlayerEntity player = event.getPlayer();
		DimensionType dimension = event.getTo();
		trySendWorkspace((ServerPlayerEntity) player, dimension);
	}

	private static void trySendWorkspace(ServerPlayerEntity player, DimensionType dimension) {
		MinecraftServer server = player.world.getServer();
		MapWorkspaceManager workspaceManager = MapWorkspaceManager.get(server);

		MapWorkspace workspace = workspaceManager.getWorkspace(dimension);

		if (workspace != null) {
			SetWorkspaceMessage message = new SetWorkspaceMessage(workspace.getRegions());
			LTNetwork.CHANNEL.send(PacketDistributor.DIMENSION.with(() -> dimension), message);
		}
	}
}