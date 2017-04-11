package com.coalesce.ttb.blocks;

import com.coalesce.ttb.TextToBlock;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;

public class SessionHolder {
    
    //A map of all the current user sessions.
    private final Map<UUID, TextSession> sessionMap = new HashMap<>();
    
    /**
     * The text session holder.
     */
    public SessionHolder() {}
    
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
        
        //Sets of blocks.
        private Set<BlockState> blocks;
        
        //The user's undoable history.
        private final Stack<Set<BlockState>> undoMap = new Stack<>();
        
        //The user's redoable history.
        private final Stack<Set<BlockState>> redoMap = new Stack<>();
    
        /**
         * Creates a new TextSession for the user.
         * @param user User to create session for.
         */
        private TextSession(UUID user) {
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
         *
         * @return True if operation successful, false otherwise.
         */
        public boolean undo() {
            if (undoMap.isEmpty()) {
                return false;
            }
            this.blocks = undoMap.peek();
            redoMap.push(this.blocks);
            undoMap.pop().forEach(state -> {
                state.getBlock().setType(state.getType());
            });
            return true;
        }
    
        /**
         * Undos an operation at a certain index.
         * @param operation The operation index.
         *
         * @return True if operation successful, false otherwise.
         */
        public boolean undo(int operation) {
            if (undoMap.get(operation).isEmpty()) {
                return false;
            }
            this.blocks = undoMap.get(operation);
            blocks.forEach(state -> {
                state.getBlock().setType(state.getType());
            });
            redoMap.push(this.blocks);
            return true;
        }
    
        /**
         * Redos the last operation.
         *
         * @return True if operation successful, false otherwise.
         */
        public boolean redo() {
            if (redoMap.isEmpty()) {
                return false;
            }
            this.blocks = redoMap.peek();
            undoMap.push(this.blocks);
            redoMap.pop().forEach(state -> {
                state.getBlock().setType(state.getType());
            });
            return true;
        }
    
        /**
         * Redos an operation at a certain index.
         * @param operation The operation index.
         *
         * @return True if operation successful, false otherwise.
         */
        public boolean redo(int operation) {
            if (redoMap.get(operation).isEmpty()) {
                return false;
            }
            this.blocks = redoMap.get(operation);
            blocks.forEach(state -> {
                state.getBlock().setType(state.getType());
            });
            undoMap.push(this.blocks);
            return true;
        }
    }
}
