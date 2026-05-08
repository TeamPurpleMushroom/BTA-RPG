package purplemushroom.btarpg.api.entitydata;

public abstract class BTASynchedEntityData extends BTAEntityData {
	private int syncID = -1;

	void setSyncID(int id) { // package-private; should only be accessed from BTAEntityDataHandler
		if (syncID < 0) {
			this.syncID = id;
		} else {
			throw new IllegalStateException("Sync ID has already been set");
		}
	}

	int getSyncID() { // package-private; should only be accessed from BTAEntityDataHandler
		return syncID;
	}

	protected abstract void writeData(BTAEntityDataUpdate update);

	protected abstract void readData(BTAEntityDataUpdate update);
}
