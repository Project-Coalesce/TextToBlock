package com.coalesce.ttb.config;

import com.coalesce.config.yml.YamlEntry;
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
		addEntry(new YamlEntry(config, "font.maxFontSize", 100));
		addEntry(new YamlEntry(config, "font.fallbackFontSize", 12));
		addEntry(new YamlEntry(config, "font.fallbackFont", "blocked"));
		addEntry(new YamlEntry(config, "font.fallbackMaterial", Material.QUARTZ_BLOCK.name()));
		addEntry(new YamlEntry(config, "operations.historySize", 10));
	}
	
	public int getMaxFontSize() {
		return getInt("font.maxFontSize");
	}
	
	public void setMaxFontSize(int maxFontSize) {
		getEntry("font.maxFontSize").setValue(maxFontSize);
	}
	
	public int getMaxOperations() {
		return getInt("operations.historySize");
	}
	
	public void setMaxOperations(int maxOperations) {
		getEntry("operations.historySize").setValue(maxOperations);
	}
	
	public String getFallbackFont() {
		return getString("font.fallbackFont");
	}
	
	public void setFallbackFont(String fallbackFont) {
		getEntry("font.fallbackFont").setValue(fallbackFont);
	}
	
	public float getFallbackFontSize() {
		return getInt("font.fallbackFontSize");
	}
	
	public void setFallbackFontSize(float fallbackFontSize) {
		getEntry("font.fallbackFontSize").setValue(fallbackFontSize);
	}
	
	public Material getFallbackMaterial() {
		return Material.valueOf(getString("font.fallbackMaterial"));
	}
	
	public void setFallbackMaterial(Material material) {
		getEntry("font.fallbackMaterial").setValue(material.name());
	}
	
}
