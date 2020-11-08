package com.github.bigibas123.ServerChangeGui.menu;

import lombok.Value;
import lombok.With;
import org.bukkit.inventory.ItemStack;

/**
 * Object representing an item belonging to a server in a certain slot
 */
@Value
class ServerItem {
	/**
	 * The name of the server as reported by bungee
	 */
	public String name;
	/**
	 * the slot the server is in in the menu (0 indexed)
	 */
	@With
	public Integer slot;
	/**
	 * The item the server is displayed as
	 */
	@With
	public ItemStack stack;
}
