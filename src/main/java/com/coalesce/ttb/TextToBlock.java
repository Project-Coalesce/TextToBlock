package com.coalesce.ttb;

import com.coalesce.command.CommandLoader;
import com.coalesce.plugin.CoPlugin;
import com.coalesce.ttb.commands.TTBCommands;
import com.coalesce.ttb.data.FontLoader;

public final class TextToBlock extends CoPlugin {

	private FontLoader fontLoader;

	@Override
	public void onPluginEnable() throws Exception {

		//Register Modules
		addModules(fontLoader = new FontLoader(this));

		//Add commands
		TTBCommands ttbCommands = new TTBCommands(this);
		CommandLoader.addCommand(ttbCommands.getClass());
	}

	@Override
	public void onPluginDisable() throws Exception {

	}

	public FontLoader getFontLoader() {
		return fontLoader;
	}
}
