package net.bilivrayka.callofequestria.block.custom;

import net.minecraft.util.StringRepresentable;

public enum  PressingTroughVariant implements StringRepresentable {
    DEFAULT("default"),
    APPLE("apple"),
    APPLE_JUICE("apple_juice");

    private final String name;

    PressingTroughVariant(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}