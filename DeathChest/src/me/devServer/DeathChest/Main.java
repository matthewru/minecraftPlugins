package me.devServer.DeathChest;

import java.lang.reflect.Field;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import me.devServer.DeathChest.additional.Glow;
import me.devServer.DeathChest.commands.CompassCommand;
import me.devServer.DeathChest.listeners.DeathListener;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		new DeathListener(this);
		new CompassCommand(this);
		registerGlow();
		
	}
	
	public void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Glow glow = new Glow(new NamespacedKey(this, "Tracker"));
            Enchantment.registerEnchantment(glow);
        }
        catch (IllegalArgumentException e){
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
