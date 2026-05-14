package purplemushroom.btarpg.util;

public class RegistrationHelper {
	private static final int MIN_ITEM_ID = 27_000;
	private static final int MAX_ITEM_ID = 27_500; // max id so we can keep track of it (check https://better-than-documentation.readthedocs.io/en/latest/ID_ranges/)
	private static int currentItemID = MIN_ITEM_ID;

	public static int getItemID() {
		if (currentItemID > MAX_ITEM_ID) throw new IllegalStateException("Better Than Dragons has run out of IDs!");
		return currentItemID++;
	}
}
