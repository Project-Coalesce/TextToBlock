package com.coalesce.ttb.blocks;

import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class TTBConverter {

	public static Set<Vector> getTextBlocks(String text, Font font, float fontSize, Block origin) {

		font = font.deriveFont(fontSize);
		Vector originVector = origin.getLocation().toVector();
		Set<Vector> textLocations = new HashSet<>();

		Rectangle bounds = font.getStringBounds(text, new FontRenderContext(null, false, false)).getBounds();
		int width = (int)bounds.getWidth();
		int height = (int)bounds.getHeight();

		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D imageGraphics = bufferedImage.createGraphics();
		imageGraphics.setFont(font);
		imageGraphics.setColor(Color.BLACK);
		imageGraphics.drawString(text, bufferedImage.getMinX(), height);
		imageGraphics.dispose();

		//Start from the top since the image is upsidedown
		for (int y = 0; y < bufferedImage.getHeight(); y++) {
			for (int x = 0; x < bufferedImage.getWidth(); x++) {

				// White (0) is the background. Anything else is a pixel we want
				if (bufferedImage.getRGB(x, y) != 0) {
					//Invert the heights, because the text normally renders upside down
					textLocations.add(originVector.clone().add(new Vector(x, height - y, 0)));
				}
			}
		}

		return textLocations;
	}

}
