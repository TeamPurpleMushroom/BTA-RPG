package purplemushroom.btarpg.mixininterfaces;

import purplemushroom.btarpg.api.entitydata.BTAEntityData;

public interface IEntity {
	<T extends BTAEntityData> T getData(Class<T> clazz);
}
