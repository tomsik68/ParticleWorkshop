package sk.tomsik68.particleworkshop.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface IParticlePlaySituation {
    public boolean playsEffect(Location location, Player owner);
}
