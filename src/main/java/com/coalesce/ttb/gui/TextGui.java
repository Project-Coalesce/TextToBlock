package com.coalesce.ttb.gui;

import com.coalesce.gui.ItemBuilder;
import com.coalesce.gui.PlayerGui;
import com.coalesce.ttb.TextToBlock;
import com.coalesce.ttb.blocks.TextLoader;
import com.google.common.util.concurrent.ListenableFuture;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
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
	
	private short durability;
	private Material material;
	private TextLoader.TextFace face;
	private final TextToBlock plugin;
	private final TextLoader textLoader;
	private TextLoader.TextDirection direction;
	private static final float SOUND_VOLUME = 3;
	private static final Sound ACTION_SOUND = Sound.BLOCK_WOOD_BUTTON_CLICK_ON;
	private static final Sound INVALID_ACTION_SOUND = Sound.BLOCK_ANVIL_PLACE;
	
	
	/**
	 * Builds a new TextGui for the user.
	 *
	 * @param plugin The providing plugin.
	 * @param fontName The name of the current font.
	 * @param text The text that is going to be built.
	 * @param player The player this inventory belongs to.
	 * @param material The default material.
	 */
	public TextGui(TextToBlock plugin, String fontName, String text, Player player, Material material) {
		super(plugin, 9, DARK_GRAY + "Text Menu");
		
		this.plugin = plugin;
		this.durability = 0;
		this.material = material; //Default material
		this.textLoader = new TextLoader(plugin, text, fontName, player.getLocation());
		this.face = textLoader.getFace();
		this.direction = textLoader.getDirection();
		
		setupIcons();
	}
	
	private void setupIcons(){
		
		//Text
		addItem(viewer ->
						new ItemBuilder(Material.BOOK)
								.displayName(YELLOW + "" + BOLD + "Text")
								.lore(GRAY + "Current Text: " + RESET + textLoader.getText())
								.build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), INVALID_ACTION_SOUND, SOUND_VOLUME, 1);
				});
		
		//Font
		addItem(viewer ->
						new ItemBuilder(Material.PAPER)
								.displayName(YELLOW + "" + BOLD + "Font")
								.lore(GRAY + "Current Font: " + RESET + textLoader.getFontName())
								.build(),
				click -> {
					Player clicker = (Player) click.getWhoClicked();
					clicker.playSound(clicker.getLocation(), INVALID_ACTION_SOUND, SOUND_VOLUME, 1);
				});
		
		//Font size
		addItem(viewer ->
						new ItemBuilder(Material.EMERALD)
								.displayName(YELLOW + "" + BOLD + "Font Size")
								.lore(GRAY + "Current Font Size: " + RESET + textLoader.getFontSize())
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
					clicker.playSound(clicker.getLocation(), ACTION_SOUND, SOUND_VOLUME, 1);
					
					update(clicker);
				});
		
		//Italics
		addItem(viewer ->
						new ItemBuilder(Material.NETHER_STAR)
								.displayName(YELLOW + "" + BOLD + "Italics")
								.lore(textLoader.isItalics() ? GREEN + "True" : RED + "False")
								.build(),
				clickEvent -> {
					
					Player clicker = (Player) clickEvent.getWhoClicked();
					
					clicker.playSound(clicker.getLocation(), ACTION_SOUND, SOUND_VOLUME, 1);
					textLoader.setItalics(!textLoader.isItalics());
					
					update(clicker);
				});
		
		//Bold
		addItem(viewer ->
						new ItemBuilder(Material.NETHER_STAR)
								.displayName(YELLOW + "" + BOLD + "Bold")
								.lore(textLoader.isBold() ? GREEN + "True" : RED + "False")
								.build(),
				clickEvent -> {
					
					Player clicker = (Player) clickEvent.getWhoClicked();
					
					clicker.playSound(clicker.getLocation(), ACTION_SOUND, SOUND_VOLUME, 1);
					textLoader.setBold(!textLoader.isBold());
					
					update(clicker);
				});
		
		//Material Menu
		addItem(viewer ->
						new ItemBuilder(material)
								.displayName(YELLOW + "" + BOLD + "Material")
								.lore(GRAY + "Current: " + RESET + material.toString().substring(0, 1).toUpperCase() + material.toString().substring(1).toLowerCase().replace("_", " "))
								.durability(durability)
								.build(),
				clickEvent -> {
					Player clicker = (Player) clickEvent.getWhoClicked();
					clicker.playSound(clicker.getLocation(), ACTION_SOUND, 3, 1);
					new MaterialGui(plugin, material, this).open(clicker);
				});
		
		//Orientation Menu
		addItem(viewer ->
						new ItemBuilder(Material.COMPASS)
								.displayName(YELLOW + "" + BOLD + "Orientation")
								.lore(GRAY + "Current Direction: " + RESET + direction.name(), GRAY + "Current Face: " + RESET + face.name())
								.build(),
				clickEvent -> {
					Player clicker = (Player) clickEvent.getWhoClicked();
					clicker.playSound(clicker.getLocation(), ACTION_SOUND, 3, 1);
					new OrientationGui(plugin, this, material, durability, direction, face).open(clicker);
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
							Block block = vector.toLocation(world).getBlock();
							block.setType(material);
							block.setData((byte)durability);
						}
						plugin.getSessionHolder().getSession((Player) clickEvent.getWhoClicked()).cacheUndo(cache);
						
					}, runnable -> plugin.getServer().getScheduler().runTask(plugin, runnable));
					
				});
		
	}
	
	/**
	 * Sets the material to turn into text.
	 * @param material The new material.
	 */
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	/**
	 * Sets the durability of the material
	 * @param durability The durability to set the material to.
	 */
	public void setDurability(short durability) {
		this.durability = durability;
	}
	
	/**
	 * Sets the direction of the text.
	 * @param direction The new direction of the text.
	 */
	public void setDirection(TextLoader.TextDirection direction) {
		textLoader.setDirection(direction);
		this.direction = direction;
	}
	
	/**
	 * Sets the way the text will face.
	 * @param face The new face of the text.
	 */
	public void setFace(TextLoader.TextFace face) {
		textLoader.setFace(face);
		this.face = face;
	}
}