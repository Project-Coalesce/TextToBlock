package com.coalesce.ttb.commands;

import com.coalesce.command.base.CmdContext;
import com.coalesce.command.base.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import static com.coalesce.command.base.Command.of;

public final class TTBCommands {
    
    //The command would ideally look like this: /text <{message}> [-f, -s, -i, -b, -u]
    private void text(CmdContext context) {
	    if (!context.isPlayer()) {
	        context.send(ChatColor.RED + "Sender must be a player.");
	        return;
        }
        if (context.getInput().size() < 1) {
	        context.send(ChatColor.RED + "Not enough arguments.");
	        return;
        }
    }
    
    private void undo(CmdContext context) {
	    
    }
    
    private void redo(CmdContext context) {
	    
    }

}
