package me.devServer.ImageDisplay;

import java.awt.Color;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Material;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSonUtil {

	@SuppressWarnings("unchecked")
	public void writeJSON(File json, Map<Color, Material> colorMaterialMap) {
		PrintWriter writer = null;
		JSONArray colorMaterialArray = new JSONArray();
		try {
			writer = new PrintWriter(json);
			for (Map.Entry<Color, Material> entry : colorMaterialMap.entrySet()) {
				Color color = entry.getKey();
				JSONObject obj = new JSONObject();
				JSONObject colorObj = new JSONObject();
				colorObj.put("r", ((Integer) color.getRed()).toString());
				colorObj.put("g", ((Integer) color.getGreen()).toString());
				colorObj.put("b", ((Integer) color.getBlue()).toString());
				obj.put("color", colorObj);
				obj.put("material", entry.getValue().toString());
				colorMaterialArray.add(obj);
			}
			writer.write(colorMaterialArray.toJSONString());
			if (writer != null) {
				writer.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Map<Color, Material> getColorMaterialJSONDefinition(String fileName) {
		JSONParser jsonParser = new JSONParser();
		JSONArray materialList = null;
		InputStream materialJSONInputStream = null;
		materialJSONInputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
		try (InputStreamReader reader = new InputStreamReader(materialJSONInputStream)) {
			Object obj = jsonParser.parse(reader);
			materialList = (JSONArray) obj;
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<Color, Material> map = parseColorMaterialObject(materialList);

		return map;
	}

	public Map<String, String> getJSONDefinition(String fileName) {

		JSONParser jsonParser = new JSONParser();
		JSONArray materialList = null;
		InputStream materialJSONInputStream = null;
		materialJSONInputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
		try (InputStreamReader reader = new InputStreamReader(materialJSONInputStream)) {
			Object obj = jsonParser.parse(reader);

			materialList = (JSONArray) obj;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return parseMaterialObject(materialList);
	}

	@SuppressWarnings("unchecked")
	private Map<Color, Material> parseColorMaterialObject(JSONArray colorList) {
		Map<Color, Material> map = new LinkedHashMap<>();

		if (colorList != null) {
			colorList.forEach(m -> {

				JSONObject jo = (JSONObject) m;
				JSONObject color = (JSONObject) jo.get("color");
				Map<String, String> colorMap = parseColorObject(color);
				String material = (String) jo.get("material");
				Color colorObject = new Color(Integer.valueOf(colorMap.get("r")), Integer.valueOf(colorMap.get("g")),
						Integer.valueOf(colorMap.get("b")));
				Material materialObject = Material.valueOf(material);

				map.put(colorObject, materialObject);

			});
		}
		return map;
	}

	private Map<String, String> parseColorObject(JSONObject color) {
		Map<String, String> map = new LinkedHashMap<>();

		String red = (String) color.get("r");
		String green = (String) color.get("g");
		String blue = (String) color.get("b");

		map.put("r", red);
		map.put("g", green);
		map.put("b", blue);
		return map;
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> parseMaterialObject(JSONArray materialList) {
		Map<String, String> map = new LinkedHashMap<>();

		if (materialList != null) {
			materialList.forEach(m -> {

				JSONObject jo = (JSONObject) m;
				String name = (String) jo.get("name");
				String fileName = (String) jo.get("file");

				map.put(name, fileName);

			});
		}
		return map;
	}

}
