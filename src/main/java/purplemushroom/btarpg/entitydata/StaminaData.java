package purplemushroom.btarpg.entitydata;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.player.Player;
import purplemushroom.btarpg.api.entitydata.BTAEntityDataUpdate;
import purplemushroom.btarpg.api.entitydata.BTASynchedEntityData;
import purplemushroom.btarpg.api.entitydata.IPersistentEntityData;
import purplemushroom.btarpg.mixininterfaces.IEntity;

public class StaminaData extends BTASynchedEntityData implements IPersistentEntityData {
	private float stamina = 0.0f;

	public void setStamina(float stamina) {
		this.stamina = stamina;
	}

	public float getStamina() {
		return stamina;
	}

	@Override
	protected void writeData(BTAEntityDataUpdate update) {
		update.writeFloat(stamina);
	}

	@Override
	protected void readData(BTAEntityDataUpdate update) {
		stamina = update.readFloat();
	}

	@Override
	public String getName() {
		return "stamina";
	}

	@Override
	public void writeToNBT(CompoundTag nbt) {
		nbt.putFloat("value", stamina);
	}

	@Override
	public void readFromNBT(CompoundTag nbt) {
		stamina = nbt.getFloat("value");
	}

	public static StaminaData get(Player player) {
		return ((IEntity)player).getData(StaminaData.class);
	}
}
