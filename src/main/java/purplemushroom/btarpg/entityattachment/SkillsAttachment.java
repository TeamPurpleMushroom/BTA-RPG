package purplemushroom.btarpg.entityattachment;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.player.Player;
import purplemushroom.btarpg.api.entityattachment.EntityAttachment;

public class SkillsAttachment extends EntityAttachment {
	private final StaminaAttachment stamina;
	private final Player player;

	private boolean doubleJumpSkill = false;
	private boolean doubleJumpReady = false;

	public SkillsAttachment(Player player, StaminaAttachment stamina) {
		this.player = player;
		this.stamina = stamina;
	}

	@Override
	public void entityPostTickHook() {
		if (player.onGround) doubleJumpReady = true;
	}

	@Override
	public void playerKeyInputHook(int keyCode, boolean pressed) {
		if (pressed && Minecraft.getMinecraft().gameSettings.keyJump.isKeyboardKey(keyCode)) {
			if (doubleJumpSkill) {
				if (!player.onGround && doubleJumpReady) {
					player.jump();
					stamina.setStamina(stamina.getStamina() + 1);
					doubleJumpReady = false;
					player.fallDistance = 0;
					System.out.println(stamina.getStamina());
				}
			}
		}
	}

	@Override
	public String getNBTName() {
		return "skills";
	}

	@Override
	public void writeToNBT(CompoundTag nbt) {
		nbt.putBoolean("skillDoubleJump", doubleJumpSkill);
		nbt.putBoolean("doubleJumpReady", doubleJumpReady);
	}

	@Override
	public void readFromNBT(CompoundTag nbt) {
		doubleJumpSkill = nbt.getBoolean("skillDoubleJump");
		doubleJumpReady = nbt.getBoolean("doubleJumpReady");
	}
}
