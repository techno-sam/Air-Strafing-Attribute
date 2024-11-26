package dev.cammiescorner.asa;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class AirStrafingAttribute implements ModInitializer {
	public static final UUID TEMP_OVERRIDE_UUID = UUID.fromString("acfb014b-e8b8-46a9-8bc6-496977398400");

	@Override
	public void onInitialize() {
		Registry.register(Registries.ATTRIBUTE, new Identifier("asa", "air_strafing_speed"), getAirStrafingAttribute());
	}

	public static EntityAttribute getAirStrafingAttribute() {
		return EntityAttributes.AIR_STRAFING_SPEED;
	}

	public static float getAirStrafingSpeed(LivingEntity entity) {
		@Nullable final EntityAttributeInstance airStrafingSpeed = entity.getAttributeInstance(getAirStrafingAttribute());
		return airStrafingSpeed != null ? (float) airStrafingSpeed.getValue() : 0.02F;
	}

	public static class EntityAttributes {
		public static final EntityAttribute AIR_STRAFING_SPEED = new ClampedEntityAttribute("attribute.name.generic.asa.air_strafing_speed", 0.02D, 0D, Double.MAX_VALUE).setTracked(true);
	}
}
