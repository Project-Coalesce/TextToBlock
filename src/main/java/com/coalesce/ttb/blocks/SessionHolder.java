package com.coalesce.ttb.blocks;

import com.coalesce.ttb.TextToBlock;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;

public class SessionHolder {
    
    private final HashMap<UUID, TextSession> sessionMap = new HashMap<>();
    private TextToBlock plugin;
    
    public SessionHolder(TextToBlock plugin) {
        this.plugin = plugin;
    }
    
    public TextSession getSession(UUID user) {
        if (!sessionMap.containsKey(user)) {
            sessionMap.put(user, new TextSession(user));
        }
        return sessionMap.get(user);
    }
    
    public void removeSession(UUID user) {
        sessionMap.remove(user);
    }
    
    public final class TextSession {
        
        private UUID user;
        private final Set<BlockState> blocks = new HashSet<>();
        private final Stack<Set<BlockState>> edits = new Stack<>();
    
        public TextSession(UUID user) {
            this.user = user;
        }
    
        public void addHistory(Set<Vector> vectorSet) {
            Player player = Bukkit.getPlayer(user);
            vectorSet.forEach(vector -> {
                blocks.add(player.getWorld().getBlockAt(vector.toLocation(player.getWorld())).getState());
            });
            edits.push(blocks);
        }
    
        public void undo() {
            Bukkit.getScheduler().runTask(plugin, () -> {
                edits.pop().forEach(state -> {
                    state.getBlock().setType(state.getType());
                });
            });
        }
    
        public void undo(int operation) {
        
        }
        
        public void redo() {
            
        }
        
        public void redo(int operation) {
            
        }
    }
}
