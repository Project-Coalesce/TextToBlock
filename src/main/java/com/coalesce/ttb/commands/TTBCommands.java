package com.coalesce.ttb.commands;

import com.coalesce.command.CoCommand;
import com.coalesce.command.CommandBuilder;
import com.coalesce.command.CommandContext;
import com.coalesce.command.tabcomplete.TabContext;
import com.coalesce.plugin.CoModule;
import com.coalesce.ttb.TextToBlock;
import com.coalesce.ttb.gui.TextGui;
import com.coalesce.ttb.session.SessionHolder;
import com.coalesce.ttb.session.TextSession;
import org.bukkit.ChatColor;

import java.util.List;

public final class TTBCommands extends CoModule {
	
	private final TextToBlock plugin;
	private final SessionHolder session;

	public TTBCommands(TextToBlock plugin) {
		super(plugin, "TextToBlock Commands");
		this.session = plugin.getSessionHolder();
		this.plugin = plugin;
	}

	@Override
	protected void onEnable() throws Exception {
		CoCommand textCommand = new CommandBuilder(plugin, "text")
				.executor(this::text)
				.usage("/text <font> <message>")
				.description("Generates text from a TTF file.")
				.permission("ttb.generate")
				.minArgs(2)
				.playerOnly()
				.build();

		CoCommand undoCommand = new CommandBuilder(plugin, "textundo")
				.executor(this::undo)
				.maxArgs(0)
				.permission("ttb.undo")
				.usage("/textundo")
				.description("Undoes a previously generated font.")
				.playerOnly()
				.build();
		
		CoCommand redoCommand = new CommandBuilder(plugin, "textredo")
				.executor(this::redo)
				.maxArgs(0)
				.permission("ttb.undo")
				.usage("/textredo")
				.description("Redoes a previously undone font generation.")
				.playerOnly()
				.build();
		
		plugin.addCommand(textCommand, undoCommand, redoCommand);
	}

	@Override
	protected void onDisable() throws Exception {

	}
	
	
	
	private void text(CommandContext context) {
		String fontName = context.argAt(0);
		String text = context.joinArgs(1);
		if (!plugin.getFontLoader().getLoadedFonts().contains(fontName)) {
			context.pluginMessage(ChatColor.RED + "Font is not loaded.");
			return;
		}
		new TextGui(plugin, fontName, text, context.asPlayer(), plugin.getFontsConfig().getFallbackMaterial()).open(context.asPlayer());
	}
	
	private void fontCompleter(TabContext context) {
		List<String> fonts = plugin.getFontLoader().getLoadedFonts();
		context.completionAt(0, fonts.toArray(new String[fonts.size()]));
	}
	
	
	
	private void undo(CommandContext context) {
		if (getSession(context) == null) {
			context.send(ChatColor.RED + "Cannot perform operation. ");
			return;
		}
		if (!getSession(context).undo()) context.pluginMessage(ChatColor.RED + "Cannot perform operation.");
		else context.pluginMessage(ChatColor.GREEN + "Operation successfully undone.");
		return;
	}
	
	
	
	private void redo(CommandContext context) {
		if (getSession(context) == null) {
			context.send(ChatColor.RED + "Cannot perform operation.");
			return;
		}
		if (!getSession(context).redo()) context.pluginMessage(ChatColor.RED + "Cannot perform operation.");
		else context.pluginMessage(ChatColor.GREEN + "Operation successfully redone.");
		return;
	}
	
	
	
	private TextSession getSession(CommandContext context) {
		return session.getSession(context.asPlayer());
	}
}
