package sk.tomsik68.particleworkshop.impl;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import sk.tomsik68.particleworkshop.api.IParticlePlaySituation;

public class InWater implements IParticlePlaySituation {
    private final boolean water;

    public InWater(boolean water) {
        this.water = water;
    }

    @Override
    public boolean playsEffect(Location location, Player owner) {
        return owner != null && (water == (owner.getLocation().getBlock().getType() == Material.WATER || owner.getLocation().getBlock().getType() == Material.STATIONARY_WATER));
    }

}
