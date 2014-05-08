package sk.tomsik68.particleworkshop.tasks;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import sk.tomsik68.particleworkshop.api.IParticlePlaySituation;
import sk.tomsik68.particleworkshop.api.IParticlePlayer;
import sk.tomsik68.particleworkshop.api.LocationIterator;
import sk.tomsik68.particleworkshop.api.ParticlePlaySituations;

public class PlayParticleTask implements Runnable {
    private final LocationIterator locations;
    private final IParticlePlayer particle;
    private final int data;
    private boolean finished = false;
    private final IParticlePlaySituation[] rules;
    private final UUID owner;
    private final int count;

    public PlayParticleTask(LocationIterator locations, IParticlePlayer particle, int data, UUID player) {
        this(locations, particle, data, player, ParticlePlaySituations.ALWAYS.normalize());
    }

    public PlayParticleTask(LocationIterator locations, IParticlePlayer particle, int data, UUID player, IParticlePlaySituation... rulz) {
        this(locations, particle, data, player, 1, rulz);
    }

    public PlayParticleTask(LocationIterator locations, IParticlePlayer particle, int data, UUID player, int count,
            IParticlePlaySituation... rulz) {
        this.locations = locations;
        this.particle = particle;
        this.data = data;
        rules = rulz;
        owner = player;
        this.count = count;
    }

    @Override
    public void run() {
        if (locations.hasNext()) {
            for (int c = 0; c < count; ++c) {
                Location loc = locations.next();
                int x, y, z;
                x = loc.getBlockX();
                y = loc.getBlockY();
                z = loc.getBlockZ();
                boolean plays = true;
                for (IParticlePlaySituation rule : rules) {
                    // TODO get player by UUID plays &= rule.playsEffect(loc, );
                    if (!plays)
                        break;
                }
                if (plays)
                    particle.playParticle(loc.getWorld(), x, y, z, data);
            }
        } else
            remove();
    }

    public void remove() {
        finished = true;
    }

    public LocationIterator getIter() {
        return locations;
    }

    public IParticlePlayer getParticle() {
        return particle;
    }

    public int getData() {
        return data;
    }

    public boolean hasFinished() {
        return finished;
    }

    public UUID getOwner() {
        return owner;
    }

    public int getCount() {
        return count;
    }

    public IParticlePlaySituation[] getRules() {
        return rules;
    }
}
