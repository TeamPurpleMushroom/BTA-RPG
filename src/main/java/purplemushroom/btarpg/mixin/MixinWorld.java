package purplemushroom.btarpg.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import purplemushroom.btarpg.api.entityattachment.EntityAttachment;
import purplemushroom.btarpg.api.entityattachment.EntityAttachmentHandler;

@Debug(export = true)
@Mixin(value = World.class, remap = false)
public class MixinWorld {
	@WrapOperation(
		method = "updateEntityWithOptionalForce",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Entity;rideTick()V")
	)
	private static void rideTick(Entity instance, Operation<Void> original) {
		EntityAttachmentHandler.fireHook(instance, EntityAttachment::entityPreTickHook);
		original.call(instance);
		EntityAttachmentHandler.fireHook(instance, EntityAttachment::entityPostTickHook);
	}

	@WrapOperation(
		method = "updateEntityWithOptionalForce",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Entity;tick()V")
	)
	private static void tick(Entity instance, Operation<Void> original) {
		EntityAttachmentHandler.fireHook(instance, EntityAttachment::entityPreTickHook);
		original.call(instance);
		EntityAttachmentHandler.fireHook(instance, EntityAttachment::entityPostTickHook);
	}
}
