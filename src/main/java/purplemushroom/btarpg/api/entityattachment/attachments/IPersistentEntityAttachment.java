package purplemushroom.btarpg.api.entityattachment.attachments;

import com.mojang.nbt.tags.CompoundTag;

public interface IPersistentEntityAttachment {
	String getName();

	void writeToNBT(CompoundTag nbt);

	void readFromNBT(CompoundTag nbt);
}
