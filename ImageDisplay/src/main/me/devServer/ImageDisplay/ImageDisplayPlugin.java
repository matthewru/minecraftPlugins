package me.devServer.ImageDisplay;

import org.bukkit.plugin.java.JavaPlugin;

import me.devServer.ImageDisplay.commands.DisplayCommand;

public class ImageDisplayPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		new DisplayCommand(this);
	}
}
