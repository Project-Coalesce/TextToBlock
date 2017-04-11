package com.coalesce.ttb.commands;

import com.coalesce.command.CommandModule;
import com.coalesce.command.base.CmdContext;
import com.coalesce.command.base.Command;
import com.coalesce.plugin.CoModule;
import com.coalesce.plugin.CoPlugin;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public final class TTBCommands extends CoModule {


	public TTBCommands(@NotNull CoPlugin plugin, CommandModule commands) {
		super(plugin, "TTB Commands");

		commands.add(Command.of(this::text, "Text").onlyPlayer());
	}

	@Override
	protected void onEnable() throws Exception {
	}

	@Override
	protected void onDisable() throws Exception {

	}

	//The command would ideally look like this: /text <{message}> [-f, -s, -i, -b, -u]
    private void text(CmdContext context) {
		if (context.noArgs(ChatColor.RED + "Please input the text")) return;

		String text = context.joinArgs();
		// TODO: 4/10/2017 Create gui with this text
	}
    
    private void undo(CmdContext context) {
	    
    }
    
    private void redo(CmdContext context) {
	    
    }

}
