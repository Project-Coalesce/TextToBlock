package com.coalesce.ttb.config;

import org.jetbrains.annotations.NotNull;

import java.io.File;


public class FontsConfig {

	private static final FontsConfig DEFAULT = new FontsConfig();
	
	private int maxFontSize = 100;
	private boolean fontPermissions = false;
	private Operations operations = new Operations();
	private String fallbackFont = "blocked";
	private float fallbackFontSize = 12;


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
	
	public String getFallbackFont() {
		return fallbackFont;
	}
	
	public void setFallbackFont(String fallbackFont) {
		this.fallbackFont = fallbackFont;
	}
	
	public float getFallbackFontSize() {
		return fallbackFontSize;
	}
	
	public void setFallbackFontSize(float fallbackFontSize) {
		this.fallbackFontSize = fallbackFontSize;
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

}
