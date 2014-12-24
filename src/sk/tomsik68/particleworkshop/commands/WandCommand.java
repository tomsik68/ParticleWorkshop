package sk.tomsik68.particleworkshop.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import sk.tomsik68.particleworkshop.ParticleTaskData;
import sk.tomsik68.particleworkshop.ParticleWorkshopPlugin;
import sk.tomsik68.particleworkshop.PlayerWandData;
import sk.tomsik68.particleworkshop.api.IParticlePlayer;
import sk.tomsik68.particleworkshop.api.ParticlePlaySituations;
import sk.tomsik68.particleworkshop.listeners.PWSWandUsageListener;
import sk.tomsik68.particleworkshop.players.ParticlePlayerRegistry;
import sk.tomsik68.permsguru.EPermissions;

public class WandCommand extends CommandHandler {

	public WandCommand(EPermissions perms) {
		super(perms);
		setPlayerOnly(true);
		setPermission("pws.wand");
		setDescription("(De)Activates your particle wand. Don't give any arguments to disable wand.");
		setArgs("<effect> <name> [-r] [-d <data(number)>] [-s <situation>]");
	}

	@Override
	public void run(CommandSender sender, String[] args) throws Exception {
		if (args.length == 0) {
			Player player = (Player) sender;
			if (!player.hasMetadata(PWSWandUsageListener.WAND_METADATA_KEY)) {
				sendHelp(sender);
			} else {
				player.removeMetadata(PWSWandUsageListener.WAND_METADATA_KEY,
						ParticleWorkshopPlugin.getInstance());
				sender.sendMessage(ChatColor.GREEN
						+ "[ParticleWorkshop] Your wand has been disabled.");
			}
		} else if (args.length >= 2) {
			// activate wand using the player's current equipped item
			Player player = (Player) sender;
			if (player.getItemInHand() != null) {

				ParticleTaskData taskData = new ParticleTaskData(
						player.getUniqueId());
				PlayerWandData wandData = new PlayerWandData(taskData, player
						.getItemInHand().getType());
				// wandData.setWandType(player.getItemInHand().getType());
				IParticlePlayer particle = ParticlePlayerRegistry.instance
						.getParticlePlayer(args[0]);
				if (particle == null) {
					sendHelp(sender);
					return;
				}
				OptionParser parser = new OptionParser();

				parser.acceptsAll(Arrays.asList("r", "repeat"),
						"Plays the particle repeatedly");
				parser.acceptsAll(Arrays.asList("f", "follow"),
						"Keeps following the clicked entity");
				parser.acceptsAll(Arrays.asList("s", "situation"),
						"Plays particle only in special case")
						.withRequiredArg().ofType(ParticlePlaySituations.class)
						.defaultsTo(ParticlePlaySituations.ALWAYS);
				parser.acceptsAll(Arrays.asList("d", "data"),
						"Effect data(integer)").withRequiredArg()
						.ofType(Integer.class).defaultsTo(0);
				parser.acceptsAll(Arrays.asList("c", "count"), "Count(integer)")
						.withRequiredArg().ofType(Integer.class).defaultsTo(1);
				String[] args2 = new String[args.length - 2];
				System.arraycopy(args, 2, args2, 0, args.length - 2);
				OptionSet options = parser.parse(args);
				boolean follow = options.has("f");
				boolean repeat = options.has("r");

				int relativeC = 0;
				double[] relative = new double[] { 0, 0, 0 };

				@SuppressWarnings("unchecked")
				List<String> nonOptions = (List<String>) options
						.nonOptionArguments();
				// parse relative coords
				for (String s : nonOptions) {
					if (s.equals("~"))
						relative[relativeC++] = 0;
					if (s.startsWith("~") && (isDouble(s.replace("~", "")))) {
						relative[relativeC] = getDouble(s.replace("~", ""));
						++relativeC;
					}
				}

				Vector relativeVector = new Vector(relative[0], relative[1],
						relative[2]);

				ParticlePlaySituations situation = ParticlePlaySituations
						.valueOf(options.valueOf("s").toString());
				int data = (Integer) options.valueOf("d");
				int count = (Integer) options.valueOf("c");
				taskData.setCount(count);
				taskData.setFollow(follow);
				taskData.setRepeat(repeat);
				taskData.setSituation(situation);
				taskData.setEffectData(data);
				if (taskData.isFollow()) {
					taskData.setRelativeVector(relativeVector);
					// wandData has ZERO_VECTOR set
				} else {
					taskData.setRelativeVector(PlayerWandData.ZERO_VECTOR);
					wandData.setRelativeVector(relativeVector);
				}
				taskData.setParticleName(args[0]);
				player.setMetadata(
						PWSWandUsageListener.WAND_METADATA_KEY,
						new FixedMetadataValue(ParticleWorkshopPlugin
								.getInstance(), wandData));
				sender.sendMessage(ChatColor.GREEN
						+ "[ParticleWorkshop] Enjoy your particle wand :)");
			} else {
				player.sendMessage(ChatColor.RED
						+ "[ParticleWorkshop] Please hold an item to use as wand.");
			}
		} else {
			sendHelp(sender);
		}
	}

	private void sendHelp(CommandSender sender) {
		sender.sendMessage(ChatColor.RED
				+ "You must specify an effect. It must be one of these:");
		StringBuilder sb = new StringBuilder("Available Effects: ");
		Set<String> effects = ParticlePlayerRegistry.instance
				.getParticlePlayerNames();
		for (String effect : effects) {
			sb = sb.append(effect).append(',');
		}
		if (sb.length() > 0)
			sb = sb.deleteCharAt(sb.length() - 1);
		sender.sendMessage(sb.toString());
	}

}
