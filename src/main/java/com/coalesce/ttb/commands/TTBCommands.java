package com.coalesce.ttb.commands;

import com.coalesce.command.CommandModule;
import com.coalesce.command.base.CmdContext;
import com.coalesce.command.base.Command;
import com.coalesce.plugin.CoModule;
import com.coalesce.plugin.CoPlugin;
import com.coalesce.ttb.base.TextSession;
import com.coalesce.ttb.blocks.SessionHolder;
import com.coalesce.ttb.gui.TextMenu;
import org.bukkit.ChatColor;
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

		//Open the GUI for the player
		new TextMenu(context.asPlayer(), text).openForPlayer(context.asPlayer());
	}
    
    private void undo(CmdContext context) {
	    if (context.noArgs()) {
	    	if (!getSession(context).undo()) context.send(ChatColor.RED + "Cannot perform operation.");
	    	return;
		}
		context.send(ChatColor.RED + "Too many args.");
    }
    
    private void redo(CmdContext context) {
		if (context.noArgs()) {
			if (!getSession(context).redo()) context.send(ChatColor.RED + "Cannot perform operation.");
			return;
		}
		context.send(ChatColor.RED + "Too many args.");
    }
    
    private void clear(CmdContext context) {
		if (context.noArgs()) {
			if (getSession(context).clear()) context.send(ChatColor.RED + "History is already clear.");
			return;
		}
		context.send(ChatColor.RED + "Too many args.");
	}

	private TextSession getSession(CmdContext context) {
		return session.getSession(context.asPlayer());
	}

}
