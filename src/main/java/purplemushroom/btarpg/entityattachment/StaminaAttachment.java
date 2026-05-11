package purplemushroom.btarpg.entityattachment;

import com.mojang.nbt.tags.CompoundTag;
import purplemushroom.btarpg.api.entityattachment.EntityAttachment;

public class StaminaAttachment extends EntityAttachment {
	private float stamina = 0.0f;

	public void setStamina(float stamina) {
		this.stamina = stamina;
	}

	public float getStamina() {
		return stamina;
	}

	@Override
	public String getNBTName() {
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
}
