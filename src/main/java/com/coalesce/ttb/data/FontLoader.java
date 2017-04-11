package com.coalesce.ttb.data;

import com.coalesce.plugin.CoModule;
import com.coalesce.plugin.CoPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * FontLoader loads in fonts stored in the /fonts folder and stores them
 * to be accessed quickly later
 */
public class FontLoader extends CoModule {

	private static final Executor EXECUTOR = Executors.newCachedThreadPool();


	private final Map<String, File> foundFonts = new HashMap<>();

	public FontLoader(@NotNull CoPlugin plugin) {
		super(plugin, "Font Loader");
	}


	@Override
	protected void onEnable() throws Exception {
		File fontsFolder = new File(getPlugin().getDataFolder(), "Fonts");
		if (!fontsFolder.exists()) fontsFolder.mkdirs();

		File[] fontFiles = fontsFolder.listFiles(((dir, name) -> name.endsWith(".ttf")));
		if (fontFiles == null) return;

		Stream.of(fontFiles).forEach(file -> foundFonts.put(file.getName().split(".")[0].toLowerCase(), file));
	}

	@Override
	protected void onDisable() throws Exception {
		foundFonts.clear();
	}


	public void loadFont(String fontName, Consumer<Font> callback) {
		EXECUTOR.execute(() -> callback.accept(loadFont(fontName)));
	}

	private @Nullable Font loadFont(@NotNull String fontName) {
		File fontFile = foundFonts.get(fontName.toLowerCase());
		if (fontFile == null) return null;

		try {
			return Font.createFont(Font.TRUETYPE_FONT, fontFile);
		} catch (FontFormatException | IOException e) {
			error("Failed to load font " + fontName);
			e.printStackTrace();
		}

		return null;
	}

}
