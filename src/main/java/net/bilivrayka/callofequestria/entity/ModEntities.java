package net.bilivrayka.callofequestria.entity;

import net.bilivrayka.callofequestria.CallOfEquestria;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CallOfEquestria.MOD_ID);
    /*
    public static final RegistryObject<EntityType<MagicProjectile>> MAGIC_PROJECTILE =
            ENTITY_TYPES.register("magic_projectile", () -> EntityType.Builder.<MagicProjectile>of(MagicProjectile::new, MobCategory.MISC)
                    .sized(1f, 1f).build("magic_projectile"));

     */


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
