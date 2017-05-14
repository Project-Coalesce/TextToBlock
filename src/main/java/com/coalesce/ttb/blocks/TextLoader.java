package com.coalesce.ttb.blocks;

import com.coalesce.ttb.TextToBlock;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public final class TextLoader {

	private TextToBlock plugin;
	private Location origin;
	private TextLoader.TextOrientation orientation = TextOrientation.NORTH;
	private String text;
	private String fontName;
	private float fontSize = 12;
	private boolean italics = false;
	private boolean bold = false;

	public TextLoader(TextToBlock plugin, String text, String fontName, Location origin){
		this.plugin = plugin;
		this.text = text;
		this.fontName = fontName;
		this.origin = origin;
	}
	
	/**
	 * Gets a set of vectors from the input.
	 * @return A new Listenable Future for the set of vectors.
	 */
	public ListenableFuture<Set<Vector>> getVectors(){

		SettableFuture<Set<Vector>> settableFuture = SettableFuture.create();

		ListenableFuture<Font> fontFuture = plugin.getFontLoader().loadFont(fontName);
		fontFuture.addListener(() -> {
			
			//Get the font
			Font font = null;
			try {
				font = fontFuture.get();

			} catch (Exception e){
				e.printStackTrace();
			}

			//Setup basic font stuff
			font = font.deriveFont(fontSize);
			if (bold) font = font.deriveFont(Font.BOLD);
			if (italics) font = font.deriveFont(Font.ITALIC);

			//Generate image
			BufferedImage image = generateImage(font);

			//Setup variables
			Vector originVector = origin.toVector();
			Set<Vector> textVectors = new HashSet<>();
			int height = image.getHeight();
			int width = image.getWidth();

			for (int y = image.getMinY(); y < height; y++) {
				StringBuilder sb = new StringBuilder();

				for (int x = image.getMinX(); x < width; x++) {

					// White (0) is the background. Anything else is a pixel we want
					if (image.getRGB(x, y) != 0) {
						//Invert the heights, because the text normally renders upside down
						int realY = height - y;
						textVectors.add(originVector.clone()
								.add(orientation.xDelta.clone().multiply(x))
								.add(orientation.yDelta.clone().multiply(realY)));

						sb.append("X ");
					} else {
						sb.append(". ");
					}

				}
				System.out.println(sb.toString());
			}

			settableFuture.set(textVectors);

		}, runnable -> plugin.getServer().getScheduler().runTask(plugin, runnable));


		return settableFuture;
	}

	private BufferedImage generateImage(Font font){

		Graphics g = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();
		g.setFont(font);
		FontMetrics metrics = g.getFontMetrics();

		int width = metrics.stringWidth(text);
		int height = metrics.getAscent() + metrics.getDescent();

		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D imageGraphics = bufferedImage.createGraphics();
		imageGraphics.setFont(font);
		imageGraphics.setColor(Color.BLACK);
		imageGraphics.drawString(text, 0, height - metrics.getDescent());
		imageGraphics.dispose();

		return bufferedImage;
	}

	private TextToBlock getPlugin() {
		return plugin;
	}

	public boolean isBold() {
		return bold;
	}

	public void setBold(boolean bold) {
		this.bold = bold;
	}

	public boolean isItalics() {
		return italics;
	}

	public void setItalics(boolean italics) {
		this.italics = italics;
	}

	public float getFontSize() {
		return fontSize;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public TextOrientation getOrientation() {
		return orientation;
	}

	public void setOrientation(TextOrientation orientation) {
		this.orientation = orientation;
	}

	public Location getOrigin() {
		return origin;
	}

	public void setOrigin(Location origin) {
		this.origin = origin;
	}


	public enum TextOrientation {

		NORTH(	new Vector(0, 0, -1), 	new Vector(0, 1, 0)),
		WEST(	new Vector(-1, 0, 0), 	new Vector(0, 1, 0)),
		SOUTH(	new Vector(0, 0, 1), 	new Vector(0, 1, 0)),
		EAST(	new Vector(1, 0, 0), 	new Vector(0, 1, 0));

		//The change every time the x or y is increased in the image
		private Vector xDelta;
		private Vector yDelta;

		TextOrientation(Vector xDelta, Vector yDelta) {
			this.xDelta = xDelta;
			this.yDelta = yDelta;
		}
	}

}
