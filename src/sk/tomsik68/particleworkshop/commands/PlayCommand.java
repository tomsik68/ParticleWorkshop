package sk.tomsik68.particleworkshop.commands;

import java.util.Arrays;
import java.util.List;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import sk.tomsik68.particleworkshop.ParticleTaskData;
import sk.tomsik68.particleworkshop.ParticleTaskFactory;
import sk.tomsik68.particleworkshop.ParticlesManager;
import sk.tomsik68.particleworkshop.PlayerWandData;
import sk.tomsik68.particleworkshop.api.IParticlePlayer;
import sk.tomsik68.particleworkshop.api.ParticlePlaySituations;
import sk.tomsik68.particleworkshop.commands.error.InvalidArgumentCountException;
import sk.tomsik68.particleworkshop.commands.error.InvalidArgumentException;
import sk.tomsik68.particleworkshop.players.ParticlePlayerRegistry;
import sk.tomsik68.particleworkshop.tasks.PlayParticleTask;
import sk.tomsik68.permsguru.EPermissions;

public class PlayCommand extends CommandHandler {

	public PlayCommand(EPermissions perms) {
		super(perms);
		setPlayerOnly(true);
		setPermission("pws.play");
		setDescription("Plays specified particle effect on player.");
		setArgs("<effect> <name> [-r(epeat)] [-f(ollow)] [-d <data>] [-s <situation>]");
	}

	@Override
	public void run(CommandSender sender, String[] args) throws Exception {
		if (args.length <= 1)
			throw new InvalidArgumentCountException();
		IParticlePlayer particlePlayer = ParticlePlayerRegistry.instance
				.getParticlePlayer(args[0]);
		if (particlePlayer == null)
			throw new InvalidArgumentException(args[0]);
		PlayParticleTask task = null;
		if (args.length >= 2) {
			ParticleTaskData data = new ParticleTaskData(
					((Player) sender).getUniqueId());
			int relativeC = 0;
			double[] relative = new double[] { 0, 0, 0 };

			OptionParser parser = new OptionParser();

			parser.acceptsAll(Arrays.asList("r", "repeat"),
					"Plays the particle repeatedly");
			parser.acceptsAll(Arrays.asList("f", "follow"),
					"Keeps following the player");
			parser.acceptsAll(Arrays.asList("s", "situation"),
					"Plays particle only in special case").withOptionalArg()
					.ofType(ParticlePlaySituations.class)
					.defaultsTo(ParticlePlaySituations.ALWAYS);
			parser.acceptsAll(Arrays.asList("d", "data"),
					"Effect data(integer)").withRequiredArg()
					.ofType(Integer.class);

			OptionSet options = parser.parse(args);
			data.setParticleName(args[0]);
			data.setSituation((ParticlePlaySituations) options.valueOf("s"));
			data.setRepeat(options.has("r"));
			data.setFollow(options.has("f"));
			if (options.has("d")) {
				data.setEffectData((Integer) options.valueOf("d"));

			}
			List<String> nonOptions = (List<String>) options
					.nonOptionArguments();

			for (String s : nonOptions) {
				if (s.equals("~")) {
					relative[relativeC] = 0;
					relativeC++;
				}
				if (s.startsWith("~") && (isDouble(s.replace("~", "")))) {
					relative[relativeC] = getDouble(s.replace("~", ""));
					relativeC++;
				}
			}

			Vector relativeVector = new Vector(relative[0], relative[1],
					relative[2]);
			data.setRelativeVector(PlayerWandData.ZERO_VECTOR);
			data.setCount(1);
			if (!data.isFollow())
				task = ParticleTaskFactory.createTaskOnLocation(data,
						((Player) sender).getLocation().add(relativeVector));
			else
				task = ParticleTaskFactory.createTaskOnEntity(data,
						(Player) sender);
		} else {
			ParticleTaskData data = new ParticleTaskData(
					((Player) sender).getUniqueId());
			data.setRepeat(false);
			data.setFollow(false);
			data.setParticleName(args[0]);
			data.setRelativeVector(PlayerWandData.ZERO_VECTOR);
			data.setSituation(ParticlePlaySituations.ALWAYS);
			data.setOneLocation(((Player) sender).getLocation());
			task = ParticleTaskFactory.createTask(data);
		}
		if (task != null) {
			sender.sendMessage(ChatColor.GREEN
					+ "[ParticleWorkshop] Particle added.");
			ParticlesManager.instance.addTask(task);
		}
	}
}
