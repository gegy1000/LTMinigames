package com.lovetropics.minigames.common.minigames.dimensions;

import com.lovetropics.minigames.common.map.generator.ConfiguredGenerators;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;

public class CollaborativeConservationDimension extends MinigameDimension {
	public CollaborativeConservationDimension(World world, DimensionType dimensionType) {
		super(world, dimensionType);
	}

	@Override
	public ChunkGenerator<?> createChunkGenerator() {
		return ConfiguredGenerators.TROPICS.createGenerator(world);
	}
}