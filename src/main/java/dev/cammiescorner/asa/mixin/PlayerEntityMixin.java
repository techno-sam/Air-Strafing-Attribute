package dev.cammiescorner.asa.mixin;

import dev.cammiescorner.asa.AirStrafingAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) { super(entityType, world); }

	@Redirect(method = "tickMovement", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;flyingSpeed:F", opcode = Opcodes.PUTFIELD, ordinal = 0))
	public void tickMovement1(PlayerEntity playerEntity, float value) {
		playerEntity.getAttributeInstance(AirStrafingAttribute.AIR_STRAFING_SPEED).removeModifier(AirStrafingAttribute.SPRINTING);
	}

	@Redirect(method = "tickMovement", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;flyingSpeed:F", opcode = Opcodes.PUTFIELD, ordinal = 1))
	public void tickMovement2(PlayerEntity playerEntity, float value) {
		playerEntity.getAttributeInstance(AirStrafingAttribute.AIR_STRAFING_SPEED).addTemporaryModifier(AirStrafingAttribute.SPRINTING);
	}
}
