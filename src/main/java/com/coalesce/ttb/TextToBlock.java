package com.coalesce.ttb;

import com.coalesce.command.CommandLoader;
import com.coalesce.plugin.CoPlugin;
import com.coalesce.ttb.commands.TTBCommands;

public final class TextToBlock extends CoPlugin {

	@Override
	public void onPluginEnable() throws Exception {
		CommandLoader.addCommand(TTBCommands.class);
	}

	@Override
	public void onPluginDisable() throws Exception {

	}

}
