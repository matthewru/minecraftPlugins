package me.devServer.ImageDisplay.commands;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.devServer.ImageDisplay.ColorProcessor;
import me.devServer.ImageDisplay.ImageDisplayPlugin;
import me.devServer.ImageDisplay.TextureProcessor;

public class DisplayCommand implements CommandExecutor {

	private ImageDisplayPlugin plugin;

	public DisplayCommand(ImageDisplayPlugin plugin) {
		this.plugin = plugin;
		plugin.getCommand("display").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players execute command");
			return true;
		}
		Player p = (Player) sender;
		Location loc = p.getLocation();
		ColorProcessor colorProcessor = new ColorProcessor();
		TextureProcessor textureProcessor = new TextureProcessor();
		String fileName = args[0];
		BufferedImage img = getImageFromServerFolder(fileName);
		Location currentLoc = new Location(p.getWorld(), loc.getX() + 1, loc.getY() + img.getHeight(), loc.getZ());
		Location startingLoc = new Location(p.getWorld(), loc.getX() + 1, loc.getY() + img.getHeight(), loc.getZ());
		Map<Color, Material> colorMaterial = textureProcessor.getMaterialColorFromJSON();
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				Color pixelColor = new Color(img.getRGB(x, y));
				List<Color> blockColors = new ArrayList<>(colorMaterial.keySet());
				Color closestColor = colorProcessor.getClosestColor(pixelColor, blockColors);
				Block block = currentLoc.getBlock();
				block.setType((colorMaterial.get(closestColor)));
				currentLoc.setY(currentLoc.getY() - 1);

			}
			currentLoc.setY(startingLoc.getY());
			currentLoc.setX(currentLoc.getX() + 1);
		}

		return true;

	}

	private BufferedImage getImageFromServerFolder(String fileName) {
		URL imgURL = null;
		try {
			imgURL = findFile(fileName, plugin.getServer().getWorldContainer()).toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		BufferedImage img = null;
		try {
			img = ImageIO.read(imgURL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	private File findFile(String name, File dir) {
		File[] list = dir.listFiles();
		if (list != null)
			for (File file : list) {
				if (file.isDirectory()) {
					findFile(name, file);
				} else if (name.equalsIgnoreCase(file.getName())) {
					return file;
				}
			}
		return null;
	}

}
