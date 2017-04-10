package com.coalesce.ttb.data;

import com.coalesce.plugin.CoModule;
import com.coalesce.plugin.CoPlugin;
import com.coalesce.ttb.TextToBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * FontLoader loads in fonts stored in the /fonts folder and stores them
 * to be accessed quickly later
 */
public class FontLoader extends CoModule {

	private Map<String, File> fontFiles;

	private ExecutorService executor;

	public FontLoader(@NotNull CoPlugin plugin) {
		super(plugin, "Font Loader");
	}

	@Override
	protected void onEnable() throws Exception {

		//Get all the files in the fonts folder
		fontFiles = Arrays.asList(getPlugin().getTtbConfiguration().getFontFolder().listFiles((dir, name) -> name.endsWith(".ttf")))
				.stream().collect(Collectors.toMap(file -> file.getName().toLowerCase().replace(".ttf", ""), file -> file));

		executor = Executors.newSingleThreadExecutor();
	}

	@Override
	protected void onDisable() throws Exception {

		//TODO: Stilbruch 4/9/2017 Unload things if needed.
	}

	/**
	 *  Threadsafe way to load in a {@link Font}. Once the file is loaded, callback is
	 *  called.
	 *
	 * @param fontName Name of the font to load in
	 * @param callback callback to call once the font is loaded
	 */
	public void loadFont(String fontName, Consumer<Font> callback){

		executor.execute(() -> {

			Font font = loadFont(fontName);
			callback.accept(font);

		});
	}

	/**
	 * Loads in the fonts in an unsafe way. Using this can lock up threads. It is recommended that you
	 * use {@link FontLoader#loadFont(String, Consumer)} instead, as it is tread safe.
	 *
	 * @param fontName The name of the file to load
	 * @return The {@link Font} that was loaded, or null if one is not found.
	 */
	@Nullable
	private Font loadFont(String fontName){

		try {
			File fontFile = fontFiles.get(fontName.toLowerCase());

			if (fontFile == null){
				return null;
			}

			return Font.createFont(Font.TRUETYPE_FONT, fontFile);

		} catch (Exception e) {
			error("Error loading font: "+ fontName);
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public TextToBlock getPlugin(){
		return (TextToBlock) super.getPlugin();
	}

}
