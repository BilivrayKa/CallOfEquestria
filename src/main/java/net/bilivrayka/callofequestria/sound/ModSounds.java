package net.bilivrayka.callofequestria.sound;

import net.bilivrayka.callofequestria.CallOfEquestria;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, CallOfEquestria.MOD_ID);

    public static final RegistryObject<SoundEvent> SNIFF_SOUND = registerSoundEvents("sniff_sound");
    public static final RegistryObject<SoundEvent> YAY = registerSoundEvents("yay");
    public static final RegistryObject<SoundEvent> YAY2 = registerSoundEvents("yay2");
    public static final RegistryObject<SoundEvent> PINKIE1 = registerSoundEvents("pinkie1");
    public static final RegistryObject<SoundEvent> PINKIE2 = registerSoundEvents("pinkie2");
    public static final RegistryObject<SoundEvent> SQUEE1 = registerSoundEvents("squee1");
    public static final RegistryObject<SoundEvent> SQUEE2 = registerSoundEvents("squee2");
    public static final RegistryObject<SoundEvent> SQUEE3 = registerSoundEvents("squee3");
    public static final RegistryObject<SoundEvent> ROSE1 = registerSoundEvents("rose1");
    public static final RegistryObject<SoundEvent> ROSE2 = registerSoundEvents("rose2");
    public static final RegistryObject<SoundEvent> APPLEJACK1 = registerSoundEvents("applejack1");
    public static final RegistryObject<SoundEvent> APPLEJACK2 = registerSoundEvents("applejack2");
    public static final RegistryObject<SoundEvent> RAINBOW_DASH1 = registerSoundEvents("rainbow_dash1");
    public static final RegistryObject<SoundEvent> RAINBOW_DASH2 = registerSoundEvents("rainbow_dash2");
    public static final RegistryObject<SoundEvent> RARITY1 = registerSoundEvents("rarity1");
    public static final RegistryObject<SoundEvent> RARITY2 = registerSoundEvents("rarity2");
    public static final RegistryObject<SoundEvent> SPARKLE1 = registerSoundEvents("sparkle1");
    public static final RegistryObject<SoundEvent> SPARKLE2 = registerSoundEvents("sparkle2");
    public static final RegistryObject<SoundEvent> DERPY1 = registerSoundEvents("derpy1");
    public static final RegistryObject<SoundEvent> DERPY2 = registerSoundEvents("derpy2");
    public static final RegistryObject<SoundEvent> STARLIGHT1 = registerSoundEvents("starlight1");
    public static final RegistryObject<SoundEvent> STARLIGHT2 = registerSoundEvents("starlight2");
    public static final RegistryObject<SoundEvent> TRIXIE1 = registerSoundEvents("trixie1");
    public static final RegistryObject<SoundEvent> TRIXIE2 = registerSoundEvents("trixie2");
    public static final RegistryObject<SoundEvent> SWIRL1 = registerSoundEvents("swirl1");
    public static final RegistryObject<SoundEvent> SWIRL2 = registerSoundEvents("swirl2");
    public static final RegistryObject<SoundEvent> MINUETTE1 = registerSoundEvents("minuette1");
    public static final RegistryObject<SoundEvent> MINUETTE2 = registerSoundEvents("minuette2");
    public static final RegistryObject<SoundEvent> MAUD_PIE1 = registerSoundEvents("maud_pie1");
    public static final RegistryObject<SoundEvent> MAUD_PIE2 = registerSoundEvents("maud_pie2");
    public static final RegistryObject<SoundEvent> MARBLE_PIE1 = registerSoundEvents("marble_pie1");
    public static final RegistryObject<SoundEvent> MARBLE_PIE2 = registerSoundEvents("marble_pie2");
    public static final RegistryObject<SoundEvent> DARING_DO1 = registerSoundEvents("daring_do1");
    public static final RegistryObject<SoundEvent> DARING_DO2 = registerSoundEvents("daring_do2");
    public static final RegistryObject<SoundEvent> GRANNY_SMITH1 = registerSoundEvents("granny_smith1");
    public static final RegistryObject<SoundEvent> GRANNY_SMITH2 = registerSoundEvents("granny_smith2");

    public static final RegistryObject<SoundEvent> STEREO_SNIFF_SOUND = registerSoundEvents("stereo_sniff_sound");
    public static final RegistryObject<SoundEvent> STEREO_YAY = registerSoundEvents("stereo_yay");
    public static final RegistryObject<SoundEvent> STEREO_YAY2 = registerSoundEvents("stereo_yay2");
    public static final RegistryObject<SoundEvent> STEREO_PINKIE1 = registerSoundEvents("stereo_pinkie1");
    public static final RegistryObject<SoundEvent> STEREO_PINKIE2 = registerSoundEvents("stereo_pinkie2");
    public static final RegistryObject<SoundEvent> STEREO_SQUEE1 = registerSoundEvents("stereo_squee1");
    public static final RegistryObject<SoundEvent> STEREO_SQUEE2 = registerSoundEvents("stereo_squee2");
    public static final RegistryObject<SoundEvent> STEREO_SQUEE3 = registerSoundEvents("stereo_squee3");
    public static final RegistryObject<SoundEvent> STEREO_ROSE1 = registerSoundEvents("stereo_rose1");
    public static final RegistryObject<SoundEvent> STEREO_ROSE2 = registerSoundEvents("stereo_rose2");
    public static final RegistryObject<SoundEvent> STEREO_APPLEJACK1 = registerSoundEvents("stereo_applejack1");
    public static final RegistryObject<SoundEvent> STEREO_APPLEJACK2 = registerSoundEvents("stereo_applejack2");
    public static final RegistryObject<SoundEvent> STEREO_RAINBOW_DASH1 = registerSoundEvents("stereo_rainbow_dash1");
    public static final RegistryObject<SoundEvent> STEREO_RAINBOW_DASH2 = registerSoundEvents("stereo_rainbow_dash2");
    public static final RegistryObject<SoundEvent> STEREO_RARITY1 = registerSoundEvents("stereo_rarity1");
    public static final RegistryObject<SoundEvent> STEREO_RARITY2 = registerSoundEvents("stereo_rarity2");
    public static final RegistryObject<SoundEvent> STEREO_SPARKLE1 = registerSoundEvents("stereo_sparkle1");
    public static final RegistryObject<SoundEvent> STEREO_SPARKLE2 = registerSoundEvents("stereo_sparkle2");
    public static final RegistryObject<SoundEvent> STEREO_DERPY1 = registerSoundEvents("stereo_derpy1");
    public static final RegistryObject<SoundEvent> STEREO_DERPY2 = registerSoundEvents("stereo_derpy2");
    public static final RegistryObject<SoundEvent> STEREO_STARLIGHT1 = registerSoundEvents("stereo_starlight1");
    public static final RegistryObject<SoundEvent> STEREO_STARLIGHT2 = registerSoundEvents("stereo_starlight2");
    public static final RegistryObject<SoundEvent> STEREO_TRIXIE1 = registerSoundEvents("stereo_trixie1");
    public static final RegistryObject<SoundEvent> STEREO_TRIXIE2 = registerSoundEvents("stereo_trixie2");
    public static final RegistryObject<SoundEvent> STEREO_SWIRL1 = registerSoundEvents("stereo_swirl1");
    public static final RegistryObject<SoundEvent> STEREO_SWIRL2 = registerSoundEvents("stereo_swirl2");
    public static final RegistryObject<SoundEvent> STEREO_MINUETTE1 = registerSoundEvents("stereo_minuette1");
    public static final RegistryObject<SoundEvent> STEREO_MINUETTE2 = registerSoundEvents("stereo_minuette2");
    public static final RegistryObject<SoundEvent> STEREO_MAUD_PIE1 = registerSoundEvents("stereo_maud_pie1");
    public static final RegistryObject<SoundEvent> STEREO_MAUD_PIE2 = registerSoundEvents("stereo_maud_pie2");
    public static final RegistryObject<SoundEvent> STEREO_MARBLE_PIE1 = registerSoundEvents("stereo_marble_pie1");
    public static final RegistryObject<SoundEvent> STEREO_MARBLE_PIE2 = registerSoundEvents("stereo_marble_pie2");
    public static final RegistryObject<SoundEvent> STEREO_DARING_DO1 = registerSoundEvents("stereo_daring_do1");
    public static final RegistryObject<SoundEvent> STEREO_DARING_DO2 = registerSoundEvents("stereo_daring_do2");
    public static final RegistryObject<SoundEvent> STEREO_GRANNY_SMITH1 = registerSoundEvents("stereo_granny_smith1");
    public static final RegistryObject<SoundEvent> STEREO_GRANNY_SMITH2 = registerSoundEvents("stereo_granny_smith2");



    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        //return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CallOfEquestria.MOD_ID, name)));
        ResourceLocation id = new ResourceLocation(CallOfEquestria.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
