package me.devServer.ImageDisplay;

import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.junit.jupiter.api.Test;

public class JSonReaderTest {
	
	private JSonUtil jsonReader = new JSonUtil();
	
	@Test
	public void testGetMaterialDefinition() {
		
		Map<String, String> def = jsonReader.getJSONDefinition("material.json");
		
		System.out.println(def);
	}
	
	@Test
	public void testWriteJson() {
		Map<Color, Material> colorMaterial = createColorMaterial();
		File file = new File(this.getClass().getClassLoader().getResource("color-material.json").getPath());
		jsonReader.writeJSON(file, colorMaterial);
	}

	
	private Map<Color, Material> createColorMaterial() {
		Map<Color, Material> colorMaterialMap = new HashMap<>();
		colorMaterialMap.put(Color.BLACK, Material.ACACIA_BOAT);
		colorMaterialMap.put(Color.BLUE, Material.ACACIA_FENCE);
		return colorMaterialMap;
	}
}
