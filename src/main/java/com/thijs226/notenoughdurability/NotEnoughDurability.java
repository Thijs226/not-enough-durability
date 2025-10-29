package com.thijs226.notenoughdurability;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotEnoughDurability implements ModInitializer {
	public static final String MOD_ID = "not-enough-durability";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Not Enough Durability mod initializing...");
	}
}
