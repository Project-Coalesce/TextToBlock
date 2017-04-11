package com.coalesce.ttb.data;

public enum ConfigOptions {

	DEBUG("maxFontSize", 30);


	private String path;
	private Object defValue;

	/**
	 * @param path     The path in the TOML config.
	 * @param defValue The default value of this path.
	 */
	ConfigOptions(String path, Object defValue) {
		this.path = path;
		this.defValue = defValue;
	}

	/**
	 * Gets the path of the config option.
	 *
	 * @return The path of the config option.
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Gets the default value of the path.
	 *
	 * @return The default value.
	 */
	public Object getDefValue() {
		return defValue;
	}

}
