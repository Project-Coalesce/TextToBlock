package com.coalesce.ttb.data;

import java.io.File;

/**
 * Created by NJDaeger on 4/9/2017.
 */
public class Configuration {

	private File fontFolder;

	public Configuration() {
		this.fontFolder = new File("plugins" + File.separator + "TextToBlock" + File.separator + "fonts");

		//Incase this is the first time running
		if (!fontFolder.exists()){
			fontFolder.mkdirs();
		}
	}

	public File getFontFolder() {
		return fontFolder;
	}
}
