package com.coalesce.ttb.commands;

import com.coalesce.command.CoCommand;
import com.coalesce.command.CommandBuilder;
import com.coalesce.command.CommandContext;
import com.coalesce.plugin.CoModule;
import com.coalesce.ttb.TextToBlock;
import com.coalesce.ttb.blocks.FontLoader;
import com.coalesce.ttb.config.FontsConfig;
import com.coalesce.ttb.session.SessionHolder;
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
		
		CoCommand command = new CommandBuilder(plugin, "text")
				.executor(this::text)
				.minArgs(1)
				.permission("ttb.generate")
				.usage("/text <message>")
				.description("Generates text from a TTF file.")
				.playerOnly()
				.build();
		plugin.addCommand(command);
	}

	@Override
	protected void onEnable() throws Exception {
	}

	@Override
	protected void onDisable() throws Exception {

	}
	
	public void text(CommandContext context) {
	}

	/*//The command would ideally look like this: /text <{message}> [-f, -s, -i, -b, -u]
    private void text(CmdContext context) {
		if (context.noArgs(ChatColor.RED + "Please input the text")) return;
		//String text = context.joinArgs();
		parseArgs(context);
		//Open the GUI for the player
		//new TextMenu(context.asPlayer(), text).openForPlayer(context.asPlayer());
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
	
	private void parseArgs(CmdContext context) {
		List<String> args = context.getInput();
		
		final Location location = context.asPlayer().getLocation();
		final boolean underline = args.contains("-u");
		final boolean italic = args.contains("-i");
		final boolean bold = args.contains("-b");
		final String message = context.joinArgs();
		String fontName;
		float fontSize;
		
		if (args.get(args.indexOf("-f") + 1) == null) {
			fontName = config.getFallbackFont();
		}
		else fontName = args.get(args.indexOf("-f") + 1);
		
		if (args.get(args.indexOf("-s") + 1) == null) {
			fontSize = config.getFallbackFontSize();
		}
		else fontSize = Float.parseFloat(args.get(args.indexOf("-s") + 1));
		
		message
				.replace("-f", "")
				.replace(fontName, "")
				.replace("-s", "")
				.replace(Float.toString(fontSize), "")
				.replace("-b", "")
				.replace("-i", "")
				.replace("-u", "");
		
		fontLoader.loadFont(fontName, font -> {
			if (font == null) {
				context.send(ChatColor.RED + "Font does not exist.");
				return;
			}
			Set<Vector> blockLocs = TTBConverter.getTextBlocks(message, font, location.getBlock(), fontSize, bold, italic, underline);
			
			Bukkit.getScheduler().runTask(getPlugin(), () -> {
				
				blockLocs.forEach(vector -> location.getWorld().getBlockAt(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ()).setType(Material.QUARTZ_BLOCK));
			});
		});
	}*/

}
