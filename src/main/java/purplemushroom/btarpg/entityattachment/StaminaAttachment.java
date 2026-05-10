package purplemushroom.btarpg.entityattachment;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.player.Player;
import purplemushroom.btarpg.api.entityattachment.attachments.IPersistentEntityAttachment;
import purplemushroom.btarpg.api.entityattachment.attachments.PlayerAttachment;

public class StaminaAttachment extends PlayerAttachment implements IPersistentEntityAttachment {
	private float stamina = 0.0f;

	public StaminaAttachment(Player holder) {
		super(holder);
	}

	public void setStamina(float stamina) {
		this.stamina = stamina;
	}

	public float getStamina() {
		return stamina;
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
}
