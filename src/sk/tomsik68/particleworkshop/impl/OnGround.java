package sk.tomsik68.particleworkshop.impl;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import sk.tomsik68.particleworkshop.api.IParticlePlaySituation;

public class OnGround implements IParticlePlaySituation {
    private final boolean onGround;

    public OnGround(boolean ground) {
        this.onGround = ground;
    }

    @Override
    public boolean playsEffect(Location location, Player owner) {
        return owner != null && isPlayerOnGround(owner) == onGround;
    }

    private boolean isPlayerOnGround(Player player) {
        return player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR;
    }
}
