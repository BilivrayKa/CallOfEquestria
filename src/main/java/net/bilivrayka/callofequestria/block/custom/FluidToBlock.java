package net.bilivrayka.callofequestria.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;

public class FluidToBlock extends Block {
    public static final IntegerProperty STEPS_TO_BREAK = IntegerProperty.create("steps_to_break", 0, 3);
    protected final Fluid fluid;
    public FluidToBlock(Fluid fluid, Properties pProperties) {
        super(pProperties);
        this.fluid = fluid;
        this.registerDefaultState(this.stateDefinition.any().setValue(STEPS_TO_BREAK, 3));
    }

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        int steps = pState.getValue(STEPS_TO_BREAK);
        if(pFallDistance >= 1){
            steps--;
        }
        if(steps <= 0){
            pLevel.destroyBlock(pPos,false);
            pLevel.setBlock(pPos, fluid.defaultFluidState().createLegacyBlock(), 3);
        } else {
            pLevel.setBlock(pPos, pState.setValue(STEPS_TO_BREAK, steps), 2);
        }
        pEntity.causeFallDamage(pFallDistance, 0.5F, pEntity.damageSources().fall());
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STEPS_TO_BREAK);
    }
}
