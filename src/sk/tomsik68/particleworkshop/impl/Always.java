package sk.tomsik68.particleworkshop.impl;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import sk.tomsik68.particleworkshop.api.IParticlePlaySituation;

public class Always implements IParticlePlaySituation{

    @Override
    public boolean playsEffect(Location location, Player owner) {
        return true;
    }

}
