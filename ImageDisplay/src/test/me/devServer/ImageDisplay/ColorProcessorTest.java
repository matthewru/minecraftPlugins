package me.devServer.ImageDisplay;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class ColorProcessorTest {
	
	private ColorProcessor colorProcessor = new ColorProcessor();
	private TextureProcessor textureProcessor = new TextureProcessor();

	
	@Test
	public void testGetClosestColor() {
		System.out.println(colorProcessor.getClosestColor(Color.BLACK, new ArrayList<>(textureProcessor.getMaterialColorFromJSON().keySet())));
	}
}
