package com.coalesce.ttb;

import com.coalesce.plugin.CoPlugin;
import com.coalesce.ttb.data.Configuration;
import com.coalesce.ttb.data.FontLoader;

public final class TextToBlock extends CoPlugin {

	private FontLoader fontLoader;
	private Configuration ttbConfiguration;

	@Override
	public void onPluginEnable() throws Exception {

		//Config stuff
		ttbConfiguration = new Configuration();

		//Register Modules
		addModules(fontLoader = new FontLoader(this));
	}

	@Override
	public void onPluginDisable() throws Exception {

	}

	public Configuration getTtbConfiguration() {
		return ttbConfiguration;
	}

	public FontLoader getFontLoader() {
		return fontLoader;
	}
}
