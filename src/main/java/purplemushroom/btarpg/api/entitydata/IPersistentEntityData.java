package purplemushroom.btarpg.api.entitydata;

import com.mojang.nbt.tags.CompoundTag;

public interface IPersistentEntityData {
	String getName();

	void writeToNBT(CompoundTag nbt);

	void readFromNBT(CompoundTag nbt);
}
