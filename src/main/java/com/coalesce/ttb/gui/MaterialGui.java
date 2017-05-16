package com.coalesce.ttb.gui;

import com.coalesce.gui.ItemBuilder;
import com.coalesce.gui.PlayerGui;
import com.coalesce.plugin.CoPlugin;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.bukkit.ChatColor.*;

public final class MaterialGui extends PlayerGui {
	
    private int currentPage;
    private short durability;
	private Material selection;
	private final TextGui previousMenu;

    private static final int GUI_ROWS = 6;
    private static final int MATERIAL_ROWS = 5;
	private static final float SOUND_VOLUME = 3;
	private static final Sound ACTION_SOUND = Sound.BLOCK_WOOD_BUTTON_CLICK_ON;
	private static final Sound INVALID_ACTION_SOUND = Sound.BLOCK_ANVIL_PLACE;
    private static ArrayList<Material> validBuildMaterials;
	
	/**
	 * Creates a MaterialGui for a player.
	 * @param plugin The providing plugin.
	 * @param selection The currently selected material.
	 * @param previousMenu The previous menu.
	 */
	public MaterialGui(CoPlugin plugin, Material selection, TextGui previousMenu) {
        super(plugin, GUI_ROWS * 9, DARK_GRAY + "Material Menu");

        this.selection = selection;
        this.currentPage = 0; //Page numbers start at 0
		this.durability = 0;
		this.previousMenu = previousMenu;

		setupControlBar();
		setMaterials();

    }

	private void setupControlBar(){

		final int totalPages = (getValidBuildMaterials().size() / (MATERIAL_ROWS * 9)) + 1;

		//Previous Page
		setItem(45,
				user -> {
					if (currentPage != 0) {
						return new ItemBuilder(Material.INK_SACK)
								.displayName(YELLOW + "<" + STRIKETHROUGH + "--")
								.lore(GRAY + "Page " + WHITE + (currentPage + 1) + GRAY + "/" + WHITE + totalPages)
								.durability(DyeColor.LIME.getDyeData())
								.build();
					}
					return new ItemBuilder(Material.INK_SACK)
							.displayName(" ")
							.durability(DyeColor.GRAY.getDyeData())
							.build();
				},
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					//If the player is on the first page, they cant go back
					if (currentPage == 0) {
						clicker.playSound(clicker.getLocation(), INVALID_ACTION_SOUND, SOUND_VOLUME, 1);
						return;
					}
					//Go back a page
					currentPage--;
					clicker.playSound(clicker.getLocation(), ACTION_SOUND, SOUND_VOLUME, 1);
					//Update the materials and update the Gui for the player
					setMaterials();
					update(clicker);
				});

		//Brings the user back to the main menu
		setItem(46,
				user -> new ItemBuilder(Material.SIGN)
						.displayName(YELLOW + "" + BOLD + "Main Menu")
						.lore(GRAY + "Back to main menu.")
						.build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), ACTION_SOUND, SOUND_VOLUME, 1);
					this.previousMenu.setMaterial(selection);
					this.previousMenu.setDurability(durability);
					this.previousMenu.open(clicker);
					this.previousMenu.update(clicker);
				});

		//Filler
		setItem(47,
				user -> new ItemBuilder(Material.STAINED_GLASS_PANE)
						.displayName(" ")
						.build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), INVALID_ACTION_SOUND, SOUND_VOLUME, 1);
				});

		//Filler
		setItem(48,
				user -> new ItemBuilder(Material.STAINED_GLASS_PANE)
						.displayName(" ")
						.build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), INVALID_ACTION_SOUND, SOUND_VOLUME, 1);
				});

		//The currently selected item
		setItem(49,
				user -> new ItemBuilder(this.selection)
						.durability(this.durability)
						.displayName(YELLOW + this.selection.toString().toLowerCase())
						.lore(GRAY + "Durability: " + RESET + this.durability)
						.build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), INVALID_ACTION_SOUND, SOUND_VOLUME, 1);
				});

		//Filler
		setItem(50,
				user -> new ItemBuilder(Material.STAINED_GLASS_PANE)
						.displayName(" ")
						.build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), INVALID_ACTION_SOUND, SOUND_VOLUME, 1);
				});

		//Filler
		setItem(51,
				user -> new ItemBuilder(Material.STAINED_GLASS_PANE)
						.displayName(" ")
						.build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), INVALID_ACTION_SOUND, SOUND_VOLUME, 1);
				});

		//Durability Editor
		setItem(52,
				user -> new ItemBuilder(Material.BOOK)
						.displayName(YELLOW + "Edit Durability")
						.enchant(Enchantment.DURABILITY, 1)
						.itemFlags(ItemFlag.HIDE_ENCHANTS)
						.build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), INVALID_ACTION_SOUND, SOUND_VOLUME, 1);
					new MetadataGui(plugin, durability, this, previousMenu, selection).open(clicker);
				});

		//Next Page
		setItem(53,
				user -> {
					if (currentPage < (totalPages - 1)) {
						return new ItemBuilder(Material.INK_SACK)
								.displayName(YELLOW + "" + STRIKETHROUGH + "--" + YELLOW + ">")
								.lore(GRAY + "Page " + WHITE + (currentPage + 1) + GRAY + "/" + WHITE + totalPages)
								.durability(DyeColor.LIME.getDyeData())
								.build();
					}
					return new ItemBuilder(Material.INK_SACK)
							.displayName(" ")
							.durability(DyeColor.GRAY.getDyeData())
							.build();
				},
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					//If the player is on the first page, they cant go back
					if (currentPage == totalPages) {
						clicker.playSound(clicker.getLocation(), INVALID_ACTION_SOUND, SOUND_VOLUME, 1);
						return;
					}
					//Go back a page
					currentPage++;
					clicker.playSound(clicker.getLocation(), ACTION_SOUND, SOUND_VOLUME, 1);
					//Update the materials and update the Gui for the player
					setMaterials();
					update(clicker);
				});

	}
	
	/**
	 * Sets the durability of the material.
	 * @param durability The material durability.
	 */
	public void setDurability(short durability) {
		this.durability = durability;
	}
	
	/**
	 * Sets the material.
	 * @param material The material to select.
	 */
	public void setMaterial(Material material) {
		this.selection = material;
	}

    private void setMaterials(){

        if (validBuildMaterials.size() < MATERIAL_ROWS * 9 * currentPage){
            //Something is wrong, this should never happen
            return;
        }

		//Clear spots
		removeItems(0, MATERIAL_ROWS * 9);

        validBuildMaterials.stream().skip(MATERIAL_ROWS * 9 * currentPage).limit(MATERIAL_ROWS * 9).forEach(material -> {

			addItem(player -> {

				if (material == selection){
					return new ItemBuilder(material)
							.lore(GREEN + "" + BOLD + "Current Selection")
							.enchant(Enchantment.DURABILITY, 1)
							.itemFlags(ItemFlag.HIDE_ENCHANTS)
							.build();
				}
				return new ItemBuilder(material)
						.lore(GRAY + "Click to select this item")
						.build();


			},
					clickEvent -> {

						Player clicker = (Player) clickEvent.getWhoClicked();
						clicker.playSound(clicker.getLocation(), ACTION_SOUND, SOUND_VOLUME, 1);

						selection = clickEvent.getCurrentItem().getType();

						update(clicker);
					});

		});

    }

    private static List<Material> getValidBuildMaterials(){

        if (validBuildMaterials == null){

            validBuildMaterials = new ArrayList<>();

            //Add all materials that are blocks to the array
            final Inventory temp = Bukkit.createInventory(null, 9);
            Stream.of(org.bukkit.Material.values()).filter(material -> {

                if (material.isSolid()){

                    temp.setItem(0, new ItemStack(material));
                    if (temp.getItem(0) == null){
                        return false;
                    }

					return true;
                }

                return false;

            }).forEach(validBuildMaterials::add);
        }

        return validBuildMaterials;
    }

}
