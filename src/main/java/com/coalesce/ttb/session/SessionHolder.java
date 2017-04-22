package com.coalesce.ttb.session;

import com.coalesce.plugin.CoModule;
import com.coalesce.ttb.TextToBlock;
import com.coalesce.ttb.config.FontsConfig;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Manages all user TextSessions
 */
public final class SessionHolder extends CoModule {
    
    private final Map<UUID, TextSession> sessions = new HashMap<>();
    
    private final FontsConfig config;


    public SessionHolder(TextToBlock plugin) {
        super(plugin, "TTB User Sessions");
        this.config = plugin.getFontsConfig();
    }

	@Override
	protected void onEnable() throws Exception {}

	@Override
	protected void onDisable() throws Exception {
    	sessions.entrySet().removeIf(entry -> {
    		entry.getValue().clear();
    		return true;
		});
	}

	/**
     * Gets a session from a user.
	 *
     * @param player User to get the session from.
     * @return The user's TextSession.
	 *
	 * @apiNote If a session doesn't exist, it will create one automatically.
     */
    public TextSession getSession(Player player) {
		if (!sessions.containsKey(player.getUniqueId())) {
    		TextSession session = new TextSession(config.getMaxOperations());
    		sessions.put(player.getUniqueId(), session);
    		return session;
		}
		return sessions.get(player.getUniqueId());
    }
    
    /**
     * Removes a user from a session.
	 *
     * @param player The user to remove from the sessions.
     *
	 * @apiNote This should probably only be used on a player leave event.
     */
    public void removeSession(Player player) {
		sessions.remove(player.getUniqueId());
    }

}
