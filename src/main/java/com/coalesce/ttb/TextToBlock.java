package com.coalesce.ttb;

import com.coalesce.plugin.CoPlugin;
import com.coalesce.ttb.blocks.TTBConverter;
import com.coalesce.ttb.data.Configuration;
import com.coalesce.ttb.data.FontLoader;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Set;

public final class TextToBlock extends CoPlugin {

	private FontLoader fontLoader;
	private Configuration ttbConfiguration;

	@Override
	public void onPluginEnable() throws Exception {

		//Config stuff
		ttbConfiguration = new Configuration();

		//Register Modules
		addModules(fontLoader = new FontLoader(this));

}

	@Override
	public void onPluginDisable() throws Exception {

	}

	//TODO: Remove this. THis is only so we can play with the cool font stuff before the new command system is pushed
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (label.equalsIgnoreCase("text")){

			if (!(sender instanceof Player)){
				return false;
			}

			//Cuz im lazy
			if (args.length < 3){
				return false;
			}

			Player player = (Player) sender;
			String fontName = args[0];
			int fontSize = Integer.parseInt(args[1]);

			String message = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

			fontLoader.loadFont(fontName, font -> {

				if (font == null){
					player.sendMessage("No such font");
					return;
				}

				World world = player.getWorld();
				Set<Vector> blockLocs = TTBConverter.getTextBlocks(message, font, fontSize, player.getLocation().getBlock());

				Bukkit.getScheduler().runTask(this, () -> {

					blockLocs.forEach(vector -> {

						world.getBlockAt(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ()).setType(Material.QUARTZ_BLOCK);

					});
				});
			});

			return true;

		}

		return false;
	}

	public Configuration getTtbConfiguration() {
		return ttbConfiguration;
	}

	public FontLoader getFontLoader() {
		return fontLoader;
	}
}
