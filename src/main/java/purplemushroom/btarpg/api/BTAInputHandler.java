package purplemushroom.btarpg.api;

import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;

public class BTAInputHandler {
	private final Minecraft mc;

	public BTAInputHandler(Minecraft minecraft) {
		this.mc = minecraft;
	}

	public void handleKeyPress(int keyCode, boolean pressed) {
		if (pressed && mc.gameSettings.keyJump.isKeyboardKey(keyCode)) {
			if (!this.mc.thePlayer.onGround) {
				this.mc.thePlayer.jump();
			}
		}
	}
}
