package com.coalesce.ttb.gui;

import com.coalesce.gui.ItemBuilder;
import com.coalesce.gui.PlayerGui;
import com.coalesce.ttb.TextToBlock;
import com.coalesce.ttb.blocks.TextLoader;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.GRAY;
import static org.bukkit.ChatColor.WHITE;
import static org.bukkit.ChatColor.YELLOW;

public final class TextMenu extends PlayerGui {

	private TextLoader textLoader;

	public TextMenu(TextToBlock plugin, String fontName, String text, Player player) {
		super(plugin, 9, GRAY + "Text Menu");

		textLoader = new TextLoader(plugin, text, fontName, player.getLocation());

		//Text
		addItem(viewer ->
				new ItemBuilder(Material.BOOK)
						.displayName(YELLOW + "Text")
						.lore(text)
						.build()
				, null);

		//Font
		addItem(viewer ->
				new ItemBuilder(Material.PAPER)
						.displayName(YELLOW + "Font")
						.lore(WHITE + "Current Font: " + GRAY + textLoader.getFontName())
						.build()
				, null);

		//Font size
		addItem(viewer ->
				new ItemBuilder(Material.IRON_INGOT)
						.displayName(YELLOW + "Font Size")
						.lore(WHITE + "Current Font Size: " + GRAY + textLoader.getFontSize())
						.build(),
				(clicker, clickType) -> {
					textLoader.setFontSize(textLoader.getFontSize() + 2);
				});
	}
}
