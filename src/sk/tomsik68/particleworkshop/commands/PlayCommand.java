package sk.tomsik68.particleworkshop.commands;

import java.util.Arrays;
import java.util.List;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import sk.tomsik68.particleworkshop.api.IParticlePlayer;
import sk.tomsik68.particleworkshop.api.ParticlePlaySituations;
import sk.tomsik68.particleworkshop.commands.error.InvalidArgumentCountException;
import sk.tomsik68.particleworkshop.commands.error.InvalidArgumentException;
import sk.tomsik68.particleworkshop.logic.ParticleTaskData;
import sk.tomsik68.particleworkshop.logic.ParticleTaskFactory;
import sk.tomsik68.particleworkshop.logic.ParticlesManager;
import sk.tomsik68.particleworkshop.logic.PlayParticleTask;
import sk.tomsik68.particleworkshop.logic.PlayerWandData;
import sk.tomsik68.particleworkshop.players.ParticlePlayerRegistry;
import sk.tomsik68.permsguru.EPermissions;

public class PlayCommand extends CommandHandler {

	public PlayCommand(EPermissions perms) {
		super(perms);
		setPlayerOnly(true);
		setPermission("pws.play");
		setDescription("Plays specified particle effect on player.");
		setArgs("<effect> [-r(epeat)] [-f(ollow)] [-d <data>] [-c <count>] [-s <situation>]");
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
		if (args.length >= 1) {
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
			parser.acceptsAll(Arrays.asList("c", "count"),
					"How many times to play the effect").withRequiredArg()
					.ofType(Integer.class).defaultsTo(1);

			String[] args2 = new String[args.length - 1];
			System.arraycopy(args, 1, args2, 0, args.length - 1);
			OptionSet options = parser.parse(args2);
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

			data.setCount((int) options.valueOf("c"));
			if (!data.isFollow()) {
				data.setRelativeVector(PlayerWandData.ZERO_VECTOR);
				task = ParticleTaskFactory.createTaskOnLocation(data,
						((Player) sender).getLocation().add(relativeVector));
			} else {
				data.setRelativeVector(relativeVector);
				task = ParticleTaskFactory.createTaskOnEntity(data,
						(Player) sender);
			}
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
			ParticlesManager.instance.addTask(task);
			sender.sendMessage(ChatColor.GREEN
					+ "[ParticleWorkshop] Particle added(number: "
					+ task.getTaskNumber() + ")");
		}
	}
}
