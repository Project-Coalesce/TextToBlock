package com.coalesce.ttb.blocks;

import com.coalesce.ttb.TextToBlock;
import jdk.nashorn.internal.ir.BlockStatement;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;

public class SessionHolder {
    
    //A map of all the current user sessions.
    private final HashMap<UUID, TextSession> sessionMap = new HashMap<>();
    
    //Plugin instance.
    private TextToBlock plugin;
    
    /**
     * The text session holder.
     * @param plugin Plugin instance.
     */
    public SessionHolder(TextToBlock plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Gets a session from a user.
     *
     * Note: If a session doesn't exist, it will create one automatically.
     * @param user User to get the session from.
     * @return The user's TextSession.
     */
    public TextSession getSession(UUID user) {
        if (!sessionMap.containsKey(user)) {
            sessionMap.put(user, new TextSession(user));
        }
        return sessionMap.get(user);
    }
    
    /**
     * Removes a user from a session.
     *
     * Note: This should probably only be used on a player leave event.
     * @param user The user to remove from the sessionMap.
     *
     */
    public void removeSession(UUID user) {
        if (!sessionMap.containsKey(user)) return;
        sessionMap.remove(user);
    }
    
    /**
     * A TextSession for a user. Holds the undo and redo data.
     */
    public final class TextSession {
        
        //User
        private UUID user;
        
        private Set<BlockState> blocks;
        //The user's undoable history.
        private final Stack<Set<BlockState>> undoMap = new Stack<>();
        
        //The user's redoable history.
        private final Stack<Set<BlockState>> redoMap = new Stack<>();
    
        /**
         * Creates a new TextSession for the user.
         * @param user User to create session for.
         */
        public TextSession(UUID user) {
            this.user = user;
        }
    
        /**
         * Adds a set of vectors to a user's history.
         * @param vectorSet The set of vectors.
         */
        public void addHistory(Set<Vector> vectorSet) {
            Set<BlockState> blocks = new HashSet<>();
            Player player = Bukkit.getPlayer(user);
            vectorSet.forEach(vector -> {
                blocks.add(player.getWorld().getBlockAt(vector.toLocation(player.getWorld())).getState());
            });
            undoMap.push(blocks);
        }
    
        /**
         * Undos the last operation done by the user.
         */
        public void undo() {
            this.blocks = undoMap.peek();
            redoMap.push(this.blocks);
            Bukkit.getScheduler().runTask(plugin, () -> {
                undoMap.pop().forEach(state -> {
                    state.getBlock().setType(state.getType());
                });
            });
        }
    
        /**
         * Undos an operation at a certain index.
         * @param operation The operation index.
         */
        public void undo(int operation) {
            this.blocks = undoMap.get(operation);
            Bukkit.getScheduler().runTask(plugin, () -> {
                blocks.forEach(state -> {
                    state.getBlock().setType(state.getType());
                });
            });
            redoMap.push(this.blocks);
        }
    
        /**
         * Redos the last operation.
         */
        public void redo() {
            
        }
    
        /**
         * Redos an operation at a certain index.
         * @param operation The operation index.
         */
        public void redo(int operation) {
            
        }
    }
}
