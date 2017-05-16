package com.coalesce.ttb.blocks;

import com.coalesce.ttb.TextToBlock;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public final class TextLoader {
	
	private TextDirection direction;
	private final TextToBlock plugin;
	private boolean italics = false;
	private boolean bold = false;
	private float fontSize = 12;
	private String fontName;
	private Location origin;
	private TextFace face;
	private String text;
	
	public TextLoader(TextToBlock plugin, String text, String fontName, Location origin){
		this.direction = TextDirection.NORTH;
		this.face = TextFace.FORWARD;
		this.fontName = fontName;
		this.plugin = plugin;
		this.origin = origin;
		this.text = text;
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
								.add(direction.xDelta.clone().multiply(x))
								.add(face.yDelta.clone().multiply(realY)));
						
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
	
	/**
	 * Checks if the text generated is emboldened.
	 * @return True if in bold, false otherwise.
	 */
	public boolean isBold() {
		return bold;
	}
	
	/**
	 * Sets the text to bold or not.
	 * @param bold True emboldens the text, false does the opposite.
	 */
	public void setBold(boolean bold) {
		this.bold = bold;
	}
	
	/**
	 * Checks if the text is italicised.
	 * @return True if italicised, false otherwise.
	 */
	public boolean isItalics() {
		return italics;
	}
	
	/**
	 * Sets the text to italics or not.
	 * @param italics True italicises the text, false does the opposite.
	 */
	public void setItalics(boolean italics) {
		this.italics = italics;
	}
	
	/**
	 * Gets the current font size of the text.
	 * @return The text font size.
	 */
	public float getFontSize() {
		return fontSize;
	}
	
	/**
	 * Sets the font size of the text.
	 * @param fontSize
	 */
	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}
	
	/**
	 * Gets the name of the font being used.
	 * @return The font being used.
	 */
	public String getFontName() {
		return fontName;
	}
	
	/**
	 * Sets the font being used.
	 * @param fontName The font to be used.
	 */
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	
	/**
	 * Gets the text to be rendered.
	 * @return The text to be rendered.
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Sets the text to be rendered.
	 * @param text The text to be rendered.
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Gets the direction of the text.
	 * @return The text direction.
	 */
	public TextDirection getDirection() {
		return direction;
	}
	
	/**
	 * Sets the direction of the text.
	 * @param direction The new text direction.
	 */
	public void setDirection(TextDirection direction) {
		this.direction = direction;
	}
	
	/**
	 * Gets the way the text is facing.
	 * @return Gets the way the text is currently facing.
	 */
	public TextFace getFace() {
		return face;
	}
	
	/**
	 * Sets the way the text is facing.
	 * @param face The new text face.
	 */
	public void setFace(TextFace face) {
		this.face = face;
	}
	
	/**
	 * Gets the origin location for this text to render.
	 * @return The text origin.
	 */
	public Location getOrigin() {
		return origin;
	}
	
	/**
	 * Sets the origin location to render the text.
	 * @param origin The new text origin.
	 */
	public void setOrigin(Location origin) {
		this.origin = origin;
	}
	
	
	public enum TextDirection {
		
		NORTH(	new Vector(0, 0, -1)),
		WEST(	new Vector(-1, 0, 0)),
		SOUTH(	new Vector(0, 0, 1)),
		EAST(	new Vector(1, 0, 0));
		
		private Vector xDelta;
		
		TextDirection(Vector xDelta) {
			this.xDelta = xDelta;
		}
	}
	
	public enum TextFace {
		
		UPWARD(		new Vector(-1, 0, 0)),
		DOWNWARD(	new Vector(1, 0, 0)),
		FORWARD(	new Vector(0, 1, 0)),
		BACKWARD(	new Vector(0, -1, 0));
		
		private Vector yDelta;
		
		TextFace(Vector yDelta) {
			this.yDelta = yDelta;
		}
	}
}