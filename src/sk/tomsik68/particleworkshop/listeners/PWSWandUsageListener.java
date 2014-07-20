package sk.tomsik68.particleworkshop.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.MetadataValue;

import sk.tomsik68.particleworkshop.ParticleWorkshopPlugin;
import sk.tomsik68.particleworkshop.ParticlesManager;
import sk.tomsik68.particleworkshop.PlayerWandData;
import sk.tomsik68.particleworkshop.api.IParticlePlaySituation;
import sk.tomsik68.particleworkshop.api.IParticlePlayer;
import sk.tomsik68.particleworkshop.api.LocationIterator;
import sk.tomsik68.particleworkshop.impl.AlwaysOnEntity;
import sk.tomsik68.particleworkshop.impl.OneLocation;
import sk.tomsik68.particleworkshop.tasks.PlayParticleTask;

public class PWSWandUsageListener implements Listener {
    public static final String WAND_METADATA_KEY = "pws.wand";

    public PWSWandUsageListener() {
        
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        PlayerWandData data = getWandData(event.getPlayer());
        if (data != null && event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().getItemInHand().getType().equals(data.getWandType())) {
            int particleData = data.getData();
            boolean repeat = data.isRepeat();
            LocationIterator locations;
            locations = new OneLocation(event.getClickedBlock().getLocation().add(data.getRelativeVector()), repeat);
            IParticlePlaySituation situation = data.getSituation();
            IParticlePlayer particle = data.getParticle();

            PlayParticleTask task = new PlayParticleTask(locations, particle, particleData, event.getPlayer().getUniqueId(), situation);
            
            ParticlesManager.instance.addTask(task);
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        PlayerWandData data = getWandData(event.getPlayer());
        if (data != null && event.getPlayer().getItemInHand().getType().equals(data.getWandType())) {
            int particleData = data.getData();
            boolean repeat = data.isRepeat();
            LocationIterator locations = new AlwaysOnEntity(event.getRightClicked(), data.getRelativeVector(), repeat);

            IParticlePlaySituation situation = data.getSituation();
            IParticlePlayer particle = data.getParticle();

            PlayParticleTask task = new PlayParticleTask(locations, particle, particleData, event.getPlayer().getUniqueId(), situation);
            ParticlesManager.instance.addTask(task);
        }
    }

    private PlayerWandData getWandData(Player player) {
        for (MetadataValue value : player.getMetadata(WAND_METADATA_KEY)) {
            if (value.getOwningPlugin().equals(ParticleWorkshopPlugin.getInstance())) {
                PlayerWandData data = (PlayerWandData) value.value();
                return data;
            }
        }
        return null;
    }
}
