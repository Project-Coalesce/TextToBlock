package com.coalesce.ttb.config;

import com.coalesce.config.yml.Entry;
import com.coalesce.config.yml.YmlConfig;
import com.coalesce.plugin.CoModule;
import com.coalesce.ttb.TextToBlock;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.io.File;


public final class FontsConfig extends CoModule {
	
	private final TextToBlock plugin;
	private YmlConfig config;
	
	public FontsConfig(TextToBlock plugin) {
		super(plugin, "TextToBlock Configuration");
		this.plugin = plugin;
	}
	
	@Override
	protected void onEnable() throws Exception {
		this.config = (YmlConfig) plugin.getConfig("config");
		config.addEntry(new Entry(config, "font.maxFontSize", 100));
		config.addEntry(new Entry(config, "font.fallbackFontSize", 12));
		config.addEntry(new Entry(config, "font.fallbackFont", "blocked"));
		config.addEntry(new Entry(config, "font.fallbackMaterial", Material.QUARTZ_BLOCK.name()));
		config.addEntry(new Entry(config, "operations.historySize", 10));
	}
	
	@Override
	protected void onDisable() throws Exception {
		
	}

	public int getMaxFontSize() {
		return (int) config.getEntry("font.maxFontSize").getValue();
	}

	public void setMaxFontSize(int maxFontSize) {
		config.getEntry("font.maxFontSize").setValue(maxFontSize);
	}

	public int getMaxOperations() {
		return (int) config.getEntry("operations.historySize").getValue();
	}

	public void setMaxOperations(int maxOperations) {
		config.getEntry("operations.historySize").setValue(maxOperations);
	}
	
	public String getFallbackFont() {
		return (String) config.getEntry("font.fallbackFont").getValue();
	}
	
	public void setFallbackFont(String fallbackFont) {
		config.getEntry("font.fallbackFont").setValue(fallbackFont);
	}
	
	public float getFallbackFontSize() {
		return (float) config.getEntry("font.fallbackFontSize").getValue();
	}
	
	public void setFallbackFontSize(float fallbackFontSize) {
		config.getEntry("font.fallbackFontSize").setValue(fallbackFontSize);
	}

}
