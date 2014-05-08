package sk.tomsik68.particleworkshop.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.joptsimple.OptionParser;
import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import sk.tomsik68.particleworkshop.ParticleWorkshopPlugin;
import sk.tomsik68.particleworkshop.PlayerWandData;
import sk.tomsik68.particleworkshop.api.IParticlePlaySituation;
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
        setArgs("<effect> [-r] [-d <data(number)>]");
    }

    @Override
    public void run(CommandSender sender, String[] args) throws Exception {
        if (args.length == 0) {
            Player player = (Player) sender;
            player.removeMetadata(PWSWandUsageListener.WAND_METADATA_KEY, ParticleWorkshopPlugin.getInstance());
            sender.sendMessage(ChatColor.GREEN + "[ParticleWorkshop] Your wand has been disabled.");
        } else {
            // activate wand using the player's current equipped item
            Player player = (Player) sender;
            if (player.getItemInHand() != null) {
                PlayerWandData wandData = new PlayerWandData();
                wandData.setWandType(player.getItemInHand().getType());
                IParticlePlayer particle = ParticlePlayerRegistry.instance.getParticlePlayer(args[0]);
                if (particle == null) {
                    sendHelp(sender);
                    return;
                }
                OptionParser parser = new OptionParser();

                parser.acceptsAll(Arrays.asList("r", "repeat"), "Plays the particle repeatedly");
                parser.acceptsAll(Arrays.asList("f", "follow"), "Keeps following the clicked entity");
                parser.acceptsAll(Arrays.asList("s", "situation"), "Plays particle only in special case").withRequiredArg().ofType(ParticlePlaySituations.class).defaultsTo(ParticlePlaySituations.ALWAYS);
                parser.acceptsAll(Arrays.asList("d", "data"), "Effect data(integer)").withRequiredArg().ofType(Integer.class).defaultsTo(0);

                OptionSet options = parser.parse(args);
                boolean follow = options.has("f");
                boolean repeat = options.has("r");

                int relativeC = 0;
                int[] relative = new int[] { 0, 0, 0 };
                
                List<String> nonOptions = options.nonOptionArguments();
                // parse relative coords
                for (String s : nonOptions) {
                    if (s.equals("~"))
                        relative[relativeC++] = 0;
                    if (s.startsWith("~") && (isInt(s.replace("~", "")))) {
                        relative[relativeC] = getInt(s.replace("~", ""));
                        ++relativeC;
                    }
                }

                Vector relativeVector = new Vector(relative[0], relative[1], relative[2]);

                IParticlePlaySituation situation = ParticlePlaySituations.valueOf(options.valueOf("s").toString()).normalize();
                int data = (Integer) options.valueOf("d");
                wandData.setFollow(follow);
                wandData.setRepeat(repeat);
                wandData.setSituation(situation);
                wandData.setData(data);
                wandData.setRelativeVector(relativeVector);
                wandData.setParticle(particle);

                player.setMetadata(PWSWandUsageListener.WAND_METADATA_KEY, new FixedMetadataValue(ParticleWorkshopPlugin.getInstance(), wandData));
                sender.sendMessage(ChatColor.GREEN + "[ParticleWorkshop] Enjoy your particle wand :)");
            } else {
                player.sendMessage(ChatColor.RED + "[ParticleWorkshop] Please hold an item to use as wand.");
            }
        }
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "You must specify an effect. It must be one of these:");
        StringBuilder sb = new StringBuilder("Available Effects: ");
        Set<String> effects = ParticlePlayerRegistry.instance.getParticlePlayerNames();
        for (String effect : effects) {
            sb = sb.append(effect).append(',');
        }
        if (sb.length() > 0)
            sb = sb.deleteCharAt(sb.length() - 1);
        sender.sendMessage(sb.toString());
    }

}
