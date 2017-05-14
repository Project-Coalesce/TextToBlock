package com.coalesce.ttb.config;

import com.coalesce.config.yml.YamlConfig;
import com.coalesce.ttb.TextToBlock;
import org.bukkit.Material;


public final class FontsConfig extends YamlConfig {
	
	private final TextToBlock plugin;
	private final YamlConfig config;
	
	public FontsConfig(TextToBlock plugin) {
		super("config", plugin);
		this.plugin = plugin;
		this.config = (FontsConfig) plugin.getConfig("config");
		addEntry("font.maxFontSize", 100);
		addEntry("font.fallbackMaterial", Material.QUARTZ_BLOCK.name());
		addEntry("operations.historySize", 10);
	}
	
	/**
	 * Gets the max allowed font size.
	 * @return The max font size.
	 */
	public int getMaxFontSize() {
		return getInt("font.maxFontSize");
	}
	
	/**
	 * Sets the max allowed font size.
	 * @param maxFontSize The new max font size.
	 */
	public void setMaxFontSize(int maxFontSize) {
		getEntry("font.maxFontSize").setValue(maxFontSize);
	}
	
	/**
	 * Gets the max amount of operations before they begin being cleared.
	 * @return The max amount of operations saved.
	 */
	public int getMaxOperations() {
		return getInt("operations.historySize");
	}
	
	/**
	 * Sets the max amount of operations before they begin being cleared.
	 * @param maxOperations The new max amount of operations.
	 */
	public void setMaxOperations(int maxOperations) {
		getEntry("operations.historySize").setValue(maxOperations);
	}
	
	/**
	 * Gets the fallback material.
	 * @return The fallback material.
	 */
	public Material getFallbackMaterial() {
		return Material.valueOf(getString("font.fallbackMaterial"));
	}
	
	/**
	 * Sets the fallback material.
	 * @param material The new fallback material.
	 */
	public void setFallbackMaterial(Material material) {
		getEntry("font.fallbackMaterial").setValue(material.name());
	}
	
}
