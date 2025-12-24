package com.medelium.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties MEDIEVAL_BREAD = new FoodProperties.Builder()
            .nutrition(6)
            .saturationModifier(0.6f)
            .build();

    public static final FoodProperties MEAD = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.3f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 0), 1.0f)
            .build();
}
