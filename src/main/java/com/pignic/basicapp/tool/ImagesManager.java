package com.pignic.basicapp.tool;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImagesManager {

	private static Map<String, Font> fontLibrary = new HashMap<String, Font>();

	private static Map<String, BufferedImage> imagesLibrary = new HashMap<String, BufferedImage>();

	public static Font getFont(final String key) {
		if (fontLibrary.get(key) == null) {
			try {
				fontLibrary.put(key,
						Font.createFont(Font.TRUETYPE_FONT, ImagesManager.class.getResourceAsStream("/fonts/" + key)));
				final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				ge.registerFont(fontLibrary.get(key));
			} catch (final FontFormatException e) {
				e.printStackTrace();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		return fontLibrary.get(key);
	}

	public static BufferedImage getImage(final String key) {
		if (imagesLibrary.get(key) == null) {
			try {
				imagesLibrary.put(key, ImageIO.read(ImagesManager.class.getResourceAsStream("/images/" + key)));
			} catch (final IOException e) {
				imagesLibrary.put(key, new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
				System.out.println(key + " not found !");
			}
		}
		return imagesLibrary.get(key);
	}
}
