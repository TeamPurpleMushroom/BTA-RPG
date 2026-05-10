package purplemushroom.btarpg.mixin;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import purplemushroom.btarpg.api.entityattachment.DataAttachmentHook;
import purplemushroom.btarpg.api.entityattachment.attachments.EntityAttachmentBase;
import purplemushroom.btarpg.api.entityattachment.EntityAttachmentHandler;
import purplemushroom.btarpg.mixininterface.IMixinEntity;

import java.util.function.Consumer;

@Debug(export = true)
@Mixin(value = Entity.class, remap = false)
public class MixinEntity implements IMixinEntity {
	@Unique
	private final EntityAttachmentHandler attachments = new EntityAttachmentHandler();

	@Inject(method = "<init>", at = @At("TAIL"))
	private void constructor(World world, CallbackInfo ci) {
		DataAttachmentHook.attachDataTo((Entity)(Object)this, attachments);
	}

	@Inject(method = "saveWithoutId", at = @At("TAIL"))
	private void save(CompoundTag tag, CallbackInfo ci) {
		if (attachments.hasData()) {
			CompoundTag dataTag = new CompoundTag();
			attachments.writeToNBT(dataTag);
			tag.putCompound("btdAttachments", dataTag);
		}
	}

	@Inject(method = "load", at = @At("TAIL"))
	private void load(CompoundTag tag, CallbackInfo ci) {
		CompoundTag dataTag = tag.getCompoundOrDefault("btdAttachments", null);
		if (dataTag != null) {
			attachments.readFromNBT(dataTag);;
		}
	}

	@Override
	public EntityAttachmentHandler examplemod$getAttachments() {
		return attachments;
	}
}
