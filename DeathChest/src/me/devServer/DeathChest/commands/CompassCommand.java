package me.devServer.DeathChest.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.devServer.DeathChest.Main;
import me.devServer.DeathChest.additional.Glow;


public class CompassCommand implements CommandExecutor{
	
	private Main plugin;
	
	public CompassCommand(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("compassActivate").setExecutor(this);
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players execute command");
			return true;
		}
		Player p = (Player) sender;
		ItemStack mainhand = p.getInventory().getItemInMainHand();
		ItemMeta itemmeta = mainhand.getItemMeta();
		if (p.hasPermission("compass.use")) {
			if (mainhand.getType() != Material.COMPASS) {
				p.sendMessage("You must equip a compass.");
			}
			else {
				Location loc = new Location(p.getWorld(), Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
				p.setCompassTarget(loc);
				itemmeta.setDisplayName("Tracking X: " + loc.getX() + " Y: " + loc.getY() + " Z: " + loc.getZ());
				Glow glow = new Glow(new NamespacedKey(plugin, "Tracker"));
				itemmeta.addEnchant(glow, 1, true);
				mainhand.setItemMeta(itemmeta);
				
			}
			return true;
		} else {
			p.sendMessage("You do not have permission to execute command");
		}
		
		
		
		
		
		return false;
	}
}
