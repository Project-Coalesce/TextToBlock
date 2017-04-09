package com.coalesce.ttb.data;

import java.io.File;

/**
 * Created by NJDaeger on 4/9/2017.
 */
public class Configuration {

	private File fontfolder, path;

	public Configuration() {
		this.fontfolder = new File("plugins" + File.separator + "TextToBlock" + File.separator + "Fonts");
	}

	public File[] getFonts() {
		return fontfolder.listFiles();
	}

}
