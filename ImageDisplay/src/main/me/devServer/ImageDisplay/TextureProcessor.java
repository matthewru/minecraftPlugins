package me.devServer.ImageDisplay;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.bukkit.Material;

public class TextureProcessor {

	public Map<Color, Material> getMaterialColor() {

		JSonUtil jsonUtil = new JSonUtil();
		LinkedHashMap<String, String> materialFile = (LinkedHashMap<String, String>) jsonUtil
				.getJSONDefinition("material.json");
		LinkedHashMap<Color, Material> materialColor = new LinkedHashMap<Color, Material>();
		for (Map.Entry<String, String> entry : materialFile.entrySet()) {
			BufferedImage texture = getTextureImage(entry.getValue());
			Color averageRGB = getAverageRGB(texture);

			materialColor.put(averageRGB, Material.valueOf(entry.getKey()));
		}
		return materialColor;
	}

	public Map<Color, Material> getMaterialColorFromJSON() {
		JSonUtil jsonUtil = new JSonUtil();
		return jsonUtil.getColorMaterialJSONDefinition("color-material.json");
	}

	public Color getAverageRGB(BufferedImage bi) {
		long sumr = 0, sumg = 0, sumb = 0;
		int transparentPixels = 0;
		for (int x = 0; x < bi.getWidth(); x++) {
			for (int y = 0; y < bi.getHeight(); y++) {
				if (isTransparent(bi, x, y) == false) {
					Color pixel = new Color(bi.getRGB(x, y));
					sumr += pixel.getRed();
					sumg += pixel.getGreen();
					sumb += pixel.getBlue();
				} else {
					transparentPixels++;
				}

			}
		}
		int num = bi.getWidth() * bi.getHeight() - transparentPixels;
		return new Color((int) sumr / num, (int) sumg / num, (int) sumb / num);
	}

	private BufferedImage getTextureImage(String fileName) {
		BufferedImage texture = null;
		try {
			String path = String.format("textures/%s", fileName);
			texture = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return texture;
	}

	private Boolean isTransparent(BufferedImage img, int x, int y) {
		int pixel = img.getRGB(x, y);
		if ((pixel >> 24) == 0x00) {
			return true;
		}
		return false;
	}

}
