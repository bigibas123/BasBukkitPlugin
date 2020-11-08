package com.github.bigibas123.serverchangegui.menu;

import com.github.bigibas123.serverchangegui.Config;
import com.github.bigibas123.serverchangegui.IMenu;
import com.github.bigibas123.serverchangegui.util.BungeeCord;
import com.github.bigibas123.serverchangegui.util.ChatHelper;
import com.github.bigibas123.serverchangegui.util.LogHelper;
import me.lucko.helper.Schedulers;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class MenuManager implements IMenu {

	private final Config config;
	private final BungeeCord bungee;
	private final LogHelper log;
	private final ItemManager itemManager;
	private boolean receivedUpdate;
	private String title;

	public MenuManager(Config config, BungeeCord bungee, LogHelper log) {
		this.config = config;
		this.bungee = bungee;
		this.log = log;
		this.itemManager = new ItemManager(config);
		this.receivedUpdate = false;
		this.title = config.getMenuTitle();
	}

	@Override
	public void requestUpdate() {
		this.bungee.getServers().whenComplete((servers, throwable) -> {
			if (throwable == null) {
				this.updateServers(servers);
			} else {
				log.WARNING("Error fetching/receiving servers: " + throwable.getLocalizedMessage());
				throwable.printStackTrace(log.asStream(Level.WARNING));
			}
		});
	}

	private void updateServers(List<String> servers) {
		this.receivedUpdate = true;
		for (String server : servers) {
			if (itemManager.addServer(server)) {
				log.INFO("Adding server: " + server + " to list");
			}
		}
	}

	@Override
	public boolean setItem(String server, ItemStack stack) {
		return itemManager.setItem(server, stack);
	}

	@Override
	public void save() {
		this.itemManager.getAllItems().forEach(si -> {
			config.setServerItem(si.name, si.stack);
			config.setServerSlot(si.name, si.slot);
		});
		config.setMenuTitle(this.title);
	}

	@Override
	public void open(Player player) {
		if (this.receivedUpdate) {
			log.INFO("Opening menu with items: "+this.itemManager.getAllItems());
			Menu menu = new Menu(player,
					this.itemManager.getAllItems(),
					title,
					bungee::connect
			);
			Schedulers.sync().run(menu::open);
		} else {
			new ChatHelper(player, ChatHelper.level.WARN).append("Menu not fetched").newLine(ChatHelper.level.DEFAULT)
					.append("Fetching now...").send();
			this.requestUpdate();
		}
	}

	@Override
	public void reload() {
		config.reload();
		itemManager.reloadItems();
	}

	@Override
	public Collection<String> getServers() {
		return this.itemManager.getAllItems().stream().map(ServerItem::getName).collect(Collectors.toList());
	}

	@Override
	public boolean isSlotFree(int slot) {
		return this.itemManager.isFree(slot);
	}

	@Override
	public boolean setSlot(String server, int slot) {
		return this.itemManager.setSlot(server,slot);
	}

	@Override
	public Integer[] getTakenSlots() {
		return this.itemManager.getTakenSlots();
	}

}
