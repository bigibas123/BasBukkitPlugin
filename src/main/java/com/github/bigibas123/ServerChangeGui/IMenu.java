package com.github.bigibas123.ServerChangeGui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public interface IMenu {
	void requestUpdate();

	void open(Player player);

	void save();

	void reload();

	boolean setItem(String server, ItemStack stack);

	boolean setSlot(String server, int slot);

	boolean isSlotFree(int slot);

	Integer[] getTakenSlots();

	Collection<String> getServers();
}
