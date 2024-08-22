package net.bilivrayka.callofequestria.sound;

import net.bilivrayka.callofequestria.CallOfEquestria;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.intellij.lang.annotations.Identifier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, CallOfEquestria.MOD_ID);

    public static final RegistryObject<SoundEvent> SNIFF_SOUND = registerSoundEvents("sniff_sound");
    public static final RegistryObject<SoundEvent> YAY = registerSoundEvents("yay");
    public static final RegistryObject<SoundEvent> YAY2 = registerSoundEvents("yay2");
    public static final RegistryObject<SoundEvent> SQUEE1 = registerSoundEvents("squee1");
    public static final RegistryObject<SoundEvent> SQUEE2 = registerSoundEvents("squee2");
    public static final RegistryObject<SoundEvent> SQUEE3 = registerSoundEvents("squee3");
    public static final RegistryObject<SoundEvent> ROSE1 = registerSoundEvents("rose1");
    public static final RegistryObject<SoundEvent> ROSE2 = registerSoundEvents("rose2");
    public static final RegistryObject<SoundEvent> APPLEJACK1 = registerSoundEvents("applejack1");
    public static final RegistryObject<SoundEvent> APPLEJACK2 = registerSoundEvents("applejack2");
    public static final RegistryObject<SoundEvent> RAINBOW_DASH1 = registerSoundEvents("rainbow_dash1");
    public static final RegistryObject<SoundEvent> RAINBOW_DASH2 = registerSoundEvents("rainbow_dash2");


    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        //return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CallOfEquestria.MOD_ID, name)));
        ResourceLocation id = new ResourceLocation(CallOfEquestria.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
