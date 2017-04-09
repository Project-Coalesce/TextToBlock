package com.coalesce.ttb.data;

import java.io.File;

public class Configuration {

	private File fontfolder, path;

	public Configuration() {
		this.fontfolder = new File("plugins" + File.separator + "TextToBlock" + File.separator + "Fonts");
	}

	public File[] getFonts() {
		return fontfolder.listFiles();
	}

}
