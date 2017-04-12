package com.coalesce.ttb.gui;

import com.coalesce.gui.IconBuilder;
import com.coalesce.gui.IconMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.awt.*;

public final class TextMenu extends IconMenu {

	private Player player;

	private String text;
	private int width = 1;
	private int fontSize = 12;
	private Block origin;
	private Font font;

	public TextMenu(Player player, String text) {
		super("Text Editor", 1);

		this.player = player;
		this.text = text;
		this.origin = player.getLocation().getBlock();

		//Setup the Icons
		fillbackground(new IconBuilder(Material.STAINED_GLASS_PANE).durability(15).name(" ").build());
		setIcon(new IconBuilder(Material.PAPER).name(ChatColor.YELLOW + "Text").lore(ChatColor.WHITE + text).build(), 0, 0);
		setIcon(new IconBuilder(Material.REDSTONE).name(ChatColor.YELLOW + "Font Size")
				.lore(ChatColor.GRAY + "Current Size: " + ChatColor.WHITE + fontSize, "", ChatColor.GRAY + "Right-Click to increase",
						ChatColor.GRAY + "Left-Click to decrease")
				.onClick((clicker, clickType) -> {

					if (clickType.isRightClick()){
						fontSize += 2;

					} else if (clickType.isLeftClick()) {
						fontSize -= 2;

					} else {
						return;
					}

					clicker.playSound(clicker.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 1, 1);
					//Reopen to update the menu
					openForPlayer(clicker);

				}).build(), 1, 0);
	}
}
