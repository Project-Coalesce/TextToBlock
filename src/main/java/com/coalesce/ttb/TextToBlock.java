package com.coalesce.ttb;

import com.coalesce.Core;
import com.coalesce.command.CommandModule;
import com.coalesce.plugin.CoPlugin;
import com.coalesce.ttb.blocks.SessionHolder;
import com.coalesce.ttb.commands.TTBCommands;
import com.coalesce.ttb.config.FontsConfig;
import com.coalesce.ttb.data.FontLoader;

import java.io.File;

public final class TextToBlock extends CoPlugin {

	private FontsConfig fontsConfig;

	private FontLoader fontLoader;
	private SessionHolder sessionHolder;


	@Override
	public boolean onPreEnable() {
		fontsConfig = FontsConfig.load(new File(getDataFolder(), "FontsConfig.toml"));
		return super.onPreEnable();
	}

	@Override
	public void onPluginEnable() throws Exception {

		// temporary
		CommandModule commands = getServer().getServicesManager().load(Core.class).getCommandModule();

		addModules(
				fontLoader = new FontLoader(this),
				sessionHolder = new SessionHolder(this),
				new TTBCommands(this, commands, sessionHolder));
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
