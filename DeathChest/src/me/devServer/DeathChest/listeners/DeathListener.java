package me.devServer.DeathChest.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Chest.Type;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.devServer.DeathChest.Main;

public class DeathListener implements Listener {

	private static Main plugin;

	public DeathListener(Main plugin) {
		this.plugin = plugin;

		Bukkit.getPluginManager().registerEvents(this, plugin);

	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		Location loc = p.getLocation();
		Inventory inventory = p.getInventory();
		inventory.clear();
		e.setKeepInventory(true);
		inventory.addItem(new ItemStack(Material.COMPASS, 1));
		p.sendMessage("Death Location is X: " + (int) loc.getX() + " Y: " + (int) loc.getY() + " Z: " + (int) loc.getZ());
		ArrayList<ItemStack> allItems = (ArrayList<ItemStack>) e.getDrops();
		storeItemsInChests(getChests(loc, e), allItems);
		ArrayList<ItemStack> droppedItems = (ArrayList<ItemStack>) e.getDrops();
		e.getDrops().removeAll(droppedItems);
	}

	private boolean isDoubleChest(PlayerDeathEvent event) {
		List<ItemStack> allItems = event.getDrops();
		return allItems.size() > 27;
	}
	
	private List<Chest> getChests(Location loc, PlayerDeathEvent event) {
		List<Chest> chests = new ArrayList<>();
		if (isDoubleChest(event)) {
			Location loc2 = new Location(event.getEntity().getWorld(), loc.getX() + 1, loc.getY(), loc.getZ());
			Block block1 = loc.getBlock();
			Block block2 = loc2.getBlock();
			block1.setType(Material.CHEST);
			block2.setType(Material.CHEST);
			Chest chest1 = (Chest) block1.getState();
			Chest chest2 = (Chest) block2.getState();
			BlockData chestBlockState = loc.getBlock().getBlockData();
			BlockData chestBlockState2 = loc2.getBlock().getBlockData();
			((org.bukkit.block.data.type.Chest) chestBlockState).setType(Type.LEFT);
			loc.getBlock().setBlockData(chestBlockState);
			((org.bukkit.block.data.type.Chest) chestBlockState2).setType(Type.RIGHT);
			chests.add(chest1);
			chests.add(chest2);
		}
		else {
			loc.getBlock().setType(Material.CHEST);
			Chest chest = (Chest) loc.getBlock().getState();
			chests.add(chest);
		}
		
		return chests;
	}
	
	private void storeItemsInChests(List<Chest> chests, List<ItemStack> allItems) {
		if (chests.size() > 1) {
			for (int i = 0; i < allItems.size(); i++) {
				ItemStack items = allItems.get(i);
				if (null != items) {
					if (i <= 26) {
						chests.get(0).getBlockInventory().addItem(items);
					}
					else {
						chests.get(1).getBlockInventory().addItem(items);
					}
					
				}
				
			}
		}
		for (int i = 0; i < allItems.size(); i++) {
			ItemStack items = allItems.get(i);
			if (null != items) {
				chests.get(0).getBlockInventory().addItem(items);
			}
			
		}
	}
	
}
