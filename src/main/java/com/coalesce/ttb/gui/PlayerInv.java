package com.coalesce.ttb.gui;

import com.coalesce.plugin.CoPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class PlayerInv implements Listener {
	
	/**
	 * The plugin
	 */
	protected final CoPlugin plugin;
	
	/**
	 * The size of the inventory
	 */
	protected final int size;
	/**
	 * The function to apply to get the title
	 */
	protected final Function<Player, String> title;
	/**
	 * The functions to apply to get each item
	 */
	private final Function<Player, ItemStack>[] items;
	
	/**
	 * The consumers to run when each item is clicked
	 */
	private final Consumer<InventoryClickEvent>[] listeners;
	
	/**
	 * Map of current inventories, entries are removed on close
	 */
	protected final Map<UUID, Inventory> inventories;
	
	private final Player player;
	
	public PlayerInv(CoPlugin plugin, int size, Function<Player, String> title, Player player) {
		
		this.player = player;
		this.plugin = plugin;
		this.size = size;
		this.title = title;
		this.items = new Function[size];
		this.listeners = new Consumer[size];
		this.inventories = new HashMap<>();
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
		Inventory inventory = Bukkit.createInventory(player, size, title.apply(player));
		player.openInventory(inventory);
		inventories.put(player.getUniqueId(), inventory);
	}
	
	public PlayerInv(CoPlugin plugin, int size, String title, Player player) {
		this(plugin, size, p -> title, player);
	}
	
	/**
	 * Adds an item to the next most available slot in the inventory.
	 * @param item The item to add.
	 * @param onClick The click event for this item.
	 * @return
	 */
	public PlayerInv addItem(Function<Player, ItemStack> item, Consumer<InventoryClickEvent> onClick) {
		Inventory inventory = inventories.get(player.getUniqueId());
		int i = inventory.firstEmpty();
		this.items[i] = item;
		this.listeners[i] = onClick;
		return this;
	}
	
	public PlayerInv setItem(int index, Function<Player, ItemStack> item, Consumer<InventoryClickEvent> onClick) {
		this.items[index] = item;
		this.listeners[index] = onClick;
		return this;
	}
	
	public void update() {
		Inventory inventory = inventories.get(player.getUniqueId());
		for (int i = 0; i < items.length; i++) {
			Function<Player, ItemStack> function = items[i];
			if (function != null) {
				ItemStack item = function.apply(player);
				inventory.setItem(i, item);
			}
		}
		player.updateInventory();
	}
	
	public Inventory getInventory() {
		return inventories.get(player.getUniqueId());
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player) || event.getClickedInventory() == null) {
			return;
		}
		Player player = (Player) event.getWhoClicked();
		Inventory inventory = inventories.get(player.getUniqueId());
		if (inventory == null) {
			return;
		}
		if (inventory.equals(event.getClickedInventory())) {
			event.setCancelled(true);
			Consumer<InventoryClickEvent> onClick = listeners[event.getSlot()];
			if (onClick != null) {
				try {
					onClick.accept(event);
				} catch (Exception e) {
					throw new RuntimeException("Failed to handle inventory click event", e);
				}
			}
		} else if (inventories.containsValue(event.getView().getTopInventory())) {
			event.setResult(Event.Result.DENY);
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInventoryDrag(InventoryDragEvent event) {
		if (inventories.containsValue(event.getInventory()) && event.getWhoClicked() instanceof Player) {
			event.setResult(Event.Result.DENY);
			event.setCancelled(true);
			((Player) event.getWhoClicked()).updateInventory();
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		this.onClose(event);
	}
	
	public void onClose(InventoryCloseEvent event) {
		if (!(event.getPlayer() instanceof Player) || !inventories.values().stream().anyMatch(inv -> event.getInventory().equals(inv))) {
			return;
		}
		
		inventories.remove(event.getPlayer().getUniqueId());
	}
}
