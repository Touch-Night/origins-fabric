package io.github.apace100.origins;

import io.github.apace100.origins.networking.ModPacketsC2S;
import io.github.apace100.origins.origin.Origin;
import io.github.apace100.origins.power.PowerTypes;
import io.github.apace100.origins.registry.*;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Origins implements ModInitializer {

	public static final String MODID = "origins";
	public static final Logger LOGGER = LogManager.getLogger(Origins.class);


	@Override
	public void onInitialize() {
		LOGGER.info("Origins is initializing. Have fun!");
		ModComponents.register();
		ModBlocks.register();
		ModItems.register();
		ModTags.register();
		ModPacketsC2S.register();
		ModEnchantments.register();
		ModLoot.register();
		PowerTypes.init();
		Origin.init();
	}
}
