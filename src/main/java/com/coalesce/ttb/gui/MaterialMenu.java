package com.coalesce.ttb.gui;

import com.coalesce.gui.ItemBuilder;
import com.coalesce.gui.PlayerGui;
import com.coalesce.ttb.TextToBlock;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public final class MaterialMenu extends PlayerGui {
	
	private final TextToBlock plugin;
	private Material selection;
	private int page;
	private int index;
	
	public MaterialMenu(TextToBlock plugin, Material currentBlock, Player player, String fontName, String text) {
		super(plugin, 54, DARK_GRAY + "Material Selection");
		this.plugin = plugin;
		this.selection = currentBlock;
		this.page = 1;
		this.index = 0;
		
		//Previous Page
		setItem(45,
			user -> {
				if (page == 1) {
					return new ItemBuilder(Material.BARRIER)
							.lore(GRAY + "" + ITALIC + "No previous pages exist.")
							.displayName(RED + "" + BOLD + "<<--")
							.build();
				}
				page--;
				return new ItemBuilder(Material.BARRIER)
						.lore(GRAY + "" + ITALIC + "Previous Page")
						.displayName(YELLOW + "" + BOLD + "<<--")
						.build();
			},
			click -> {
				Player clicker = (Player) click.getWhoClicked();
				if (page == 1) {
					clicker.playSound(clicker.getLocation(), Sound.BLOCK_ANVIL_PLACE, 3, 1);
					return;
				}
				clicker.playSound(clicker.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 3, 1);
		});
		
		//Next Page
		setItem(53,
				user -> {
					if (page == 1) {
						return new ItemBuilder(Material.BARRIER)
								.lore(GRAY + "" + ITALIC + "No more pages exist.")
								.displayName(RED + "" + BOLD + "-->>")
								.build();
					}
					page++;
					return new ItemBuilder(Material.BARRIER)
							.lore(GRAY + "" + ITALIC + "Next Page")
							.displayName(YELLOW + "" + BOLD + "-->>")
							.build();
				},
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					if (page == 1) {
						clicker.playSound(clicker.getLocation(), Sound.BLOCK_ANVIL_PLACE, 3, 1);
						return;
					}
					clicker.playSound(clicker.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 3, 1);
				});
		
		
		for (Material material : Material.values()) {
			if (material.isOccluding()) {
				if (index > 44) {
					break;
				}
				setItem(index, user -> new ItemBuilder(material).build(),
						clickEvent -> {
							if (clickEvent.getSlot() > 44) return;
							Player clicker = (Player) clickEvent.getWhoClicked();
							selection = clickEvent.getCursor().getType();
							clicker.playSound(clicker.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 3, 1);
							update(clicker);
						});
				index++;
			}
		}
	}
}
