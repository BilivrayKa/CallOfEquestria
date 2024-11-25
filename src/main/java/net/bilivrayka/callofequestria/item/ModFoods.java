package net.bilivrayka.callofequestria.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    /*
    public static final FoodProperties FLOWER_DUST = new FoodProperties.Builder().nutrition(1).
            saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.HARM, 10), 0.33f).build();
    public static final FoodProperties FLOWER_DUST_BLACK = new FoodProperties.Builder().nutrition(1).
            saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.HARM, 10), 0.33f).build();
    public static final FoodProperties FLOWER_DUST_BLUE = new FoodProperties.Builder().nutrition(1).
            saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.HARM, 10), 0.33f).build();
    public static final FoodProperties FLOWER_DUST_BROWN = new FoodProperties.Builder().nutrition(1).
            saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.HARM, 10), 0.33f).build();
    public static final FoodProperties FLOWER_DUST_CYAN = new FoodProperties.Builder().nutrition(1).
            saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.HARM, 10), 0.33f).build();
    public static final FoodProperties FLOWER_DUST_GRAY = new FoodProperties.Builder().nutrition(1).
            saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.HARM, 10), 0.33f).build();
    public static final FoodProperties FLOWER_DUST_GREEN = new FoodProperties.Builder().nutrition(1).
            saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.HARM, 10), 0.33f).build();
    public static final FoodProperties FLOWER_DUST_LIGHTBLUE = new FoodProperties.Builder().nutrition(1).
            saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.HARM, 10), 0.33f).build();
    public static final FoodProperties FLOWER_DUST_LIGHTGRAY = new FoodProperties.Builder().nutrition(1).
            saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.HARM, 10), 0.33f).build();
    public static final FoodProperties FLOWER_DUST_LIME = new FoodProperties.Builder().nutrition(1).
            saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.HARM, 10), 0.33f).build();
    public static final FoodProperties FLOWER_DUST_MAGENTA = new FoodProperties.Builder().nutrition(1).
            saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.HARM, 10), 0.33f).build();
    public static final FoodProperties FLOWER_DUST_ORANGE = new FoodProperties.Builder().nutrition(1).
            saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.HARM, 10), 0.33f).build();
    public static final FoodProperties FLOWER_DUST_PINK = new FoodProperties.Builder().nutrition(1).
            saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.HARM, 10), 0.33f).build();
    public static final FoodProperties FLOWER_DUST_PURPLE = new FoodProperties.Builder().nutrition(1).
            saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.HARM, 10), 0.33f).build();
    public static final FoodProperties FLOWER_DUST_RED = new FoodProperties.Builder().nutrition(1).
            saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.HARM, 10), 0.33f).build();
    public static final FoodProperties FLOWER_DUST_WHITE = new FoodProperties.Builder().nutrition(1).
            saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.HARM, 10), 0.33f).build();
    public static final FoodProperties FLOWER_DUST_YELLOW = new FoodProperties.Builder().nutrition(1).
            saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.HARM, 10), 0.33f).build();
     */
    public static final FoodProperties MUG_APPLE = new FoodProperties.Builder().nutrition(1).
            saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 100), 0.33f).build();
    public static final FoodProperties NOTCH_APPLE_JAM = new FoodProperties.Builder()
            .nutrition(1).
            saturationMod(0.2f)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 4800, 3),1)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 800, 1),1)
            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 12000),1)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 12000),1).build();
    public static final FoodProperties APPLE_VODKA = new FoodProperties.Builder().nutrition(-1).
            saturationMod(-0.2f).effect(() -> new MobEffectInstance(MobEffects.POISON, 30), 0.33f)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 100), 0.33f).build();

    public static final FoodProperties MUFFIN = new FoodProperties.Builder().nutrition(1).
            saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 1000), 0.33f).build();
    public static final FoodProperties HAYBURGER = new FoodProperties.Builder().nutrition(1).
            saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400), 0.88f).build();
}
