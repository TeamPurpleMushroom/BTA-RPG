package purplemushroom.btarpg.mixin;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import purplemushroom.btarpg.api.entitydata.BTADataAttachmentHook;
import purplemushroom.btarpg.api.entitydata.BTAEntityData;
import purplemushroom.btarpg.api.entitydata.BTAEntityDataHandler;
import purplemushroom.btarpg.mixininterfaces.IEntity;

@Mixin(value = Entity.class, remap = false)
public class MixinEntity implements IEntity {
	@Unique
	private final BTAEntityDataHandler btaData = new BTAEntityDataHandler();

	@Inject(method = "<init>", at = @At("TAIL"))
	private void constructor(World world, CallbackInfo ci) {
		BTADataAttachmentHook.attachDataTo((Entity)(Object)this, btaData);
	}

	@Inject(method = "saveWithoutId", at = @At("TAIL"))
	private void save(CompoundTag tag, CallbackInfo ci) {
		if (btaData.hasData()) {
			CompoundTag dataTag = new CompoundTag();
			btaData.writeToNBT(dataTag);
			tag.putCompound("btaData", dataTag);
		}
	}

	@Inject(method = "load", at = @At("TAIL"))
	private void load(CompoundTag tag, CallbackInfo ci) {
		CompoundTag dataTag = tag.getCompoundOrDefault("btaData", null);
		if (dataTag != null) {
			btaData.readFromNBT(dataTag);;
		}
	}

	@Override
	public <T extends BTAEntityData> T getData(Class<T> clazz) {
		return btaData.get(clazz);
	}
}
