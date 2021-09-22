package dev.cammiescorner.asa.mixin;

import dev.cammiescorner.asa.AirStrafingAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> entityType, World world) { super(entityType, world); }

	@Inject(method = "createLivingAttributes", at = @At("RETURN"))
	private static void createLivingAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> info) {
		info.getReturnValue().add(AirStrafingAttribute.getAirStrafingAttribute());
	}

	@Redirect(method = "getMovementSpeed(F)F", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/LivingEntity;flyingSpeed:F", opcode = Opcodes.GETFIELD))
	public float getMovementSpeed(LivingEntity livingEntity) {
		return AirStrafingAttribute.getAirStrafingSpeed(livingEntity);
	}
}
