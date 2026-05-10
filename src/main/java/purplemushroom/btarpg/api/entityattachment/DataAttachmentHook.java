package purplemushroom.btarpg.api.entityattachment;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import purplemushroom.btarpg.entityattachment.SkillsAttachment;
import purplemushroom.btarpg.entityattachment.StaminaAttachment;

public class DataAttachmentHook {
	public static void attachDataTo(Entity entity, EntityAttachmentHandler data) {
		if (entity instanceof Player) {
			Player player = (Player) entity;
			StaminaAttachment stamina = new StaminaAttachment(player);
			SkillsAttachment skills = new SkillsAttachment(player, stamina);
			data.register(stamina);
			data.register(skills);
		}
	}
}
