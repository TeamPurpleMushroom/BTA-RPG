package purplemushroom.btarpg.api.entityattachment.attachments;

import net.minecraft.core.entity.Entity;

public abstract class EntityAttachmentBase<T extends Entity> {
	protected final T holder;

	public EntityAttachmentBase(T holder) {
		this.holder = holder;
	}
}
