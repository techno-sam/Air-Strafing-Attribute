package dev.cammiescorner.asa;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class AirStrafingAttribute implements ModInitializer {
	public static final EntityAttribute AIR_STRAFING_SPEED = new ClampedEntityAttribute("attribute.name.generic.asa.air_strafing_speed", 0.02D, 0D, Double.MAX_VALUE).setTracked(true);
	public static final EntityAttributeModifier SPRINTING = new EntityAttributeModifier(UUID.fromString("172c709b-9dc5-46f7-a543-b42d5ae36ccf"), "Sprinting", 0.005999999865889549D, EntityAttributeModifier.Operation.ADDITION);

	public static float getAirStrafingSpeed(LivingEntity entity) {
		@Nullable final EntityAttributeInstance airStrafingSpeed = entity.getAttributeInstance(AIR_STRAFING_SPEED);
		return airStrafingSpeed != null ? (float) airStrafingSpeed.getValue() : 0.02F;
	}

	@Override
	public void onInitialize() {
		Registry.register(Registry.ATTRIBUTE, new Identifier("asa", "air_strafing_speed"), AIR_STRAFING_SPEED);
	}
}
