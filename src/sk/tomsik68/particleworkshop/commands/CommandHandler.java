package sk.tomsik68.particleworkshop.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import sk.tomsik68.particleworkshop.commands.error.InvalidArgumentCountException;
import sk.tomsik68.particleworkshop.commands.error.InvalidArgumentException;
import sk.tomsik68.permsguru.EPermissions;

public abstract class CommandHandler implements CommandExecutor {
	private final EPermissions perms;
	private String permission = "", description = "";
	private boolean isPlayerOnly = true;
	private String argsDescription;

	public CommandHandler(EPermissions perms) {
		this.perms = perms;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2,
			String[] args) {
		if (perms.has(sender, permission)) {
			if (!isPlayerOnly || (isPlayerOnly && sender instanceof Player)) {
				try {
					run(sender, args);
				} catch (InvalidArgumentException e) {
					sender.sendMessage(ChatColor.RED + "Invalid argument '"
							+ e.getInvalidValue() + "'");
				} catch (InvalidArgumentCountException e) {
					sender.sendMessage(ChatColor.RED
							+ "Invalid argument count.");
				} catch (Exception e) {
					sender.sendMessage(ChatColor.RED
							+ "An exception occured while executing command. See console for more details.");
					e.printStackTrace();
				}
			} else {
				sender.sendMessage(ChatColor.RED
						+ "You need to be a player to run this command.");
			}
		} else {
			sender.sendMessage(arg1.getPermissionMessage());
		}
		return true;
	}

	/**
	 * 
	 * @param args
	 * @param sender
	 */
	public abstract void run(CommandSender sender, String[] args)
			throws Exception;

	public void setPlayerOnly(boolean isPlayerOnly) {
		this.isPlayerOnly = isPlayerOnly;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getArgs() {
		return argsDescription;
	}

	public void setArgs(String a) {
		argsDescription = a;
	}

	public double getDouble(String s) {
		return Double.parseDouble(s);
	}

	public boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
