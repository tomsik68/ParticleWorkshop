package sk.tomsik68.particleworkshop.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import sk.tomsik68.particleworkshop.commands.error.InvalidArgumentCountException;
import sk.tomsik68.particleworkshop.logic.ParticlesManager;
import sk.tomsik68.permsguru.EPermissions;

public class RemoveCommand extends CommandHandler {

	public RemoveCommand(EPermissions perms) {
		super(perms);
		setPlayerOnly(true);
		setPermission("pws.rm");
		setDescription("Removes specified particle effect. You need to own the effect to remove it...");
		setArgs("<effect number>");
	}

	@Override
	public void run(CommandSender sender, String[] args) throws Exception {
		if (args.length == 1) {
			if (!isInt(args[0])) {
				sender.sendMessage(ChatColor.RED + "'" + args[0]
						+ "' is not a valid number.");
				return;
			}
			if (ParticlesManager.instance.removeParticle(
					((Player) sender).getUniqueId(), getInt(args[0])))
				sender.sendMessage("[ParticleWorkshop] Your particle will disappear soon");
			else
				sender.sendMessage(ChatColor.RED
						+ "[ParticleWorkshop] That particle wasn't found! Please make sure it exists by /pws ls");
		} else
			throw new InvalidArgumentCountException();
	}
}
