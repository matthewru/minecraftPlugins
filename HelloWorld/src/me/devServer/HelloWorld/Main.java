package me.devServer.HelloWorld;

import org.bukkit.plugin.java.JavaPlugin;

import me.devServer.HelloWorld.commands.HelloCommand;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		new HelloCommand(this);
	}

}
