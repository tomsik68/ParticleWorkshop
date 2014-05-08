package sk.tomsik68.particleworkshop.api;

import org.bukkit.World;

public interface IParticlePlayer {
    public void playParticle(World world, int x, int y, int z, int data);

    /**
     * Returns name of effect in uppercase, so it can be reconstructed via
     * registry.
     *
     */
    public String name();
}
