package com.coalesce.ttb.blocks;

import com.coalesce.ttb.TextToBlock;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class TextLoader {

	private TextToBlock plugin;
	private Location origin;
	private TextLoader.TextOrientation orientation = TextOrientation.NORTH;
	private String text;
	private Material material = Material.STONE;
	private String fontName;
	private float fontSize = 12;
	private boolean underline = false;
	private boolean italics = false;
	private boolean bold = false;

	public TextLoader(TextToBlock plugin, String text, String fontName, Location origin){
		this.plugin = plugin;
		this.text = text;
		this.fontName = fontName;
		this.origin = origin;
	}

	public ListenableFuture<Set<Vector>> getVectors(){

		SettableFuture<Set<Vector>> settableFuture = SettableFuture.create();

		ListenableFuture<Font> fontFuture = plugin.getFontLoader().loadFont(fontName);
		fontFuture.addListener(() -> {

			Font font = null;
			try {
				font = fontFuture.get();

			} catch (Exception e){
				e.printStackTrace();
			}

			font = font.deriveFont(fontSize);

			if (bold) font = font.deriveFont(Font.BOLD);
			if (italics) font = font.deriveFont(Font.ITALIC);
			if (underline) font = font.deriveFont(TextAttribute.UNDERLINE_ON);

			Vector originVector = origin.toVector();
			Set<Vector> textVectors = new HashSet<>();

			Rectangle bounds = font.getStringBounds(text, new FontRenderContext(null, false, false)).getBounds();
			int width = (int)bounds.getWidth();
			//Multiplying by 1.5 here because for some reason the font bounds don't take parts of the text (such as a y)
			//that hang below the line
			int height = (int)(bounds.getHeight() * 1.5d);

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
						int realY = height - y;
						textVectors.add(originVector.clone().add(orientation.xDelta.clone().multiply(x)).add(orientation.yDelta.clone().multiply(realY)));
					}
				}
			}

			settableFuture.set(textVectors);

		}, runnable -> plugin.getServer().getScheduler().runTask(plugin, runnable));


		return settableFuture;
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

	public boolean isUnderline() {
		return underline;
	}

	public void setUnderline(boolean underline) {
		this.underline = underline;
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

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
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

	public TextToBlock getPlugin() {
		return plugin;
	}

	public void setPlugin(TextToBlock plugin) {
		this.plugin = plugin;
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
