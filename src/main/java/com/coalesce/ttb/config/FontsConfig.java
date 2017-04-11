package com.coalesce.ttb.config;

import com.coalesce.config.Config;
import org.jetbrains.annotations.NotNull;

import java.io.File;


public class FontsConfig extends Config {

	private static final FontsConfig DEFAULT = new FontsConfig();
	
	private int maxFontSize = 100;
	private boolean fontPermissions = false;
	private Operations operations = new Operations();


	public int getMaxFontSize() {
		return maxFontSize;
	}

	public void setMaxFontSize(int maxFontSize) {
		this.maxFontSize = maxFontSize;
	}

	public int getMaxOperations() {
		return operations.getMaxOperations();
	}

	public void setMaxOperations(int maxOperations) {
		this.operations.setMaxOperations(maxOperations);
	}
	
	public boolean perFontPermissions() {
		return  fontPermissions;
	}
	
	public void setFontPermissions(boolean perFontPermissions) {
		this.fontPermissions = perFontPermissions;
	}


	private class Operations {

		private int maxOperations = 5;


		int getMaxOperations() {
			return maxOperations;
		}

		void setMaxOperations(int maxOperations) {
			this.maxOperations = maxOperations;
		}

	}


	public static FontsConfig load(@NotNull File file) {
		return load(DEFAULT, file);
	}

}
