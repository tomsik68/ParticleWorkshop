package sk.tomsik68.particleworkshop.players;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;

public class BukkitParticlePlayer extends StandardParticlePlayer {

    public BukkitParticlePlayer(Effect e, int radius) {
        super(e, radius);
    }

    @Override
    public void playParticle(World world, int x, int y, int z, int data) {
        world.playEffect(new Location(world, x, y, z), effect, data, radius);
    }

    @Override
    public String name() {
        return effect.name();
    }

}
