package purplemushroom.btarpg.api.entityattachment.attachments;

import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.player.Player;
import purplemushroom.btarpg.entityattachment.StaminaAttachment;

public class PlayerAttachment extends EntityAttachmentBase<Player> {
	public PlayerAttachment(Player holder) {
		super(holder);
	}

	public void handleKeyPress(int keyCode, boolean pressed) {}
}
