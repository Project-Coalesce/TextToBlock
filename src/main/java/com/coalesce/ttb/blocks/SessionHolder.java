package com.coalesce.ttb.blocks;

import com.coalesce.plugin.CoModule;
import com.coalesce.plugin.CoPlugin;
import com.coalesce.ttb.base.TextSession;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Manages all user TextSessions
 */
public final class SessionHolder extends CoModule {
    
    private final Map<UUID, TextSession> sessions = new HashMap<>();


    public SessionHolder(@NotNull CoPlugin plugin) {
        super(plugin, "TTB User Sessions");
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
     * @param uuid User to get the session from.
     * @return The user's TextSession.
	 *
	 * @apiNote If a session doesn't exist, it will create one automatically.
     */
    public @NotNull TextSession getSession(UUID uuid) {
    	return sessions.computeIfAbsent(uuid, newUuid -> new TextSession());
    }
    
    /**
     * Removes a user from a session.
	 *
     * @param user The user to remove from the sessions.
     *
	 * @apiNote This should probably only be used on a player leave event.
     */
    public void removeSession(@NotNull UUID user) {
		sessions.remove(user);
    }

}
