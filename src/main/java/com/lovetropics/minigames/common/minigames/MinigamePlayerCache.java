package com.lovetropics.minigames.common.minigames;

import com.lovetropics.minigames.common.dimension.DimensionUtils;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.FoodStats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameType;
import net.minecraft.world.dimension.DimensionType;

/**
 * Used to cache previous game type, dimension and position of player
 * before teleporting into minigame instance.
 *
 * Can use teleportBack() to reset them back to their previous state.
 */
public class MinigamePlayerCache {
    private GameType gameType;
    private DimensionType dimensionType;
    private BlockPos blockPos;
    private CompoundNBT cachedPlayerData;

    public MinigamePlayerCache(ServerPlayerEntity player) {
        this.gameType = player.interactionManager.getGameType();
        this.dimensionType = player.dimension;
        this.blockPos = new BlockPos(player.getPositionVec());

        this.cachedPlayerData = new CompoundNBT();
        player.writeAdditional(this.cachedPlayerData);
    }

    public void resetPlayerStats(ServerPlayerEntity player) {
        player.inventory.clear();
        player.setHealth(player.getMaxHealth());

        CompoundNBT foodTag = new CompoundNBT();
        new FoodStats().write(foodTag);
        player.getFoodStats().read(foodTag);

        player.clearActivePotions();
    }

    /**
     * Resets the player back to their previous state when this cache
     * was created.
     * @param player The player being reset.
     */
    public void teleportBack(ServerPlayerEntity player) {
        player.readAdditional(this.cachedPlayerData);
        player.setGameType(this.gameType);
        DimensionUtils.teleportPlayerNoPortal(player, this.dimensionType, this.blockPos);
    }
}