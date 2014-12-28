package sk.tomsik68.particleworkshop.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import sk.tomsik68.particleworkshop.api.ParticlePlaySituations;
import sk.tomsik68.particleworkshop.players.ParticlePlayerRegistry;
import sk.tomsik68.permsguru.EPermissions;

public class PWSCommand implements TabExecutor {
	private final Map<String, CommandHandler> subCommands = new HashMap<String, CommandHandler>();

	public PWSCommand(EPermissions perms) {
		subCommands.put("play", new PlayCommand(perms));
		subCommands.put("wand", new WandCommand(perms));
		subCommands.put("rm", new RemoveCommand(perms));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command c, String label,
			String[] args) {
		if (args.length >= 1) {
			if (args[0].equalsIgnoreCase("help")
					|| args[0].equalsIgnoreCase("?")
					|| args[0].equalsIgnoreCase("h")) {
				sendHelp(sender);
				return true;
			}
			String[] newArgs = new String[args.length - 1];
			System.arraycopy(args, 1, newArgs, 0, args.length - 1);

			if (subCommands.containsKey(args[0])) {
				return subCommands.get(args[0]).onCommand(sender, c, label,
						newArgs);
			} else {
				sender.sendMessage(ChatColor.RED
						+ "[ParticleWorkshop] Command not found.");
				sendHelp(sender);
			}
		}
		return true;
	}

	private void sendHelp(CommandSender sender) {
		sender.sendMessage(ChatColor.GOLD
				+ "================ParticleWorkshop help================");
		for (Entry<String, CommandHandler> entry : subCommands.entrySet()) {
			sender.sendMessage(String.format(ChatColor.GREEN + "/%s %s %s -"
					+ ChatColor.AQUA + " %s", "pws", entry.getKey(), entry
					.getValue().getArgs(), entry.getValue().getDescription()));
		}
	}

	public List<String> onTabComplete(CommandSender sender, Command command,
			String alias, String[] args) {
		ArrayList<String> result = new ArrayList<String>();

		if (command.getName().equalsIgnoreCase("pws")) {
			if (args.length == 0) {
				result.add("wand");
				result.add("play");
			} else if (args.length > 0) {
				if (args[0].equalsIgnoreCase("play")) {
					result.addAll(ParticlePlayerRegistry.instance
							.getParticlePlayerNames());
					result.add("-r");
					result.add("--repeat");
					result.add("-f");
					result.add("--follow");
					for (ParticlePlaySituations sit : ParticlePlaySituations
							.values()) {
						result.add(sit.name());
					}
				}
			}
		}
		return result;
	}
}