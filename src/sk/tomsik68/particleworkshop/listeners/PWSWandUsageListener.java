package sk.tomsik68.particleworkshop.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.MetadataValue;

import sk.tomsik68.particleworkshop.ParticleTaskFactory;
import sk.tomsik68.particleworkshop.ParticleWorkshopPlugin;
import sk.tomsik68.particleworkshop.ParticlesManager;
import sk.tomsik68.particleworkshop.ParticleTaskData;
import sk.tomsik68.particleworkshop.tasks.PlayParticleTask;

public class PWSWandUsageListener implements Listener {
	public static final String WAND_METADATA_KEY = "pws.wand";

	public PWSWandUsageListener() {

	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		ParticleTaskData data = getWandData(event.getPlayer());
		if (data != null
				&& event.getAction() == Action.RIGHT_CLICK_BLOCK
				&& event.getPlayer().getItemInHand().getType()
						.equals(data.getWandType())) {
			PlayParticleTask task = ParticleTaskFactory.createTaskOnLocation(
					data, event.getClickedBlock().getLocation());

			ParticlesManager.instance.addTask(task);
		}
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		ParticleTaskData data = getWandData(event.getPlayer());
		if (data != null
				&& event.getPlayer().getItemInHand().getType()
						.equals(data.getWandType())) {

			PlayParticleTask task = ParticleTaskFactory.createTaskOnEntity(
					data, event.getRightClicked());
			ParticlesManager.instance.addTask(task);
		}
	}

	private ParticleTaskData getWandData(Player player) {
		for (MetadataValue value : player.getMetadata(WAND_METADATA_KEY)) {
			if (value.getOwningPlugin().equals(
					ParticleWorkshopPlugin.getInstance())) {
				ParticleTaskData data = (ParticleTaskData) value.value();
				return data;
			}
		}
		return null;
	}
}
