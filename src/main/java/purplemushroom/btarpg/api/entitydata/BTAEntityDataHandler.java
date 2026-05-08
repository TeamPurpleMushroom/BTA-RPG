package purplemushroom.btarpg.api.entitydata;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public final class BTAEntityDataHandler {
	private final HashMap<Class<? extends BTAEntityData>, BTAEntityData> dataMap = new HashMap<>();
	private final ArrayList<BTASynchedEntityData> synchedEntityData = new ArrayList<>();
	private final ArrayList<IPersistentEntityData> persistentEntityData = new ArrayList<>();

	private final ArrayList<BTASynchedEntityData> dataThatNeedsToBeSynched = new ArrayList<>();

	public <T extends BTAEntityData> void register(Class<T> clazz, Supplier<T> constructor) {
		T newData = constructor.get();
		if (newData instanceof BTASynchedEntityData) {
			BTASynchedEntityData synchedData = (BTASynchedEntityData) newData;
			synchedData.setSyncID(synchedEntityData.size());
			synchedEntityData.add(synchedData);
		}
		if (newData instanceof IPersistentEntityData) {
			persistentEntityData.add((IPersistentEntityData) newData);
		}
		dataMap.put(clazz, newData);
	}

	public <T extends BTAEntityData> T get(Class<T> clazz) {
		T data = (T)dataMap.get(clazz);
		if (data == null) throw new IllegalStateException("Attempted to fetch entity data that has not been registered");
		return data;
	}

	public boolean hasData() {
		return !dataMap.isEmpty();
	}

	public void writeToNBT(CompoundTag nbt) {
		for (IPersistentEntityData data : persistentEntityData) {
			CompoundTag tag = new CompoundTag();
			data.writeToNBT(tag);
			nbt.putCompound(data.getName(), tag);
		}
	}

	public void readFromNBT(CompoundTag nbt) {
		for (IPersistentEntityData data : persistentEntityData) {
			CompoundTag tag = nbt.getCompound(data.getName());
			if (tag != null) data.readFromNBT(tag);
		}
	}

	public void setDirty(BTASynchedEntityData data) {
		if (!dataThatNeedsToBeSynched.contains(data)) {
			dataThatNeedsToBeSynched.add(data);
		}
	}

	public boolean isDirty() {
		return !dataThatNeedsToBeSynched.isEmpty();
	}

	public void sendUpdate(BTAEntityDataUpdate update) {
		update.writeInt(dataThatNeedsToBeSynched.size());
		for (BTASynchedEntityData data : dataThatNeedsToBeSynched) {
			update.writeInt(data.getSyncID());
			data.writeData(update);
		}
		dataThatNeedsToBeSynched.clear();
	}

	public void receiveUpdate(BTAEntityDataUpdate update) {
		int updates = update.readInt();
		for (int i = 0; i < updates; i++) {
			synchedEntityData.get(update.readInt()).readData(update);
		}
	}
}
