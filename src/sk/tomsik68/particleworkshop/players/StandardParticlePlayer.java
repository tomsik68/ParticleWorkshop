package sk.tomsik68.particleworkshop.players;

import org.bukkit.Effect;

import sk.tomsik68.particleworkshop.api.IParticlePlayer;

public abstract class StandardParticlePlayer implements IParticlePlayer {
    protected final Effect effect;
    protected final int radius;

    public StandardParticlePlayer(Effect e, int playerRadius) {
        effect = e;
        radius = playerRadius;
    }

}
