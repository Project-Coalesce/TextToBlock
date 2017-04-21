package com.coalesce.ttb.commands;

import com.coalesce.command.CoCommand;
import com.coalesce.command.CommandBuilder;
import com.coalesce.command.CommandContext;
import com.coalesce.plugin.CoModule;
import com.coalesce.ttb.TextToBlock;
import com.coalesce.ttb.blocks.FontLoader;
import com.coalesce.ttb.config.FontsConfig;
import com.coalesce.ttb.session.SessionHolder;
import com.coalesce.ttb.session.TextSession;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public final class TTBCommands extends CoModule {

	private SessionHolder session;
	private FontLoader fontLoader;
	private FontsConfig config;

	public TTBCommands(@NotNull TextToBlock plugin) {
		super(plugin, "TextToBlock Commands");
		
		this.fontLoader = plugin.getFontLoader();
		this.session = plugin.getSessionHolder();
		this.config = plugin.getFontsConfig();
		
		CoCommand textCommand = new CommandBuilder(plugin, "text")
				.executor(this::text)
				.permission("ttb.generate")
				.usage("/text <message>")
				.description("Generates text from a TTF file.")
				.playerOnly()
				.build();
		
		CoCommand undoCommand = new CommandBuilder(plugin, "textundo")
				.executor(this::undo)
				.maxArgs(0)
				.permission("ttb.undo")
				.usage("/textundo")
				.description("Undo's a previously generated font.")
				.playerOnly()
				.build();
		
		CoCommand redoCommand = new CommandBuilder(plugin, "textredo")
				.executor(this::redo)
				.maxArgs(0)
				.permission("ttb.undo")
				.usage("/textredo")
				.description("Redo's a previously undone font generation.")
				.playerOnly()
				.build();
				
		plugin.addCommand(textCommand);
		plugin.addCommand(undoCommand);
		plugin.addCommand(redoCommand);
	}

	@Override
	protected void onEnable() throws Exception {
	}

	@Override
	protected void onDisable() throws Exception {

	}
	
	public void text(CommandContext context) {
		StringBuilder builder = new StringBuilder();
		for (String s : context.getArgs()) {
			builder.append(s);
			builder.append(" ");
		}
		String text = builder.toString();
	}
	
	public void undo(CommandContext context) {
		if (getSession(context) == null) {
			context.send(ChatColor.RED + "Cannot perform operation.");
			return;
		}
		if (!getSession(context).undo()) context.send(ChatColor.RED + "Cannot perform operation.");
		return;
	}
	
	public void redo(CommandContext context) {
		if (getSession(context) == null) {
			context.send(ChatColor.RED + "Cannot perform operation.");
			return;
		}
		if (!getSession(context).redo()) context.send(ChatColor.RED + "Cannot perform operation.");
		return;
	}
	
	private TextSession getSession(CommandContext context) {
		return session.getSession(context.asPlayer());
	}
}
