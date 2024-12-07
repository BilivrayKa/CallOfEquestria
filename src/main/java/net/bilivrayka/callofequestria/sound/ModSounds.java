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
    public static final RegistryObject<SoundEvent> LIMESTONE_PIE1 = registerSoundEvents("limestone_pie1");
    public static final RegistryObject<SoundEvent> LIMESTONE_PIE2 = registerSoundEvents("limestone_pie2");
    public static final RegistryObject<SoundEvent> NURSE_REDHEART1 = registerSoundEvents("nurse_redheart1");
    public static final RegistryObject<SoundEvent> NURSE_REDHEART2 = registerSoundEvents("nurse_redheart2");
    public static final RegistryObject<SoundEvent> LILY_VALLEY1 = registerSoundEvents("lily_valley1");
    public static final RegistryObject<SoundEvent> LILY_VALLEY2 = registerSoundEvents("lily_valley2");
    public static final RegistryObject<SoundEvent> DAISY1 = registerSoundEvents("daisy1");
    public static final RegistryObject<SoundEvent> DAISY2 = registerSoundEvents("daisy2");
    public static final RegistryObject<SoundEvent> LOTUS_BLOSSOM1 = registerSoundEvents("lotus_blossom1");
    public static final RegistryObject<SoundEvent> LOTUS_BLOSSOM2 = registerSoundEvents("lotus_blossom2");
    public static final RegistryObject<SoundEvent> ALOE_VERA1 = registerSoundEvents("aloe_vera1");
    public static final RegistryObject<SoundEvent> ALOE_VERA2 = registerSoundEvents("aloe_vera2");
    public static final RegistryObject<SoundEvent> OCTAVIA_MELODY1 = registerSoundEvents("octavia_melody1");
    public static final RegistryObject<SoundEvent> OCTAVIA_MELODY2 = registerSoundEvents("octavia_melody2");
    public static final RegistryObject<SoundEvent> DJ_PON31 = registerSoundEvents("dj_pon31");
    public static final RegistryObject<SoundEvent> DJ_PON32 = registerSoundEvents("dj_pon32");
    public static final RegistryObject<SoundEvent> CHEERILEE1 = registerSoundEvents("cheerilee1");
    public static final RegistryObject<SoundEvent> CHEERILEE2 = registerSoundEvents("cheerilee2");
    public static final RegistryObject<SoundEvent> SPITFIRE1 = registerSoundEvents("spitfire1");
    public static final RegistryObject<SoundEvent> SPITFIRE2 = registerSoundEvents("spitfire2");
    public static final RegistryObject<SoundEvent> CHERRY_BERRY1 = registerSoundEvents("cherry_berry1");
    public static final RegistryObject<SoundEvent> CHERRY_BERRY2 = registerSoundEvents("cherry_berry2");
    public static final RegistryObject<SoundEvent> SUGAR_BELLE1 = registerSoundEvents("sugar_belle1");
    public static final RegistryObject<SoundEvent> SUGAR_BELLE2 = registerSoundEvents("sugar_belle2");
    public static final RegistryObject<SoundEvent> TWILIGHT_VELVET1 = registerSoundEvents("twilight_velvet1");
    public static final RegistryObject<SoundEvent> TWILIGHT_VELVET2 = registerSoundEvents("twilight_velvet2");
    public static final RegistryObject<SoundEvent> LIGHTNING_DUST1 = registerSoundEvents("lightning_dust1");
    public static final RegistryObject<SoundEvent> LIGHTNING_DUST2 = registerSoundEvents("lightning_dust2");

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
    public static final RegistryObject<SoundEvent> STEREO_LIMESTONE_PIE1 = registerSoundEvents("stereo_limestone_pie1");
    public static final RegistryObject<SoundEvent> STEREO_LIMESTONE_PIE2 = registerSoundEvents("stereo_limestone_pie2");
    public static final RegistryObject<SoundEvent> STEREO_NURSE_REDHEART1 = registerSoundEvents("stereo_nurse_redheart1");
    public static final RegistryObject<SoundEvent> STEREO_NURSE_REDHEART2 = registerSoundEvents("stereo_nurse_redheart2");
    public static final RegistryObject<SoundEvent> STEREO_LILY_VALLEY1 = registerSoundEvents("stereo_lily_valley1");
    public static final RegistryObject<SoundEvent> STEREO_LILY_VALLEY2 = registerSoundEvents("stereo_lily_valley2");
    public static final RegistryObject<SoundEvent> STEREO_DAISY1 = registerSoundEvents("stereo_daisy1");
    public static final RegistryObject<SoundEvent> STEREO_DAISY2 = registerSoundEvents("stereo_daisy2");
    public static final RegistryObject<SoundEvent> STEREO_LOTUS_BLOSSOM1 = registerSoundEvents("stereo_lotus_blossom1");
    public static final RegistryObject<SoundEvent> STEREO_LOTUS_BLOSSOM2 = registerSoundEvents("stereo_lotus_blossom2");
    public static final RegistryObject<SoundEvent> STEREO_ALOE_VERA1 = registerSoundEvents("stereo_aloe_vera1");
    public static final RegistryObject<SoundEvent> STEREO_ALOE_VERA2 = registerSoundEvents("stereo_aloe_vera2");
    public static final RegistryObject<SoundEvent> STEREO_OCTAVIA_MELODY1 = registerSoundEvents("stereo_octavia_melody1");
    public static final RegistryObject<SoundEvent> STEREO_OCTAVIA_MELODY2 = registerSoundEvents("stereo_octavia_melody2");
    public static final RegistryObject<SoundEvent> STEREO_DJ_PON31 = registerSoundEvents("stereo_dj_pon31");
    public static final RegistryObject<SoundEvent> STEREO_DJ_PON32 = registerSoundEvents("stereo_dj_pon32");
    public static final RegistryObject<SoundEvent> STEREO_CHEERILEE1 = registerSoundEvents("stereo_cheerilee1");
    public static final RegistryObject<SoundEvent> STEREO_CHEERILEE2 = registerSoundEvents("stereo_cheerilee2");
    public static final RegistryObject<SoundEvent> STEREO_SPITFIRE1 = registerSoundEvents("stereo_spitfire1");
    public static final RegistryObject<SoundEvent> STEREO_SPITFIRE2 = registerSoundEvents("stereo_spitfire2");
    public static final RegistryObject<SoundEvent> STEREO_CHERRY_BERRY1 = registerSoundEvents("stereo_cherry_berry1");
    public static final RegistryObject<SoundEvent> STEREO_CHERRY_BERRY2 = registerSoundEvents("stereo_cherry_berry2");
    public static final RegistryObject<SoundEvent> STEREO_SUGAR_BELLE1 = registerSoundEvents("stereo_sugar_belle1");
    public static final RegistryObject<SoundEvent> STEREO_SUGAR_BELLE2 = registerSoundEvents("stereo_sugar_belle2");
    public static final RegistryObject<SoundEvent> STEREO_TWILIGHT_VELVET1 = registerSoundEvents("stereo_twilight_velvet1");
    public static final RegistryObject<SoundEvent> STEREO_TWILIGHT_VELVET2 = registerSoundEvents("stereo_twilight_velvet2");
    public static final RegistryObject<SoundEvent> STEREO_LIGHTNING_DUST1 = registerSoundEvents("stereo_lightning_dust1");
    public static final RegistryObject<SoundEvent> STEREO_LIGHTNING_DUST2 = registerSoundEvents("stereo_lightning_dust2");



    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        //return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CallOfEquestria.MOD_ID, name)));
        ResourceLocation id = new ResourceLocation(CallOfEquestria.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
