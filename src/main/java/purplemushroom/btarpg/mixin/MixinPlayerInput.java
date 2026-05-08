package purplemushroom.btarpg.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.PlayerInput;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import purplemushroom.btarpg.BTARPG;
import purplemushroom.btarpg.api.playerinput.BTAInputHandler;

@Debug(export = true)
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

	@Definition(id = "currentScreen", field = "Lnet/minecraft/client/Minecraft;currentScreen:Lnet/minecraft/client/gui/Screen;")
	@Expression("?.currentScreen == null")
	@ModifyExpressionValue(method = "keyEvent", at = @At("MIXINEXTRAS:EXPRESSION"))
	private boolean handleKeyPress(boolean original, int keyCode, boolean pressed) {
		if (original) {
			btarpgHook.handleKeyPress(keyCode, pressed);
		}
		return original;
	}
}
