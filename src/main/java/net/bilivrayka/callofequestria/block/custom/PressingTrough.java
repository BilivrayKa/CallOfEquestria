package net.bilivrayka.callofequestria.block.custom;

import net.bilivrayka.callofequestria.sound.ModSounds;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PressingTrough extends Block {

    public static final EnumProperty<PressingTroughVariant> VARIANT = EnumProperty.create("variant", PressingTroughVariant.class);

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(-11, 0, -11, 27, 1.2, 27),
            Block.box(-10, 1, -10.200000000000003, -8, 11, 26.1),
            Block.box(24, 1, -10.200000000000003, 26, 11, 26.099999999999994),
            Block.box(-10.200000000000003, 1, 24, 26.1, 11, 26),
            Block.box(-10.100000000000001, 1, -10, 26.1, 11, -8),
            Block.box(-8, 1, -10, 24, 11, -8)
        ).reduce(Shapes::or).get();

    public PressingTrough(BlockBehaviour.Properties properties) {
        super(properties);
        //this.registerDefaultState(this.getRenderPropertiesInternal());
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(VARIANT);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(VARIANT, PressingTroughVariant.DEFAULT);
    }
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        if (!pLevel.isClientSide()) {
            BlockState state = pState.setValue(VARIANT, PressingTroughVariant.DEFAULT);
            if(pState.getValue(VARIANT).getSerializedName() == "default"){
                state = pState.setValue(VARIANT, PressingTroughVariant.APPLE);
            }
            if(pState.getValue(VARIANT).getSerializedName() == "apple"){
                state = pState.setValue(VARIANT, PressingTroughVariant.APPLE_JUICE);
            }
            if(pState.getValue(VARIANT).getSerializedName() == "apple_juice"){
                state = pState.setValue(VARIANT, PressingTroughVariant.DEFAULT);
            }
            pLevel.setBlockAndUpdate(pPos, state);
        }
        return InteractionResult.CONSUME;
    }
}
