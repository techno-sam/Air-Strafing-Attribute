package dev.cammiescorner.asa.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.cammiescorner.asa.AirStrafingAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) { super(entityType, world); }

	@ModifyReturnValue(method = "getOffGroundSpeed", at = @At("RETURN"))
	public float getOffGroundSpeed(float speed) {
		EntityAttributeInstance instance = getAttributeInstance(AirStrafingAttribute.getAirStrafingAttribute());
		if (instance == null) return speed;

		if (speed != AirStrafingAttribute.getAirStrafingAttribute().getDefaultValue()) {
			instance.addTemporaryModifier(new EntityAttributeModifier(
				AirStrafingAttribute.TEMP_OVERRIDE_UUID,
				"Temp Override",
				speed - AirStrafingAttribute.getAirStrafingAttribute().getDefaultValue(),
				EntityAttributeModifier.Operation.ADDITION
			));
		}

		speed = AirStrafingAttribute.getAirStrafingSpeed(this);

		if (instance.getModifier(AirStrafingAttribute.TEMP_OVERRIDE_UUID) != null) {
			instance.removeModifier(AirStrafingAttribute.TEMP_OVERRIDE_UUID);
		}
		return speed;
	}
}
