package com.coalesce.ttb.blocks;

import com.coalesce.ttb.TextToBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

public class VectorBuilder {
	
	private final TextToBlock plugin;
	private final Location location;
	private Orientation orientation;
	private Set<Vector> vectorSet;
	private final String text;
	private Material material;
	private float size;
	private Font font;
	private boolean underline = false;
	private boolean italics = false;
	private boolean bold = false;
	
	
	public VectorBuilder(String text, Location location, TextToBlock plugin) {
		this.text = text;
		this.location = location;
		this.plugin = plugin;
		
		/*
		 * Section handles font size fallback, material fallback, and font file fallback.
		 */
		String name = (String)plugin.getConfig("config").getEntry("font.fallbackFont").getValue();
		Material material = Material.valueOf((String)plugin.getConfig("config").getEntry("font.fallbackMaterial").getValue());
		float size = (float)plugin.getConfig("config").getEntry("font.fallbackFontSize").getValue();
		
		plugin.getFontLoader().loadFont(name, font -> {
			if (font == null) {
				try {
					throw new FileNotFoundException("Fallback font \"" + name + "\" does not exist.");
				}
				catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			this.font = font;
		});
		this.material = material;
		this.size = size;
	}
	
	/**
	 * Loads a font by name.
	 *
	 * @param name The name of the font wanting to be loaded.
	 */
	public VectorBuilder font(String name) {
		plugin.getFontLoader().loadFont(name, font -> {
			if (font == null) {
				try {
					throw new FileNotFoundException("Font \"" + name + "\" does not exist! Fallback font being used.");
				}
				catch (FileNotFoundException e) {
				}
			}
			this.font = font;
		});
		return this;
	}
	
	/**
	 * Sets the size of the font.
	 *
	 * @param size The font size.
	 */
	public VectorBuilder size(float size) {
		this.size = size;
		return this;
	}
	
	/**
	 * Sets the font to bold.
	 */
	public VectorBuilder bold() {
		this.bold = true;
		return this;
	}
	
	/**
	 * Sets the text material.
	 *
	 * @param material The material for this text.
	 */
	public VectorBuilder material(Material material) {
		if (material.isBlock()) {
			this.material = material;
		}
		return this;
	}
	
	/**
	 * Sets the font in italics.
	 */
	public VectorBuilder italic() {
		this.italics = true;
		return this;
	}
	
	/**
	 * Sets the font underlined.
	 */
	public VectorBuilder underline() {
		this.underline = true;
		return this;
	}
	
	/**
	 * Changes the orientation of the font
	 *
	 * @param orientation The orientation of this text.
	 */
	public VectorBuilder orientation(Orientation orientation) {
		this.orientation = orientation;
		return this;
	}
	
	/**
	 * Builds the final vector set for the text locations.
	 *
	 * @return A set of vectors for the text.
	 */
	public Set<Vector> build() {
		
		font = font.deriveFont(size);
		if (bold) font = font.deriveFont(Font.BOLD);
		if (italics) font = font.deriveFont(Font.ITALIC);
		if (underline) font = font.deriveFont(TextAttribute.UNDERLINE_ON);
		
		Vector origin =  location.toVector();
		Set<Vector> locations = new HashSet<>();
		
		//Ye this needs to be done.
		
		return locations;
	}
	
	
	private enum Orientation {
		
		NORTH(180),
		WEST(90),
		SOUTH(0),
		EAST(-90); //I dont know what else to add... Things like FLAT and VERTICAL are other ideas, you did mention something about flipped as well.
		
		int rotation;
		
		Orientation(int rotation) {
			this.rotation = rotation;
		}
		
		public int getRotation() {
			return rotation;
		}
	}
	
	
	/*
	THIS WILL BE REMOVED
	 */
	
	@Deprecated
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
