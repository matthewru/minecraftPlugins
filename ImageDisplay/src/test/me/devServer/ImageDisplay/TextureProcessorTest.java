package me.devServer.ImageDisplay;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import org.bukkit.Material;
import org.junit.jupiter.api.Test;

public class TextureProcessorTest {

	private TextureProcessor textureProcessor = new TextureProcessor();
	private JSonUtil jsonUtil = new JSonUtil();

	@Test
	public void getMaterialColor() {
		Map<Color, Material> materialColor = textureProcessor.getMaterialColor();
		System.out.println(this.getClass().getClassLoader().getResource("color-material.json").getPath());
		jsonUtil.writeJSON(new File(this.getClass().getClassLoader().getResource("color-material.json").getPath()),
				materialColor);
	}
	
	@Test
	public void testGetMaterialColorFromJSON() {
		System.out.println(textureProcessor.getMaterialColorFromJSON());
		System.out.println(new ArrayList<>(textureProcessor.getMaterialColorFromJSON().keySet()));
	}

}
