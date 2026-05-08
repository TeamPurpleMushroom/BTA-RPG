package purplemushroom.btarpg.api.entitydata;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import purplemushroom.btarpg.entitydata.StaminaData;

public class BTADataAttachmentHook {
	public static void attachDataTo(Entity entity, BTAEntityDataHandler data) {
		if (entity instanceof Player) data.register(StaminaData.class, StaminaData::new);
	}
}
