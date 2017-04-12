package com.coalesce.ttb.gui;

import com.coalesce.gui.IconBuilder;
import com.coalesce.gui.IconMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.awt.*;

public final class TextMenu extends IconMenu {

	private Player player;

	private String text;
	private int width = 1;
	private Block origin;
	private Font font;

	public TextMenu(Player player, String text) {
		super("Text Editor", 1);

		this.player = player;
		this.text = text;
		this.origin = player.getLocation().getBlock();

		//Setup the Icons
		fillbackground(new IconBuilder(Material.STAINED_GLASS_PANE).withDurability(15).withName(" ").build());
		setIcon(new IconBuilder(Material.PAPER).withName(ChatColor.YELLOW + "Text").withLore(ChatColor.GRAY + text).build(), 0, 0);
	}
}
