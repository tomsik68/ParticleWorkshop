package sk.tomsik68.particleworkshop.impl;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import sk.tomsik68.particleworkshop.api.IParticlePlaySituation;

public class Sneak implements IParticlePlaySituation {
    private boolean sneak;

    public Sneak(boolean b) {
        sneak = b;
    }

    @Override
    public boolean playsEffect(Location location, Player owner) {
        return owner != null && owner.isSneaking() == sneak;
    }

}
