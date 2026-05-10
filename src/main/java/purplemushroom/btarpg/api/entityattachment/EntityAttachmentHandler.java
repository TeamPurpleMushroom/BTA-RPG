package purplemushroom.btarpg.api.entityattachment;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import purplemushroom.btarpg.api.entityattachment.attachments.EntityAttachmentBase;
import purplemushroom.btarpg.api.entityattachment.attachments.IPersistentEntityAttachment;
import purplemushroom.btarpg.mixininterface.IMixinEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Consumer;

public final class EntityAttachmentHandler {
	private final HashMap<Class<? extends EntityAttachmentBase>, EntityAttachmentBase<?>> dataMap = new HashMap<>();
	//private final ArrayList<ISynchedEntityAttachment> synchedEntityData = new ArrayList<>();
	private final ArrayList<IPersistentEntityAttachment> persistentEntityData = new ArrayList<>();

	//private final ArrayList<ISynchedEntityAttachment> dataThatNeedsToBeSynched = new ArrayList<>();

	public void register(EntityAttachmentBase<?> attachment) {

		/*if (newData instanceof ISynchedEntityAttachment) {
			ISynchedEntityAttachment synchedData = (ISynchedEntityAttachment) newData;
			synchedData.setSyncID(synchedEntityData.size());
			synchedEntityData.add(synchedData);
		}*/
		if (attachment instanceof IPersistentEntityAttachment) {
			persistentEntityData.add((IPersistentEntityAttachment) attachment);
		}

		if (dataMap.put(attachment.getClass(), attachment) != null) {
			throw new IllegalStateException("An entity attachment of this type was already registered");
		}
	}

	private <T extends EntityAttachmentBase<?>> T get(Class<T> clazz) {
		T data = (T)dataMap.get(clazz);
		if (data == null) throw new IllegalStateException("Attempted to fetch entity data that has not been registered");
		return data;
	}

	private Collection<EntityAttachmentBase<?>> getAllAttachments() {
		return dataMap.values();
	}

	public boolean hasData() {
		return !dataMap.isEmpty();
	}

	public void writeToNBT(CompoundTag nbt) {
		for (IPersistentEntityAttachment data : persistentEntityData) {
			CompoundTag tag = new CompoundTag();
			data.writeToNBT(tag);
			nbt.putCompound(data.getName(), tag);
		}
	}

	public void readFromNBT(CompoundTag nbt) {
		for (IPersistentEntityAttachment data : persistentEntityData) {
			CompoundTag tag = nbt.getCompoundOrDefault(data.getName(), null);
			if (tag != null) data.readFromNBT(tag);
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

	public static <K extends Entity, T extends EntityAttachmentBase<K>> T get(K entity, Class<T> attachmentType) {
		EntityAttachmentHandler attachments = ((IMixinEntity)entity).examplemod$getAttachments();
		return attachments.get(attachmentType);
	}

	public static <K extends Entity, T extends EntityAttachmentBase<?>> void fireHook(K entity, Class<T> attachmentType, Consumer<T> hook) {
		EntityAttachmentHandler attachments = ((IMixinEntity)entity).examplemod$getAttachments();

		for (EntityAttachmentBase<?> attachment : attachments.getAllAttachments()) {
			if (attachmentType.isInstance(attachment)) {
				hook.accept((T)attachment);
			}
		}
	}
}
