package net.bilivrayka.callofequestria.datagen;

import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.block.ModBlocks;
import net.bilivrayka.callofequestria.block.custom.ZinniaBushBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CallOfEquestria.MOD_ID, exFileHelper);
    }
    @Override
    protected void registerStatesAndModels() {
        makeZinniaBush((BushBlock) ModBlocks.ZINNIA_BUSH.get(), "zinnia_bush_flower_stage", "zinnia_bush_flower_stage");
    }
    private ConfiguredModel[] zinniaStates(BlockState state, BushBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().cross(modelName + state.getValue(((ZinniaBushBlock) block).getAgeProperty()),
                new ResourceLocation(CallOfEquestria.MOD_ID, "block/" + textureName + state.getValue(((ZinniaBushBlock) block).getAgeProperty()))).renderType("cutout"));

        return models;
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
    public void makeZinniaBush(BushBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> zinniaStates(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

}
