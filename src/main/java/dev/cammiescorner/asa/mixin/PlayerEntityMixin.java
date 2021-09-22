package dev.cammiescorner.asa.mixin;

import dev.cammiescorner.asa.AirStrafingAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	@Shadow @Final private PlayerAbilities abilities;

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) { super(entityType, world); }

	@Redirect(method = "tickMovement", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;flyingSpeed:F", opcode = Opcodes.PUTFIELD, ordinal = 1))
	public void setSprintingModifier(PlayerEntity playerEntity, float value) {
		playerEntity.getAttributeInstance(AirStrafingAttribute.getAirStrafingAttribute()).addTemporaryModifier(new EntityAttributeModifier(AirStrafingAttribute.SPRINTING_UUID, "Sprinting", 0.005999999865889549D, EntityAttributeModifier.Operation.ADDITION));
	}

	@Redirect(method = "travel", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;flyingSpeed:F", opcode = Opcodes.PUTFIELD, ordinal = 0))
	public void setFlyingModifier(PlayerEntity playerEntity, float value) {
		playerEntity.getAttributeInstance(AirStrafingAttribute.getAirStrafingAttribute()).addTemporaryModifier(new EntityAttributeModifier(AirStrafingAttribute.FLYING_UUID, "Flying", abilities.getFlySpeed() * (this.isSprinting() ? 2 : 1) - AirStrafingAttribute.getAirStrafingAttribute().getDefaultValue(), EntityAttributeModifier.Operation.ADDITION));
	}

	@Redirect(method = "tickMovement", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;flyingSpeed:F", opcode = Opcodes.PUTFIELD, ordinal = 0))
	public void undoSprintingModifier(PlayerEntity playerEntity, float value) {
		playerEntity.getAttributeInstance(AirStrafingAttribute.getAirStrafingAttribute()).removeModifier(AirStrafingAttribute.SPRINTING_UUID);
	}

	@Redirect(method = "travel", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;flyingSpeed:F", opcode = Opcodes.PUTFIELD, ordinal = 1))
	public void undoFlyingModifier(PlayerEntity playerEntity, float value) {
		playerEntity.getAttributeInstance(AirStrafingAttribute.getAirStrafingAttribute()).removeModifier(AirStrafingAttribute.FLYING_UUID);
	}
}
