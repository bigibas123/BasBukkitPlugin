package com.github.bigibas123.ServerChangeGui.menu;

import com.github.bigibas123.ServerChangeGui.Config;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.OptionalInt;
import java.util.stream.IntStream;

@RequiredArgsConstructor
class ItemManager {

	private final Config config;

	private final HashMap<String, ServerItem> map = new HashMap<>();

	public boolean addServer(String name) {
		if (this.map.containsKey(name)) {
			return false;
		} else {
			int slot = config.getServerSlot(name);
			ItemStack item = config.getServerItem(name);
			if (slot == -1) slot = getNextFreeSlot();
			this.map.put(name, new ServerItem(name, slot, item));
			return true;
		}
	}

	private int getNextFreeSlot() {
		OptionalInt m = this.getSlotStream().map(v -> v + 1).max();
		System.out.println(m);
		return m.orElse(0);
	}

	public boolean setItem(String server, ItemStack stack) {
		if (this.map.containsKey(server)) {
			this.map.put(server, this.map.get(server).withStack(stack));
			return true;
		} else {
			return false;
		}
	}


	public Collection<ServerItem> getAllItems() {
		return this.map.values();
	}

	public void reloadItems() {
		this.map.replaceAll((s, i) -> i.withSlot(config.getServerSlot(s)).withStack(config.getServerItem(s)));
	}

	public boolean isFree(int slot) {
		return getSlotStream().noneMatch(value -> value == slot);
	}

	@NotNull
	private IntStream getSlotStream() {
		return this.map.values().stream().mapToInt(value -> value.slot);
	}

	public boolean setSlot(String server, int slot) {
		if (this.map.containsKey(server)) {
			this.map.put(server, this.map.get(server).withSlot(slot));
			return true;
		} else {
			return false;
		}
	}

	public Integer[] getTakenSlots() {
		return this.getSlotStream().boxed().toArray(Integer[]::new);
	}
}
