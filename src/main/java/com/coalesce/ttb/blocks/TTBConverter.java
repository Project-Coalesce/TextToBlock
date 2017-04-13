package com.coalesce.ttb.blocks;

import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class TTBConverter {

	public static Set<Vector> getTextBlocks(String text, Font font, Block origin, float fontSize, boolean bold, boolean italic, boolean underLine) {
		
		font = font.deriveFont(fontSize);
		
		if (bold) font = font.deriveFont(Font.BOLD);
		if (italic) font = font.deriveFont(Font.ITALIC);
		if (underLine) font = font.deriveFont(TextAttribute.UNDERLINE_ON);
		
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

		//Start from the top since the image is up-side-down
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

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
