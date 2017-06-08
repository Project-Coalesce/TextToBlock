package com.coalesce.ttb.gui;

import com.coalesce.gui.ItemBuilder;
import com.coalesce.gui.PlayerGui;
import com.coalesce.plugin.CoPlugin;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import static org.bukkit.ChatColor.*;

public final class MetadataGui extends PlayerGui {
	
	private short durability;
	private final TextGui mainMenu;
	private final MaterialGui lastMenu;
	private final Material currentMaterial;
	private static final int GUI_ROWS = 2;
	private static final float SOUND_VOLUME = 3;
	private static final int MAX_DURABILITY = 15;
	private static final Sound ACTION_SOUND = Sound.BLOCK_WOOD_BUTTON_CLICK_ON;
	
	public MetadataGui(CoPlugin plugin, short durability, MaterialGui lastMenu, TextGui mainMenu, Material currentMaterial) {
		super(plugin, GUI_ROWS * 9, DARK_GRAY + "");
		this.mainMenu = mainMenu;
		this.lastMenu = lastMenu;
		this.durability = durability;
		this.currentMaterial = currentMaterial;
		
		setStaticItems();
		setRemainingSlots();
	}
	
	private void setStaticItems() {
		setItem(0,
				user -> new ItemBuilder(Material.SIGN)
						.lore(GRAY + "Back to the Main Menu.")
						.displayName(YELLOW + "" + BOLD + "Main Menu")
						.build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), ACTION_SOUND, SOUND_VOLUME, 1);
					this.mainMenu.setMaterial(currentMaterial);
					this.mainMenu.setDurability(durability);
					this.mainMenu.open(clicker);
					this.mainMenu.update(clicker);
				});
		setItem(9,
				user -> new ItemBuilder(Material.BOOK)
						.lore(GRAY + "Back to the Material Menu.")
						.displayName(YELLOW + "" + BOLD + "Material Menu")
						.build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), ACTION_SOUND, SOUND_VOLUME, 1);
					this.lastMenu.setMaterial(currentMaterial);
					this.lastMenu.setDurability(durability);
					this.lastMenu.open(clicker);
					this.lastMenu.update(clicker);
				});
		
	}
	
	private void setRemainingSlots() {
		short hack = 0;
		while (hack <= MAX_DURABILITY) {
			final short data = hack;
			addItem(user -> {
						if (data == this.durability) {
							return new ItemBuilder(currentMaterial)
									.lore(GRAY + "Durability: " + RESET + data, GREEN + "Current Selection")
									.durability(data)
									.enchant(Enchantment.DURABILITY, 1)
									.itemFlags(ItemFlag.HIDE_ENCHANTS)
									.build();
						}
						return new ItemBuilder(currentMaterial)
								.lore(GRAY + "Durability: " + RESET + data)
								.durability(data)
								.build();
					},
					click -> {
						Player clicker = (Player) click.getWhoClicked();
						clicker.playSound(clicker.getLocation(), ACTION_SOUND, SOUND_VOLUME, 1);
						this.durability = click.getCurrentItem().getDurability();
						update(clicker);
					}
			);
			hack++;
		}
	}
}
