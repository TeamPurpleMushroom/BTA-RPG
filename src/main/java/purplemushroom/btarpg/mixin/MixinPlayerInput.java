package purplemushroom.btarpg.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.PlayerInput;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import purplemushroom.btarpg.api.entityattachment.EntityAttachmentHandler;

@Debug(export = true)
@Mixin(value = PlayerInput.class, remap = false)
public class MixinPlayerInput {
	@Shadow
	@Final
	public Minecraft mc;

	@Definition(id = "currentScreen", field = "Lnet/minecraft/client/Minecraft;currentScreen:Lnet/minecraft/client/gui/Screen;")
	@Expression("?.currentScreen == null")
	@ModifyExpressionValue(method = "keyEvent", at = @At("MIXINEXTRAS:EXPRESSION"))
	private boolean handleKeyPress(boolean original, int keyCode, boolean pressed) {
		if (original) {
			EntityAttachmentHandler.fireHook(
				this.mc.thePlayer,
				(attachment) -> attachment.playerKeyInputHook(keyCode, pressed)
			);
		}
		return original;
	}
}
