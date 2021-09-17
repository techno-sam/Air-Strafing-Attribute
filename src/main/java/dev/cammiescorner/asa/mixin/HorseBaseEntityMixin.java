package dev.cammiescorner.asa.mixin;

import dev.cammiescorner.asa.AirStrafingAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HorseBaseEntity.class)
public abstract class HorseBaseEntityMixin extends AnimalEntity {
	protected HorseBaseEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) { super(entityType, world); }

	@Redirect(method = "travel", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/passive/HorseBaseEntity;flyingSpeed:F", opcode = Opcodes.PUTFIELD, ordinal = 0))
	public void setAirSpeed(HorseBaseEntity horseBaseEntity, float value) {
		if(horseBaseEntity.getAttributeInstance(AirStrafingAttribute.AIR_STRAFING_SPEED).getModifier(AirStrafingAttribute.MOUNT_UUID) == null)
			horseBaseEntity.getAttributeInstance(AirStrafingAttribute.AIR_STRAFING_SPEED).addTemporaryModifier(new EntityAttributeModifier(AirStrafingAttribute.MOUNT_UUID, "Mount speed", getMovementSpeed() * 0.1 - AirStrafingAttribute.AIR_STRAFING_SPEED.getDefaultValue(), EntityAttributeModifier.Operation.ADDITION));
	}

	@Redirect(method = "travel", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/passive/HorseBaseEntity;flyingSpeed:F", opcode = Opcodes.PUTFIELD, ordinal = 1))
	public void undoAirSpeed(HorseBaseEntity horseBaseEntity, float value) {
		horseBaseEntity.getAttributeInstance(AirStrafingAttribute.AIR_STRAFING_SPEED).removeModifier(AirStrafingAttribute.MOUNT_UUID);
	}
}
