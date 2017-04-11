package com.coalesce.ttb;

import com.coalesce.plugin.CoPlugin;
import com.coalesce.ttb.blocks.SessionHolder;
import com.coalesce.ttb.config.FontsConfig;
import com.coalesce.ttb.data.FontLoader;

import java.io.File;

public final class TextToBlock extends CoPlugin {

	private FontsConfig fontsConfig;

	private FontLoader fontLoader;
	private SessionHolder sessionHolder;


	@Override
	public boolean onPreEnable() {
		fontsConfig = FontsConfig.load(new File(getDataFolder(), "Fonts"));
		return super.onPreEnable();
	}

	@Override
	public void onPluginEnable() throws Exception {
		addModules(fontLoader = new FontLoader(this), sessionHolder = new SessionHolder(this));

	}

	@Override
	public void onPluginDisable() throws Exception {

	}


	public FontsConfig getFontsConfig() {
		return fontsConfig;
	}

	public FontLoader getFontLoader() {
		return fontLoader;
	}

	public SessionHolder getSessionHolder() {
		return sessionHolder;
	}

}
