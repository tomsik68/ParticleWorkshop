package sk.tomsik68.particleworkshop.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.joptsimple.OptionParser;
import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import sk.tomsik68.particleworkshop.ParticlesManager;
import sk.tomsik68.particleworkshop.api.ParticlePlaySituations;
import sk.tomsik68.particleworkshop.api.IParticlePlayer;
import sk.tomsik68.particleworkshop.api.LocationIterator;
import sk.tomsik68.particleworkshop.commands.error.InvalidArgumentCountException;
import sk.tomsik68.particleworkshop.commands.error.InvalidArgumentException;
import sk.tomsik68.particleworkshop.impl.AlwaysOnEntity;
import sk.tomsik68.particleworkshop.impl.OneLocation;
import sk.tomsik68.particleworkshop.players.ParticlePlayerRegistry;
import sk.tomsik68.particleworkshop.tasks.PlayParticleTask;
import sk.tomsik68.permsguru.EPermissions;

public class PlayCommand extends CommandHandler {

    public PlayCommand(EPermissions perms) {
        super(perms);
        setPlayerOnly(true);
        setPermission("pws.play");
        setDescription("Plays specified particle effect on player.");
        setArgs("<effect> [-r] [-f] [-d <data>] [-s <situation>]");
    }

    @Override
    public void run(CommandSender sender, String[] args) throws Exception {
        if(args.length == 0)
            throw new InvalidArgumentCountException();
        IParticlePlayer particlePlayer = ParticlePlayerRegistry.instance.getParticlePlayer(args[0]);
        if (particlePlayer == null)
            throw new InvalidArgumentException(args[0]);
        PlayParticleTask task = null;
        if (args.length > 1) {
            boolean repeat = false;
            boolean follow = false;
            int data = 0;
            int relativeC = 0;
            int[] relative = new int[] { 0, 0, 0 };

            OptionParser parser = new OptionParser();

            parser.acceptsAll(Arrays.asList("r", "repeat"), "Plays the particle repeatedly");
            parser.acceptsAll(Arrays.asList("f", "follow"), "Keeps following the player");
            parser.acceptsAll(Arrays.asList("s", "situation"), "Plays particle only in special case").withOptionalArg().ofType(ParticlePlaySituations.class).defaultsTo(ParticlePlaySituations.ALWAYS);
            parser.acceptsAll(Arrays.asList("d", "data"), "Effect data(integer)").withRequiredArg().ofType(Integer.class);

            OptionSet options = parser.parse(args);
            repeat = options.has("r");
            follow = options.has("f");
            if (options.has("d")) {
                data = (Integer) options.valueOf("d");
            }
            List<String> nonOptions = options.nonOptionArguments();

            for (String s : nonOptions) {
                if(s.equals("~"))
                    relative[relativeC++] = 0;
                if (s.startsWith("~") && (isInt(s.replace("~", "")))) {
                    relative[relativeC] = getInt(s.replace("~", ""));
                    ++relativeC;
                }
            }

            Vector relativeVector = new Vector(relative[0], relative[1], relative[2]);
            LocationIterator itr;
            if (follow) {
                itr = new AlwaysOnEntity((Player) sender, relativeVector, repeat);
            } else
                itr = new OneLocation(((Player) sender).getLocation().add(relativeVector), repeat);
            task = new PlayParticleTask(itr, particlePlayer, data, ((Player)sender).getUniqueId(), ParticlePlaySituations.ALWAYS.normalize());
        } else {
            task = new PlayParticleTask(new OneLocation(((Player) sender).getLocation(), false), particlePlayer, 4, ((Player)sender).getUniqueId());
        }
        if (task != null){
            sender.sendMessage(ChatColor.GREEN+"[ParticleWorkshop] Particle added.");
            ParticlesManager.instance.addTask(task);
        }
    }
}
