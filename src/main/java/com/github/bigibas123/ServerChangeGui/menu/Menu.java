package com.github.bigibas123.ServerChangeGui.menu;

import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.Item;
import me.lucko.helper.menu.Slot;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.Collection;
import java.util.function.BiConsumer;

class Menu extends Gui {
	private final Collection<ServerItem> items;
	private final BiConsumer<Player, String> clickCallback;

	public Menu(Player player, Collection<ServerItem> items, String title, BiConsumer<Player, String> clickCallback) {
		super(player, getMenuSize(getMaxSlot(items) + 1), title);
		this.items = items;
		this.clickCallback = clickCallback;
	}

	private static int getMaxSlot(Collection<ServerItem> items) {
		return items.stream().mapToInt(ServerItem::getSlot).max().orElse(0);
	}

	@Override
	public void redraw() {
		if (this.isFirstDraw()) {
			for (ServerItem item : this.items) {
				Slot slot = this.getSlot(item.getSlot());
				slot.applyFromItem(Item.builder(item.stack)
						.bind(inventoryClickEvent -> {
							if (inventoryClickEvent.getWhoClicked() instanceof Player) {
								clickCallback.accept((Player) inventoryClickEvent.getWhoClicked(), item.name);
							}
						}, ClickType.LEFT, ClickType.RIGHT, ClickType.MIDDLE)
						.build());
			}
		}
	}
}
