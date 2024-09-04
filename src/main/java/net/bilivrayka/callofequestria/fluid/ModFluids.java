package net.bilivrayka.callofequestria.fluid;

import net.bilivrayka.callofequestria.CallOfEquestria;
import net.bilivrayka.callofequestria.block.ModBlocks;
import net.bilivrayka.callofequestria.item.ModItems;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, CallOfEquestria.MOD_ID);

    public static final RegistryObject<FlowingFluid> SOURCE_APPLE_JUICE = FLUIDS.register("apple_juice_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.APPLE_JUICE_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_APPLE_JUICE = FLUIDS.register("flowing_apple_juice",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.APPLE_JUICE_FLUID_PROPERTIES));


    public static final ForgeFlowingFluid.Properties APPLE_JUICE_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.APPLE_JUICE_FLUID_TYPE, SOURCE_APPLE_JUICE, FLOWING_APPLE_JUICE)
            .slopeFindDistance(2).levelDecreasePerBlock(2).block(ModBlocks.APPLE_JUICE_BLOCK)
            .bucket(ModItems.APPLE_JUICE_BUCKET);

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}