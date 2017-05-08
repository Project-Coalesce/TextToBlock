package com.coalesce.ttb.gui;

import com.coalesce.gui.ItemBuilder;
import com.coalesce.ttb.TextToBlock;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Stream;

import static org.bukkit.ChatColor.*;

public final class MaterialMenu extends PlayerInv {
	
	private final TextToBlock plugin;
	private Deque<Material> used;
	private Material selection;
	private int durability;
	private int lastPage;
	private int index;
	private int page;
	
	
	
	
	public MaterialMenu(TextToBlock plugin, Material currentBlock, Player player, String fontName, String text) {
		super(plugin, 54, DARK_GRAY + "Material Selection", player);
		
		this.lastPage = (int) Stream.of(Material.values()).filter(material -> material.isSolid()).count() / 44;
		this.used = new ArrayDeque<>();
		this.selection = currentBlock;
		this.plugin = plugin;
		this.durability = 0;
		this.index = 0;
		this.page = 1;
		
		setMaterials(false);
		
		//Previous Page
		setItem(45,
				user -> {
					if (page < 2) {
						return new ItemBuilder(Material.BARRIER)
								.lore(GRAY + "" + ITALIC + "Page " + page + "/" + lastPage)
								.displayName(RED + "" + BOLD + "<<--")
								.build();
					}
					return new ItemBuilder(Material.EMERALD)
							.lore(GRAY + "" + ITALIC + "Page " + page + "/" + lastPage)
							.displayName(YELLOW + "" + BOLD + "<<--")
							.build();
				},
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					if (page <= 1) {
						clicker.playSound(clicker.getLocation(), Sound.BLOCK_ANVIL_PLACE, 3, 1);
						return;
					}
					page--;
					clicker.playSound(clicker.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 3, 1);
					setMaterials(true);
					update();
				});
		
		//Brings the user back to the main menu
		setItem(46,
				user -> new ItemBuilder(Material.SIGN)
						.lore(GRAY + "" + ITALIC + "Back to main menu.")
						.displayName(YELLOW + "" + BOLD + "Main menu")
						.build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 3, 1);
					clicker.closeInventory();
					new TextMenu(plugin, fontName, text, player, selection).open(player);
				});
		
		//Filler
		setItem(47,
				user -> new ItemBuilder(Material.STAINED_GLASS_PANE)
						.durability(14)
						.displayName(" ")
						.build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), Sound.BLOCK_ANVIL_PLACE, 3, 1);
				});
		
		//Filler
		setItem(48,
				user -> new ItemBuilder(Material.STAINED_GLASS_PANE)
						.durability(14)
						.displayName(" ")
						.build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), Sound.BLOCK_ANVIL_PLACE, 3, 1);
				});
		
		//The currently selected item
		setItem(49,
				user -> new ItemBuilder(selection)
						.displayName(YELLOW + "" + BOLD + selection.toString().toLowerCase())
						.lore(GRAY + "" + ITALIC + "Durability: " + RESET + this.durability)
						.build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), Sound.BLOCK_ANVIL_PLACE, 3, 1);
				});
		
		//Filler
		setItem(50,
				user -> new ItemBuilder(Material.STAINED_GLASS_PANE)
						.durability(14)
						.displayName(" ")
						.build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), Sound.BLOCK_ANVIL_PLACE, 3, 1);
				});
		
		//Filler
		setItem(51,
				user -> new ItemBuilder(Material.STAINED_GLASS_PANE)
						.durability(14)
						.displayName(" ")
						.build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), Sound.BLOCK_ANVIL_PLACE, 3, 1);
				});
		
		//Durability Editor
		setItem(52,
				user -> new ItemBuilder(Material.BOOK)
						.enchant(Enchantment.DURABILITY, 1)
						.itemFlags(ItemFlag.HIDE_ENCHANTS)
						.displayName(YELLOW + "" + BOLD + "Edit Durability")
						.build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), Sound.BLOCK_ANVIL_PLACE, 3, 1);
				});
		
		//Next Page
		setItem(53,
				user -> {
					if (page == lastPage) {
						return new ItemBuilder(Material.BARRIER)
								.lore(GRAY + "" + ITALIC + "Page " + page + "/" + lastPage)
								.displayName(RED + "" + BOLD + "-->>")
								.build();
					}
					return new ItemBuilder(Material.EMERALD)
							.lore(GRAY + "" + ITALIC + "Page " + page + "/" + lastPage)
							.displayName(YELLOW + "" + BOLD + "-->>")
							.build();
				},
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					if (page >= lastPage) {
						clicker.playSound(clicker.getLocation(), Sound.BLOCK_ANVIL_PLACE, 3, 1);
						update();
						return;
					}
					page++;
					clicker.playSound(clicker.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 3, 1);
					setMaterials(false);
					update();
				});
		update();
		
	}
	
	private void setMaterials(boolean previous) {
		if (previous) {
			for (index = 0; index < 44; index++) {
				setItem(index,
						user -> new ItemBuilder(used.pop()).build(),
						clickEvent -> {
							Player clicker = (Player)clickEvent.getWhoClicked();
							selection = clickEvent.getCurrentItem().getType();
							this.durability = clickEvent.getCurrentItem().getDurability();
							clicker.playSound(clicker.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 3, 1);
							update();
						});
				update();
			}
		}
		for (Material material : Material.values()) {
			if (material.isSolid() && !used.contains(material)) {
				if (index > 44) {
					index = 0;
					break;
				}
				setItem(index,
						user -> new ItemBuilder(material).build(),
						clickEvent -> {
							Player clicker = (Player)clickEvent.getWhoClicked();
							selection = clickEvent.getCurrentItem().getType();
							this.durability = clickEvent.getCurrentItem().getDurability();
							clicker.playSound(clicker.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 3, 1);
							update();
						});
				update();
				if (getInventory().getItem(index) != null) {
					used.add(material);
					index++;
				}
			}
		}
	}
	
	private void setPageMaterial(boolean previous) {
		if (previous) {
			for (index = 0; index < 44; index++) {
				System.out.println(used.peek().name());
				setItem(index,
						user -> new ItemBuilder(used.pop()).build(),
						clickEvent -> {
							Player clicker = (Player)clickEvent.getWhoClicked();
							selection = getInventory().getItem(clickEvent.getSlot()).getType();
							ItemStack stack = new ItemStack(selection);
							this.durability = stack.getDurability();
							clicker.playSound(clicker.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 3, 1);
							update();
						});
				update();
			}
		}
		for (Material material : Material.values()) {
			if (material.isSolid()) {
				if (used.contains(material)) continue;
				if (index > 44) {
					index = 0;
					break;
				}
				setItem(index,
						user -> new ItemBuilder(material).build(),
						clickEvent -> {
							Player clicker = (Player)clickEvent.getWhoClicked();
							selection = getInventory().getItem(clickEvent.getSlot()).getType();
							ItemStack stack = new ItemStack(selection);
							this.durability = stack.getDurability();
							clicker.playSound(clicker.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 3, 1);
							update();
						});
				update();
				if (getInventory().getItem(index) != null) {
					used.add(material);
					index++;
				}
			}
		}
	}
}
