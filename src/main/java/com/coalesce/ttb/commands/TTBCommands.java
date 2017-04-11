package com.coalesce.ttb.commands;

import com.coalesce.command.base.CmdContext;
import com.coalesce.command.base.Command;
import com.coalesce.ttb.TextToBlock;
import org.bukkit.ChatColor;

import static com.coalesce.command.base.Command.of;

public final class TTBCommands {
	
    public TTBCommands() {
        Command textCommand = of(this::text, "text").onlyPlayer();
    }
    
    /* command would ideally look like this: /text <{message}> [-f, -s, -i, -b, -u]
    -f = font
    -s = size
    -i = italics
    -b = bold
    -u = underline
     */
    private void text(CmdContext context) {
        if (context.noArgs(ChatColor.RED + "Please provide command arguments.")) return;
    }
    
    private void undo(CmdContext context) {
    	
    }
    
    private void redo(CmdContext context) {
	    
    }

}
