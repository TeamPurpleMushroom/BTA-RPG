package purplemushroom.btarpg.api.playerinput;

import net.minecraft.client.Minecraft;
import purplemushroom.btarpg.entitydata.StaminaData;

public class BTAInputHandler {
	private final Minecraft mc;

	public BTAInputHandler(Minecraft minecraft) {
		this.mc = minecraft;
	}

	public void handleKeyPress(int keyCode, boolean pressed) {
		if (pressed && mc.gameSettings.keyJump.isKeyboardKey(keyCode)) {
			if (!this.mc.thePlayer.onGround) {
				this.mc.thePlayer.jump();
				StaminaData stamina = StaminaData.get(this.mc.thePlayer);
				stamina.setStamina(stamina.getStamina() + 1);
				System.out.println(stamina.getStamina());
			}
		}
	}
}
