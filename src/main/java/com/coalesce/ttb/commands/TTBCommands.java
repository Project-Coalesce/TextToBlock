package com.coalesce.ttb.commands;

import com.coalesce.command.registry.Cmd;
import com.coalesce.command.registry.Executor;
import org.bukkit.command.CommandSender;

public class TTBCommands {

	@Cmd(
			name = "text",
			desc = "This will be the desc",
			usage = "/text <message> -b(block) -s(size) -f(font) -t(thickness)",
			executor = Executor.PLAYER,
			min = 1
	)
	public void command(CommandSender sender, String alias, String[] args) {

		sender.sendMessage("It worked");

	}

}