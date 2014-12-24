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
import sk.tomsik68.particleworkshop.PlayerWandData;
import sk.tomsik68.particleworkshop.tasks.PlayParticleTask;

public class PWSWandUsageListener implements Listener {
	public static final String WAND_METADATA_KEY = "pws.wand";

	public PWSWandUsageListener() {

	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		PlayerWandData data = getWandData(event.getPlayer());
		if (data != null
				&& event.getAction() == Action.RIGHT_CLICK_BLOCK
				&& event.getPlayer().getItemInHand().getType()
						.equals(data.getItem())) {
			PlayParticleTask task = ParticleTaskFactory.createTaskOnLocation(
					data.getTaskData(), event.getClickedBlock().getLocation()
							.add(data.getRelativeVector()));
			ParticlesManager.instance.addTask(task);
		}
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		PlayerWandData data = getWandData(event.getPlayer());
		if (data != null
				&& event.getPlayer().getItemInHand().getType()
						.equals(data.getItem())) {

			PlayParticleTask task = ParticleTaskFactory.createTaskOnEntity(
					data.getTaskData(), event.getRightClicked());
			ParticlesManager.instance.addTask(task);
		}
	}

	private PlayerWandData getWandData(Player player) {
		for (MetadataValue value : player.getMetadata(WAND_METADATA_KEY)) {
			if (value.getOwningPlugin().equals(
					ParticleWorkshopPlugin.getInstance())) {
				PlayerWandData data = (PlayerWandData) value.value();
				return data;
			}
		}
		return null;
	}
}
