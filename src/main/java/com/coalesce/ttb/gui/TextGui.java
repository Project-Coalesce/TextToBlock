package com.coalesce.ttb.gui;

import com.coalesce.gui.ItemBuilder;
import com.coalesce.gui.PlayerGui;
import com.coalesce.ttb.TextToBlock;
import com.coalesce.ttb.blocks.TextLoader;
import com.google.common.util.concurrent.ListenableFuture;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static org.bukkit.ChatColor.*;

public final class TextGui extends PlayerGui {

	private TextLoader textLoader;
	private Material material;

	public TextGui(TextToBlock plugin, String fontName, String text, Player player, Material material) {
		super(plugin, 9, DARK_GRAY + "Text Menu");

		this.material = material; //Default material
		textLoader = new TextLoader(plugin, text, fontName, material, player.getLocation());

		setupIcons(plugin);
	}

	private void setupIcons(TextToBlock plugin){

		//Text
		addItem(viewer ->
						new ItemBuilder(Material.BOOK)
								.displayName(YELLOW + "" + BOLD + "Text")
								.lore("Current Text: " + GRAY + ITALIC + textLoader.getText())
								.build()
				, null);

		//Font
		addItem(viewer ->
						new ItemBuilder(Material.PAPER)
								.displayName(YELLOW + "" + BOLD + "Font")
								.lore(WHITE + "Current Font: " + GRAY + ITALIC + textLoader.getFontName())
								.build()
				, null);

		//Font size
		addItem(viewer ->
						new ItemBuilder(Material.PAPER)
								.displayName(YELLOW + "" + BOLD + "Font Size")
								.lore(WHITE + "Current Font Size: " + GRAY + ITALIC + textLoader.getFontSize())
								.build(),

				clickEvent -> {

					Player clicker = (Player) clickEvent.getWhoClicked();
					float fontSize = textLoader.getFontSize();

					switch (clickEvent.getClick()){
						case RIGHT:
							fontSize--;
							break;
						case SHIFT_RIGHT:
							fontSize -= 10;
							break;
						case LEFT:
							fontSize++;
							break;
						case SHIFT_LEFT:
							fontSize += 10;
							break;
					}

					if (fontSize < 6){
						fontSize = 6;
					} else if (fontSize > plugin.getFontsConfig().getMaxFontSize()){
						fontSize = plugin.getFontsConfig().getMaxFontSize();
					}

					textLoader.setFontSize(fontSize);
					clicker.playSound(clicker.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 3, 1);

					update(clicker);
				});

		//Italics
		addItem(viewer ->
						new ItemBuilder(Material.PAPER)
								.displayName(YELLOW + "" + BOLD + "Italics")
								.lore(textLoader.isItalics() ? GREEN + "True" : RED + "False")
								.build(),
				clickEvent -> {

					Player clicker = (Player) clickEvent.getWhoClicked();

					clicker.playSound(clicker.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 3, 1);
					textLoader.setItalics(!textLoader.isItalics());

					update(clicker);
				});

		//Bold
		addItem(viewer ->
						new ItemBuilder(Material.PAPER)
								.displayName(YELLOW + "" + BOLD + "Bold")
								.lore(textLoader.isBold() ? GREEN + "True" : RED + "False")
								.build(),
				clickEvent -> {

					Player clicker = (Player) clickEvent.getWhoClicked();

					clicker.playSound(clicker.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 3, 1);
					textLoader.setBold(!textLoader.isBold());

					update(clicker);
				});

		//Material viewer
		addItem(viewer ->
						new ItemBuilder(material)
								.displayName(YELLOW + "" + BOLD + "Material")
								.lore("Current: " + GRAY + ITALIC + material.toString().substring(0, 1).toUpperCase() + material.toString().substring(1).toLowerCase().replace("_", " "))
								.build(),
				clickEvent -> {
					Player clicker = (Player) clickEvent.getWhoClicked();
					clicker.playSound(clicker.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 3, 1);
					clicker.closeInventory();
					//TODO: This will change
					new MaterialGui(plugin, material, this).open(clicker);
					update(clicker);
				});

		setItem(8,
				viewer ->
						new ItemBuilder(Material.CLAY_BRICK)
								.displayName(YELLOW + "" + BOLD + "Build")
								.lore(GRAY + "" + ITALIC + "Click here to build the text")
								.itemFlags(ItemFlag.HIDE_ENCHANTS)
								.enchant(Enchantment.DURABILITY, 1)
								.build(),
				clickEvent -> {

					Set<BlockState> cache = new HashSet<>();
					ListenableFuture<Set<Vector>> futureVectors = textLoader.getVectors();

					futureVectors.addListener(() -> {

						Set<Vector> vectors = new HashSet<>();
						try {
							vectors = futureVectors.get();
						} catch (InterruptedException | ExecutionException e) {
							e.printStackTrace();
						}

						World world = textLoader.getOrigin().getWorld();

						for (Vector vector : vectors) {
							cache.add(vector.toLocation(world).getBlock().getState());
							vector.toLocation(world).getBlock().setType(material);
						}
						plugin.getSessionHolder().getSession((Player) clickEvent.getWhoClicked()).cacheUndo(cache);

					}, runnable -> plugin.getServer().getScheduler().runTask(plugin, runnable));

				});

	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
}
