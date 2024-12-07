package net.bilivrayka.callofequestria.block.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.data.ModelData;

import java.util.List;

public class PlushBlockBakedModel implements BakedModel {

    private final BakedModel originalModel;

    public PlushBlockBakedModel(BakedModel originalModel) {
        this.originalModel = originalModel;
    }

    @Override
    public List<BakedQuad> getQuads(BlockState blockState, Direction direction, RandomSource randomSource) {
        // Пример изменения позиции текстуры
        if (blockState != null && blockState.hasProperty(BlockStateProperties.POWERED)) {
            if (blockState.getValue(BlockStateProperties.POWERED)) {
                return transformQuads(originalModel.getQuads(blockState, direction, randomSource));
            }
        }
        return originalModel.getQuads(blockState, direction, randomSource);
    }

    private List<BakedQuad> transformQuads(List<BakedQuad> quads) {
        // Простой пример: изменение позиций вершин (уменьшение высоты модели)
        return quads.stream().map(quad -> {
            int[] newVertices = quad.getVertices().clone();

            // Проход по всем вершинам (4 вершины по 8 параметров на каждую)
            for (int i = 0; i < 4; i++) {
                // Индекс вершины: X=0, Y=1, Z=2
                float y = Float.intBitsToFloat(newVertices[i * 8 + 1]);
                float newY = y * 0.5f; // Уменьшаем высоту вдвое
                newVertices[i * 8 + 1] = Float.floatToIntBits(newY);
            }

            return new BakedQuad(
                    newVertices,
                    quad.getTintIndex(),
                    quad.getDirection(),
                    quad.getSprite(),
                    quad.isShade()
            );
        }).toList();
    }


    @Override
    public boolean useAmbientOcclusion() {
        return originalModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return originalModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return originalModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return originalModel.getParticleIcon();
    }

    @Override
    public ItemOverrides getOverrides() {
        return originalModel.getOverrides();
    }

}
