package com.coalesce.ttb.session;

import com.coalesce.plugin.CoModule;
import com.coalesce.plugin.CoPlugin;
import com.coalesce.ttb.session.TextSession;
import com.coalesce.ttb.config.FontsConfig;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Manages all user TextSessions
 */
public final class SessionHolder extends CoModule {
    
    private final Map<UUID, TextSession> sessions = new HashMap<>();
    
    private final FontsConfig config;


    public SessionHolder(@NotNull CoPlugin plugin, FontsConfig config) {
        super(plugin, "TTB User Sessions");
        this.config = config;
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
    public @NotNull TextSession getSession(Player player) {
		return sessions.computeIfAbsent(player.getUniqueId(), uuid -> new TextSession(config.getMaxOperations()));
    }
    
    /**
     * Removes a user from a session.
	 *
     * @param player The user to remove from the sessions.
     *
	 * @apiNote This should probably only be used on a player leave event.
     */
    public void removeSession(@NotNull Player player) {
		sessions.remove(player.getUniqueId());
    }

}
