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
	private TextLoader.TextFace face;
	private static final int GUI_ROWS = 2;
	private final Material currentMaterial;
	private TextLoader.TextDirection direction;
	private static final float SOUND_VOLUME = 3;
	private static final Sound INVALID_ACTION_SOUND = Sound.BLOCK_ANVIL_PLACE;
	private static final Sound ACTION_SOUND = Sound.BLOCK_WOOD_BUTTON_CLICK_ON;
	
	/**
	 * Creates a new Orientation GUI for a player.
	 * @param plugin The host plugin.
	 * @param mainMenu The main TextMenu.
	 * @param currentMaterial The currently selected material.
	 * @param durability The durability of the currently selected material.
	 * @param direction The current direction the text is facing.
	 * @param face The way the text is facing currently.
	 */
	public OrientationGui(TextToBlock plugin, TextGui mainMenu, Material currentMaterial, short durability, TextLoader.TextDirection direction, TextLoader.TextFace face) {
		super(plugin, GUI_ROWS * 9, DARK_GRAY + "Orientation Menu");
		this.currentMaterial = currentMaterial;
		this.direction = direction;
		this.durability = durability;
		this.mainMenu = mainMenu;
		this.face = face;
		
		setStaticItems();
		addFiller();
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
					clicker.playSound(clicker.getLocation(), ACTION_SOUND, SOUND_VOLUME, 1);
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
					clicker.playSound(clicker.getLocation(), ACTION_SOUND, SOUND_VOLUME, 1);
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
					clicker.playSound(clicker.getLocation(), ACTION_SOUND, SOUND_VOLUME, 1);
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
					clicker.playSound(clicker.getLocation(), ACTION_SOUND, SOUND_VOLUME, 1);
					this.direction = TextLoader.TextDirection.WEST;
					this.mainMenu.setDirection(direction);
					update(clicker);
				});
		//Current Orientation
		setItem(9,
				user -> new ItemBuilder(currentMaterial)
						.durability(durability)
						.lore(GRAY + "Direction: " + RESET + direction.name(), GRAY + "Facing: " + RESET + face.name())
						.displayName(YELLOW + "" + BOLD + "Current Orientation").build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), INVALID_ACTION_SOUND, SOUND_VOLUME, 1);
				});
		//Forward
		setItem(12,
				user -> {
					if (face == TextLoader.TextFace.FORWARD) {
						return new ItemBuilder(currentMaterial)
								.durability(durability)
								.lore(GRAY + "The text will be facing forward.", GREEN + "Current Selection")
								.displayName(YELLOW + "" + BOLD + "Forwards")
								.enchant(Enchantment.DURABILITY, 1)
								.itemFlags(ItemFlag.HIDE_ENCHANTS)
								.build();
					}
					return new ItemBuilder(currentMaterial)
							.durability(durability)
							.lore(GRAY + "The text will be facing forward.")
							.displayName(YELLOW + "" + BOLD + "Forwards")
							.build();
				},
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), ACTION_SOUND, SOUND_VOLUME, 1);
					this.face = TextLoader.TextFace.FORWARD;
					this.mainMenu.setFace(face);
					update(clicker);
				});
		//Backward
		setItem(13,
				user -> {
					if (face == TextLoader.TextFace.BACKWARD) {
						return new ItemBuilder(currentMaterial)
								.durability(durability)
								.lore(GRAY + "The text will be facing backwards.", GREEN + "Current Selection")
								.displayName(YELLOW + "" + BOLD + "Backwards")
								.enchant(Enchantment.DURABILITY, 1)
								.itemFlags(ItemFlag.HIDE_ENCHANTS)
								.build();
					}
					return new ItemBuilder(currentMaterial)
							.durability(durability)
							.lore(GRAY + "The text will be facing backwards.")
							.displayName(YELLOW + "" + BOLD + "Backwards")
							.build();
				},
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), ACTION_SOUND, SOUND_VOLUME, 1);
					this.face = TextLoader.TextFace.BACKWARD;
					this.mainMenu.setFace(face);
					update(clicker);
				});
		//Upward
		setItem(14,
				user -> {
					if (face == TextLoader.TextFace.UPWARD) {
						return new ItemBuilder(currentMaterial)
								.durability(durability)
								.lore(GRAY + "The text will be facing up.", GREEN + "Current Selection")
								.displayName(YELLOW + "" + BOLD + "Upwards")
								.enchant(Enchantment.DURABILITY, 1)
								.itemFlags(ItemFlag.HIDE_ENCHANTS)
								.build();
					}
					return new ItemBuilder(currentMaterial)
							.durability(durability)
							.lore(GRAY + "The text will be facing up.")
							.displayName(YELLOW + "" + BOLD + "Upwards")
							.build();
				},
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), ACTION_SOUND, SOUND_VOLUME, 1);
					this.face = TextLoader.TextFace.UPWARD;
					this.mainMenu.setFace(face);
					update(clicker);
				});
		//Downward
		setItem(15,
				user -> {
					if (face == TextLoader.TextFace.DOWNWARD) {
						return new ItemBuilder(currentMaterial)
								.durability(durability)
								.lore(GRAY + "The text will be facing down.", GREEN + "Current Selection")
								.displayName(YELLOW + "" + BOLD + "Downwards")
								.enchant(Enchantment.DURABILITY, 1)
								.itemFlags(ItemFlag.HIDE_ENCHANTS)
								.build();
					}
					return new ItemBuilder(currentMaterial)
							.durability(durability)
							.lore(GRAY + "The text will be facing down.")
							.displayName(YELLOW + "" + BOLD + "Downwards")
							.build();
				},
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), ACTION_SOUND, SOUND_VOLUME, 1);
					this.face = TextLoader.TextFace.DOWNWARD;
					this.mainMenu.setFace(face);
					update(clicker);
				});
	}
	
	private void addFiller() {
		for (int i = 0; i < GUI_ROWS * 9; i++) {
			addItem(user -> new ItemBuilder(Material.STAINED_GLASS_PANE).displayName(" ").build(), click -> {
				Player clicker = (Player)click.getWhoClicked();
				clicker.playSound(clicker.getLocation(), INVALID_ACTION_SOUND, SOUND_VOLUME, 1);
			});
		}
	}
}
