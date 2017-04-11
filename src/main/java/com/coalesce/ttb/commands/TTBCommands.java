package com.coalesce.ttb.commands;

import com.coalesce.command.CommandModule;
import com.coalesce.command.base.CmdContext;
import com.coalesce.command.base.Command;
import com.coalesce.plugin.CoModule;
import com.coalesce.plugin.CoPlugin;
import com.coalesce.ttb.blocks.SessionHolder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class TTBCommands extends CoModule {

	private SessionHolder session;

	public TTBCommands(@NotNull CoPlugin plugin, CommandModule commands, SessionHolder sessionHolder) {
		super(plugin, "TTB Commands");
		
		this.session = sessionHolder;
		
		commands.add(
				Command.of(this::text, "Text").onlyPlayer().require("ttb.create"),
				Command.of(this::undo, "Undotext").onlyPlayer().require("ttb.undo"),
				Command.of(this::redo, "Redotext").onlyPlayer().require("ttb.redo"),
				Command.of(this::clear, "Cleartext").onlyPlayer().require("ttb.clear"));
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
		
		//Make sure you cache undo when creating text..
		// TODO: 4/10/2017 Create gui with this text
	}
    
    private void undo(CmdContext context) {
	    if (context.noArgs()) {
	    	session.getSession(((Player)context.getSender()).getUniqueId()).undo();
	    	
		}
    }
    
    private void redo(CmdContext context) {
		if (context.noArgs()) {
			session.getSession(((Player)context.getSender()).getUniqueId()).redo();
		}
    }
    
    private void clear(CmdContext context) {
		if (context.noArgs()) {
			session.getSession(((Player)context.getSender()).getUniqueId()).clear();
		}
	}

}
