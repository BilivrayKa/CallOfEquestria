package net.bilivrayka.callofequestria.block.custom;

import net.bilivrayka.callofequestria.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

public class PlushBaseBlock extends Block {
    public static final BooleanProperty POWERED;
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static SoundEvents sound4;
    protected final SoundEvent sound2;
    protected final SoundEvent sound3;

    private static final Random rnd = new Random();
    private static final VoxelShape SHAPE =
            Block.box(6, 0, 3, 10, 7, 12);
    private static final VoxelShape SHAPE2 =
            Block.box(3, 0, 6, 12, 7, 10);



    public PlushBaseBlock(SoundEvent sound2, SoundEvent sound3, BlockBehaviour.Properties properties) {
        super(properties.sound(new SoundType(1.0F, rnd.nextFloat(1.25f,1.35f),
                rnd.nextInt(2)+1 == 1 ? sound2 : sound3, SoundEvents.WOOL_STEP,
                rnd.nextInt(2)+1 == 1 ? sound2 : sound3, SoundEvents.WOOL_HIT, SoundEvents.WOOL_FALL)));
        this.sound2 = sound2;
        this.sound3 = sound3;

        this.registerDefaultState((BlockState)this.stateDefinition.any().setValue(POWERED, false));
    }


    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        if (pLevel.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        playSound(pLevel,pPos);
        /*
        boolean powered = pState.getValue(BlockStateProperties.POWERED);
        pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.POWERED, !powered), 3);

         */

        return InteractionResult.CONSUME;
    }

    public void playSound(Level pLevel, BlockPos pPos){
        float pitch = rnd.nextFloat(1,1.15f);
        SoundEvent sound = ModSounds.SQUEE1.get();
        switch (rnd.nextInt(3)+1){
            case 2 :
                sound = ModSounds.SQUEE2.get();
                break;
            case 3 :
                sound = ModSounds.SQUEE3.get();
                break;

        }
        switch (rnd.nextInt(3)+1){
            case 2 :
                sound = sound2;
                break;
            case 3 :
                sound = sound3;
                break;

        }
        pLevel.playSound(null, pPos,
                sound, SoundSource.BLOCKS, 1.0F, pitch);
    }
    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        Direction $$4 = p_60555_.getValue(FACING);
        switch ($$4) {
            case NORTH:
                return this.SHAPE;
            case SOUTH:
                return this.SHAPE;
            case EAST:
                return this.SHAPE2;
            case WEST:
                return this.SHAPE2;
            default:
                return SHAPE;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        boolean powered = world.hasNeighborSignal(pos);
        if (powered != state.getValue(POWERED)) {
            //playSound(world,pos);
            //world.setBlock(pos, state.setValue(POWERED, powered), 3);
        }
    }

    static {
        POWERED = BlockStateProperties.POWERED;
    }
}
