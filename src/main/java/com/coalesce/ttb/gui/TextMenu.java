package com.coalesce.ttb.gui;

import com.coalesce.gui.ItemBuilder;
import com.coalesce.gui.PlayerGui;
import com.coalesce.ttb.TextToBlock;
import com.coalesce.ttb.blocks.TextLoader;
import com.google.common.util.concurrent.ListenableFuture;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static org.bukkit.ChatColor.*;

public final class TextMenu extends PlayerGui {

	private TextLoader textLoader;

	public TextMenu(TextToBlock plugin, String fontName, String text, Player player) {
		super(plugin, 9, DARK_GRAY + "Text Menu");

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
				new ItemBuilder(Material.PAPER)
						.displayName(YELLOW + "Font Size")
						.lore(WHITE + "Current Font Size: " + GRAY + textLoader.getFontSize())
						.build(),
            
				(clicker, clickType) -> {

					float fontSize = textLoader.getFontSize();

					switch (clickType){
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

		//Underline
		addItem(viewer ->
				new ItemBuilder(Material.PAPER)
						.displayName(YELLOW + "Underline")
						.lore(textLoader.isUnderline() ? GREEN + "True" : RED + "False")
						.build(),
				(clicker, clickType) -> {

					clicker.playSound(clicker.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 3, 1);
					textLoader.setUnderline(!textLoader.isUnderline());

					update(clicker);
				});

		//Italics
		addItem(viewer ->
						new ItemBuilder(Material.PAPER)
								.displayName(YELLOW + "Italics")
								.lore(textLoader.isItalics() ? GREEN + "True" : RED + "False")
								.build(),
				(clicker, clickType) -> {

					clicker.playSound(clicker.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 3, 1);
					textLoader.setItalics(!textLoader.isItalics());

					update(clicker);
				});

		//Bold
		addItem(viewer ->
						new ItemBuilder(Material.PAPER)
								.displayName(YELLOW + "Bold")
								.lore(textLoader.isBold() ? GREEN + "True" : RED + "False")
								.build(),
				(clicker, clickType) -> {

					clicker.playSound(clicker.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 3, 1);
					textLoader.setBold(!textLoader.isBold());

					update(clicker);
				});

		setItem(8,
				viewer ->
				new ItemBuilder(Material.CLAY_BRICK)
						.displayName(YELLOW + "Build")
						.lore(GRAY + "Click here to build the text")
						.itemFlags(ItemFlag.HIDE_ENCHANTS)
						.enchant(Enchantment.DURABILITY, 1)
						.build(),
				(clicker, clickType) -> {

					ListenableFuture<Set<Vector>> futureVectors = textLoader.getVectors();

					futureVectors.addListener(() -> {

						Set<Vector> vectors = new HashSet<>();
						try {
							vectors = futureVectors.get();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (ExecutionException e) {
							e.printStackTrace();
						}

						Material material = textLoader.getMaterial();
						World world = textLoader.getOrigin().getWorld();

						for (Vector vector : vectors) {
							vector.toLocation(world).getBlock().setType(material);
						}

					}, runnable -> plugin.getServer().getScheduler().runTask(plugin, runnable));

				});
	}
}
