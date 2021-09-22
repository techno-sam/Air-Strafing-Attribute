package dev.cammiescorner.asa;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class AirStrafingAttribute implements ModInitializer {
	public static final UUID SPRINTING_UUID = UUID.fromString("172c709b-9dc5-46f7-a543-b42d5ae36ccf");
	public static final UUID FLYING_UUID = UUID.fromString("253f7aaa-140c-4ec3-98b1-1efaa79ef629");
	public static final UUID MOUNT_UUID = UUID.fromString("acfb014b-e8b8-46a9-8bc6-496977398400");

	@Override
	public void onInitialize() {
		Registry.register(Registry.ATTRIBUTE, new Identifier("asa", "air_strafing_speed"), getAirStrafingAttribute());
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
