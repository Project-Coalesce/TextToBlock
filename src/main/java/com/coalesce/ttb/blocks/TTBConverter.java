package com.coalesce.ttb.blocks;

import org.bukkit.block.Block;
import org.bukkit.util.Vector;
import sun.font.FontDesignMetrics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class TTBConverter {

	public static Set<Vector> getTextBlocks(String text, Font font, Block origin) {

		Vector originVector = origin.getLocation().toVector();
		Set<Vector> textLocations = new HashSet<>();

		FontMetrics fontMetrics = FontDesignMetrics.getMetrics(font);

		int baseTextHeight = fontMetrics.getHeight();
		int baseTextWidth = fontMetrics.stringWidth(text);

		BufferedImage bufferedImage = new BufferedImage(baseTextWidth, baseTextHeight, BufferedImage.TYPE_INT_ARGB);

		Graphics2D imageGraphics = bufferedImage.createGraphics();
		imageGraphics.setFont(font);
		imageGraphics.setColor(Color.BLACK);
		imageGraphics.drawString(text, 0, baseTextHeight);
		imageGraphics.dispose();

		for (int y = 0; y < baseTextHeight; y++) {
			for (int x = 0; x < baseTextWidth; x++) {

				// White (0) is the background. Anything else is a pixel we want
				if (bufferedImage.getRGB(x, y) != 0) {
					textLocations.add(originVector.clone().add(new Vector(x, y, 0)));
				}
			}
		}

		return textLocations;
	}

}
