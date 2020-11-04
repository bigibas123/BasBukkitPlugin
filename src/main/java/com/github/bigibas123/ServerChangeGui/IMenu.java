package com.github.bigibas123.ServerChangeGui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public interface IMenu {
	void requestUpdate();

	void save();

	void open(Player player);

	void reload();

	boolean setItem(String server, ItemStack stack);

	Collection<String> getServers();

	boolean isSlotFree(int slot);

	boolean setSlot(String server, int slot);

	Collection<Integer> getTakenSlots();
}
