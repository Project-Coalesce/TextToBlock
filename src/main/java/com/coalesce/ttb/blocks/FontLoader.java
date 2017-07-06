package com.coalesce.ttb.blocks;

import com.coalesce.plugin.CoModule;
import com.coalesce.plugin.CoPlugin;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * FontLoader loads in fonts stored in the /fonts folder and stores them
 * to be accessed quickly later
 */
public class FontLoader extends CoModule {

	private static final ListeningExecutorService EXECUTOR = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

	private final Map<String, File> foundFonts = new HashMap<>();
	private final List<String> fonts = new ArrayList<>();

	public FontLoader(CoPlugin plugin) {
		super(plugin, "Font Loader");
	}

	@Override
	protected void onEnable() throws Exception {
		File fontsFolder = new File(getPlugin().getDataFolder(), "Fonts");
		if (!fontsFolder.exists()) fontsFolder.mkdirs();

		File[] fontFiles = fontsFolder.listFiles(((dir, name) -> name.endsWith(".ttf")));
		if (fontFiles == null) return;
		
		Stream.of(fontFiles).forEach(file -> {
			foundFonts.put(file.getName().split("\\.")[0].toLowerCase().replace(" ", "_"), file);
			fonts.add(file.getName().split("\\.")[0].toLowerCase().replace(" ", "_"));
		});
	}

	@Override
	protected void onDisable() throws Exception {
		foundFonts.clear();
	}
	
	
	/**
	 * Loads a font from the font map.
	 * @param fontName The name of the font you want to load.
	 * @return A new Listenable Future for the font.
	 */
	public ListenableFuture<Font> loadFont(String fontName) {
		return EXECUTOR.submit(() -> getFont(fontName));
	}

	private Font getFont(String fontName) {
		File fontFile = foundFonts.get(fontName.toLowerCase());
		if (fontFile == null) return null;

		try {
			return Font.createFont(Font.TRUETYPE_FONT, fontFile);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * A list of the loaded fonts.
	 * @return A list of the loaded fonts.
	 */
	public List<String> getLoadedFonts() {
		return fonts;
	}

}
