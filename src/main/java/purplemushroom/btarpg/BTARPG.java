package purplemushroom.btarpg;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemDiscMusic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class BTARPG implements ModInitializer, RecipeEntrypoint, GameStartEntrypoint {
	public static final String MOD_ID = "btarpg";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	@Override
	public void onInitialize() {
		LOGGER.info("BTA! RPG! initialized.");
	}

	@Override
	public void onRecipesReady() {}

	@Override
	public void initNamespaces() {}

	public static Item HauntMuskie2Record;
	private static int currentItemID = 15000;

	ItemBuilder GenericItemBuilder = new ItemBuilder(BTARPG.MOD_ID);

	private static int newItemID() {
		return currentItemID++;
	}

	@Override
	public void beforeGameStart() {
		//HauntMuskie2Record = GenericItemBuilder.build(new Item("ExampleItem", MOD_ID + ":ExampleItem", newItemID()));
	}

	@Override
	public void afterGameStart() {}
}
