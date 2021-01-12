package me.devServer.ImageDisplay;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColorProcessor {

	public Color getClosestColor(Color color, List<Color> colors) {
		Map<Color, Float> colorDistances = new HashMap<Color, Float>();
		for (int i = 0; i < colors.size(); i++) {
			colorDistances.put(colors.get(i), getColorDistance(color, colors.get(i)));
		}
		List<Float> distances = new ArrayList<Float>(colorDistances.values());
		float smallestDistance = Collections.min(distances);
		Color closestColor = getKeyFromValue(colorDistances, smallestDistance);
		return closestColor;

	}

	private float getColorDistance(Color color1, Color color2) {
		return (float) Math.sqrt(
				Math.pow((color2.getRed() - color1.getRed()), 2) + Math.pow((color2.getGreen() - color1.getGreen()), 2)
						+ Math.pow((color2.getBlue() - color1.getBlue()), 2));
	}

	private Color getKeyFromValue(Map<Color, Float> hm, Object value) {
		for (Color o : hm.keySet()) {
			if (hm.get(o).equals(value)) {
				return o;
			}
		}
		return null;
	}

}
