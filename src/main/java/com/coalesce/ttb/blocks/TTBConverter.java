package com.coalesce.ttb.blocks;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class TTBConverter {

	public static Set<Vector> getTextBlocks(String text, Font font, Location origin) {

		Vector originVector = origin.toVector();
		Set<Vector> textLocations = new HashSet<>();

		//Jlabel is only needed so we can gen the metrics
		FontMetrics fontMetrics = new JLabel().getFontMetrics(font);

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
