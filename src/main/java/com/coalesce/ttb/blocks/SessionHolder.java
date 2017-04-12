package com.coalesce.ttb.blocks;

import com.coalesce.plugin.CoModule;
import com.coalesce.plugin.CoPlugin;
import com.coalesce.ttb.base.TextSession;
import com.coalesce.ttb.config.FontsConfig;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

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
		TextSession session = sessions.computeIfAbsent(player.getUniqueId(), uuid -> new TextSession());
		Stack<Set<BlockState>> redo = session.getRedo(), undo = session.getUndo();
		
		if (redo.size() >= config.getMaxOperations()) redo.remove(redo.lastElement());
		if (undo.size() >= config.getMaxOperations()) undo.remove(undo.lastElement());
		
    	return session;
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
