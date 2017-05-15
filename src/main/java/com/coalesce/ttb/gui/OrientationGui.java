package com.coalesce.ttb.gui;

import com.coalesce.gui.ItemBuilder;
import com.coalesce.gui.PlayerGui;
import com.coalesce.ttb.TextToBlock;
import com.coalesce.ttb.blocks.TextLoader;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import static org.bukkit.ChatColor.*;

public final class OrientationGui extends PlayerGui {
	
	private final short durability;
	private final TextGui mainMenu;
	private static final int GUI_ROWS = 2;
	private final Material currentMaterial;
	private static final float SOUND_VOLUME = 3;
	private TextLoader.TextDirection direction;
	private static final Sound INVALID_ACTION_SOUND = Sound.BLOCK_ANVIL_PLACE;
	private static final Sound ACTION_SOUND = Sound.BLOCK_WOOD_BUTTON_CLICK_ON;
	
	public OrientationGui(TextToBlock plugin, TextGui mainMenu, Material currentMaterial, short durability, TextLoader.TextDirection direction) {
		super(plugin, GUI_ROWS * 9, DARK_GRAY + "Orientation Menu");
		this.currentMaterial = currentMaterial;
		this.direction = direction;
		this.durability = durability;
		this.mainMenu = mainMenu;
		
		setStaticItems();
	}
	
	private void setStaticItems() {
		//Main Menu
		setItem(0,
				user -> new ItemBuilder(Material.SIGN)
						.lore(GRAY + "Back to the Main Menu.")
						.displayName(YELLOW + "" + BOLD + "Main Menu")
						.build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), ACTION_SOUND, SOUND_VOLUME, 1);
					this.mainMenu.open(clicker);
					this.mainMenu.update(clicker);
				});
		//Filler
		setItem(1,
				user -> new ItemBuilder(Material.STAINED_GLASS_PANE)
						.displayName(" ")
						.build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), INVALID_ACTION_SOUND, 3, 1);
				});
		//Filler
		setItem(2,
				user -> new ItemBuilder(Material.STAINED_GLASS_PANE)
						.displayName(" ")
						.build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), INVALID_ACTION_SOUND, 3, 1);
				});
		//North
		setItem(3,
				user -> {
					if (direction == TextLoader.TextDirection.NORTH) {
						return new ItemBuilder(currentMaterial)
								.durability(durability)
								.lore(GRAY + "Set the text direction to North.", GREEN + "Current Selection")
								.displayName(YELLOW + "" + BOLD + "North")
								.enchant(Enchantment.DURABILITY, 1)
								.itemFlags(ItemFlag.HIDE_ENCHANTS)
								.build();
					}
					return new ItemBuilder(currentMaterial)
							.durability(durability)
							.lore(GRAY + "Set the text direction to North.")
							.displayName(YELLOW + "" + BOLD + "North")
							.build();
				},
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), INVALID_ACTION_SOUND, 3, 1);
					this.direction = TextLoader.TextDirection.NORTH;
					this.mainMenu.setDirection(direction);
					update(clicker);
				});
		//South
		setItem(4,
				user -> {
					if (direction == TextLoader.TextDirection.SOUTH) {
						return new ItemBuilder(currentMaterial)
								.durability(durability)
								.lore(GRAY + "Set the text direction to South.", GREEN + "Current Selection")
								.displayName(YELLOW + "" + BOLD + "South")
								.enchant(Enchantment.DURABILITY, 1)
								.itemFlags(ItemFlag.HIDE_ENCHANTS)
								.build();
					}
					return new ItemBuilder(currentMaterial)
							.durability(durability)
							.lore(GRAY + "Set the text direction to South.")
							.displayName(YELLOW + "" + BOLD + "South")
							.build();
				},
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), INVALID_ACTION_SOUND, 3, 1);
					this.direction = TextLoader.TextDirection.SOUTH;
					this.mainMenu.setDirection(direction);
					update(clicker);
				});
		//East
		setItem(5,
				user -> {
					if (direction == TextLoader.TextDirection.EAST) {
						return new ItemBuilder(currentMaterial)
								.durability(durability)
								.lore(GRAY + "Set the text direction to East.", GREEN + "Current Selection")
								.displayName(YELLOW + "" + BOLD + "East")
								.enchant(Enchantment.DURABILITY, 1)
								.itemFlags(ItemFlag.HIDE_ENCHANTS)
								.build();
					}
					return new ItemBuilder(currentMaterial)
							.durability(durability)
							.lore(GRAY + "Set the text direction to East.")
							.displayName(YELLOW + "" + BOLD + "East")
							.build();
				},
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), INVALID_ACTION_SOUND, 3, 1);
					this.direction = TextLoader.TextDirection.EAST;
					this.mainMenu.setDirection(direction);
					update(clicker);
				});
		//West
		setItem(6,
				user -> {
					if (direction == TextLoader.TextDirection.WEST) {
						return new ItemBuilder(currentMaterial)
								.durability(durability)
								.lore(GRAY + "Set the text direction to West.", GREEN + "Current Selection")
								.displayName(YELLOW + "" + BOLD + "West")
								.enchant(Enchantment.DURABILITY, 1)
								.itemFlags(ItemFlag.HIDE_ENCHANTS)
								.build();
					}
					return new ItemBuilder(currentMaterial)
							.durability(durability)
							.lore(GRAY + "Set the text direction to West.")
							.displayName(YELLOW + "" + BOLD + "West")
							.build();
				},
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), INVALID_ACTION_SOUND, 3, 1);
					this.direction = TextLoader.TextDirection.WEST;
					this.mainMenu.setDirection(direction);
					update(clicker);
				});
		//Filler
		setItem(7,
				user -> new ItemBuilder(Material.STAINED_GLASS_PANE)
						.displayName(" ")
						.build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), INVALID_ACTION_SOUND, 3, 1);
				});
		//Filler
		setItem(8,
				user -> new ItemBuilder(Material.STAINED_GLASS_PANE)
						.displayName(" ")
						.build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), INVALID_ACTION_SOUND, 3, 1);
				});
	}
	
}
