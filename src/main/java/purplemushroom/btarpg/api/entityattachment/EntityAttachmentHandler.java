package purplemushroom.btarpg.api.entityattachment;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import purplemushroom.btarpg.mixininterface.IMixinEntity;

import java.util.Collection;
import java.util.HashMap;
import java.util.function.Consumer;

public final class EntityAttachmentHandler {
	private final HashMap<Class<? extends EntityAttachment>, EntityAttachment> dataMap = new HashMap<>();

	public void register(EntityAttachment attachment) {

		if (dataMap.put(attachment.getClass(), attachment) != null) {
			throw new IllegalStateException("An entity attachment of this type was already registered");
		}
	}

	private <T extends EntityAttachment> T get(Class<T> clazz) {
		T data = (T)dataMap.get(clazz);
		if (data == null) throw new IllegalStateException("Attempted to fetch entity data that has not been registered");
		return data;
	}

	private Collection<EntityAttachment> getAllAttachments() {
		return dataMap.values();
	}

	public boolean hasData() {
		return !dataMap.isEmpty();
	}

	public void writeToNBT(CompoundTag nbt) {
		for (EntityAttachment attachment : dataMap.values()) {
			if (attachment.getNBTName() != null) {
				CompoundTag tag = new CompoundTag();
				attachment.writeToNBT(tag);
				nbt.putCompound(attachment.getNBTName(), tag);
			}
		}
	}

	public void readFromNBT(CompoundTag nbt) {
		for (EntityAttachment attachment : dataMap.values()) {
			if (attachment.getNBTName() != null) {
				CompoundTag tag = nbt.getCompoundOrDefault(attachment.getNBTName(), null);
				if (tag != null) attachment.readFromNBT(tag);
			}
		}
	}

	/*public void setDirty(ISynchedEntityAttachment data) {
		if (!dataThatNeedsToBeSynched.contains(data)) {
			dataThatNeedsToBeSynched.add(data);
		}
	}

	public boolean isDirty() {
		return !dataThatNeedsToBeSynched.isEmpty();
	}

	public void sendUpdate(EntityDataUpdate update) {
		update.writeInt(dataThatNeedsToBeSynched.size());
		for (ISynchedEntityAttachment data : dataThatNeedsToBeSynched) {
			update.writeInt(data.getSyncID());
			data.writeData(update);
		}
		dataThatNeedsToBeSynched.clear();
	}

	public void receiveUpdate(EntityDataUpdate update) {
		int updates = update.readInt();
		for (int i = 0; i < updates; i++) {
			synchedEntityData.get(update.readInt()).readData(update);
		}
	}*/

	public static <T extends EntityAttachment> T get(Entity entity, Class<T> attachmentType) {
		EntityAttachmentHandler attachments = ((IMixinEntity)entity).examplemod$getAttachments();
		return attachments.get(attachmentType);
	}

	public static <T extends Entity> void fireHook(T entity, Consumer<EntityAttachment> hook) {
		EntityAttachmentHandler attachments = ((IMixinEntity)entity).examplemod$getAttachments();

		for (EntityAttachment attachment : attachments.getAllAttachments()) {
			hook.accept(attachment);
		}
	}
}
