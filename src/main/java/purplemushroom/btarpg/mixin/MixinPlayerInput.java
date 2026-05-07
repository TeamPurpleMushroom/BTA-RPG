package purplemushroom.btarpg.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.input.PlayerInput;
import net.minecraft.client.option.GameSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import purplemushroom.btarpg.api.BTAInputHandler;

@Mixin(value = PlayerInput.class, remap = false)
public class MixinPlayerInput {
	@Shadow
	@Final
	public Minecraft mc;

	@Unique
	private BTAInputHandler btarpgHook; // TODO: figure out how to make this final

	@Inject(method = "<init>", at = @At("TAIL"))
	private void constructor(Minecraft minecraft, CallbackInfo ci) {
		btarpgHook = new BTAInputHandler(minecraft);
	}

	@Inject(method = "keyEvent", at = @At("TAIL")) // TODO: inject inside the currentScreen check instead of checking twice
	private void handleKeyPress(int keyCode, boolean pressed, CallbackInfo ci) {
		if (this.mc.currentScreen == null) {
			btarpgHook.handleKeyPress(keyCode, pressed);
		}
	}
}
