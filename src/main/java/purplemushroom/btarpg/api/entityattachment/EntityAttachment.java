package purplemushroom.btarpg.api.entityattachment;

import com.mojang.nbt.tags.CompoundTag;

public abstract class EntityAttachment {
	/**
	 * The id this attachment will be saved under in the entity's NBT
	 * Null indicates this attachment should not be saved at all
	 */
	public String getNBTName() {
		return null;
	}

	public void writeToNBT(CompoundTag nbt) {
		// remind ourselves to override the method if getNBTName isn't null
		throw new UnsupportedOperationException("No nbt serialization defined");
	}

	public void readFromNBT(CompoundTag nbt) {
		// remind ourselves to override the method if getNBTName isn't null
		throw new UnsupportedOperationException("No nbt deserialization defined");
	}

	/*
	All entity hooks will be in this class
	Ideally you'd have different subclasses for entity hooks, player hooks, etc
	But that means you have to deal with casting and generics and stuff
	 */

	// Entity
	public void entityPreTickHook() {}

	public void entityPostTickHook() {}

	// Player
	public void playerKeyInputHook(int keyCode, boolean pressed) {}
}
