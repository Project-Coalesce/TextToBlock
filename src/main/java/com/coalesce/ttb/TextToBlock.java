package com.coalesce.ttb;

import com.coalesce.gui.IconMenuListener;
import com.coalesce.plugin.CoPlugin;
import com.coalesce.ttb.blocks.FontLoader;
import com.coalesce.ttb.commands.TTBCommands;
import com.coalesce.ttb.config.FontsConfig;
import com.coalesce.ttb.session.SessionHolder;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;

public final class TextToBlock extends CoPlugin implements Listener {

	private FontsConfig fontsConfig;

	private FontLoader fontLoader;
	private SessionHolder sessionHolder;
	
	public TextToBlock() {
		this.displayName = "TextToBlock";
	}

	@Override
	public void onPluginEnable() throws Exception {
		
		fontsConfig = new FontsConfig(this);

		addModules(
				fontLoader = new FontLoader(this),
				sessionHolder = new SessionHolder(this),
				new TTBCommands(this));

		//Might not be the best way to do this
		PluginManager manager = Bukkit.getPluginManager();
		manager.registerEvents(new IconMenuListener(), this);
		manager.registerEvents(this, this);
		
	}

	@Override
	public void onPluginDisable() throws Exception {

	}
	
	public FontsConfig getFontsConfig() {
		return fontsConfig;
	}

	public FontLoader getFontLoader() {
		return fontLoader;
	}

	public SessionHolder getSessionHolder() {
		return sessionHolder;
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		getSessionHolder().removeSession(e.getPlayer());
	}

}
