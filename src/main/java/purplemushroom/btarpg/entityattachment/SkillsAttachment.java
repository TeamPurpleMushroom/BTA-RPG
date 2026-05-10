package purplemushroom.btarpg.entityattachment;

import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.player.Player;
import purplemushroom.btarpg.api.entityattachment.attachments.PlayerAttachment;

public class SkillsAttachment extends PlayerAttachment {
	private final StaminaAttachment stamina;

	public SkillsAttachment(Player holder, StaminaAttachment stamina) {
		super(holder);
		this.stamina = stamina;
	}

	@Override
	public void handleKeyPress(int keyCode, boolean pressed) {
		if (pressed && Minecraft.getMinecraft().gameSettings.keyJump.isKeyboardKey(keyCode)) {
			if (!holder.onGround) {
				holder.jump();
				stamina.setStamina(stamina.getStamina() + 1);
				System.out.println(stamina.getStamina());
			}
		}
	}
}
