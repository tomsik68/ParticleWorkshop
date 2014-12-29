package sk.tomsik68.particleworkshop.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.MetadataValue;

import sk.tomsik68.particleworkshop.ParticleWorkshopPlugin;
import sk.tomsik68.particleworkshop.logic.ParticleOnBlockLocation;
import sk.tomsik68.particleworkshop.logic.ParticleOnEntityLocation;
import sk.tomsik68.particleworkshop.logic.ParticleTaskData;
import sk.tomsik68.particleworkshop.logic.ParticlesManager;
import sk.tomsik68.particleworkshop.logic.PlayerWandData;

public class PWSWandUsageListener implements Listener {
	public static final String WAND_METADATA_KEY = "pws.wand";

	public PWSWandUsageListener() {

	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		PlayerWandData data = getWandData(event.getPlayer());
		if (data != null && event.getPlayer().getItemInHand().getType() != Material.AIR
				&& event.getAction() == Action.RIGHT_CLICK_BLOCK
				&& event.getPlayer().getItemInHand().getType()
						.equals(data.getItem())) {
			ParticleTaskData taskData = data.getTaskData();
			taskData.setLocation(new ParticleOnBlockLocation(event
					.getClickedBlock().getLocation()
					.add(data.getRelativeVector())));
			int number = ParticlesManager.instance.addParticle(taskData);
			event.getPlayer().sendMessage(
					"[ParticleWorkshop] Particle number:" + number);
		}
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		PlayerWandData data = getWandData(event.getPlayer());
		if (data != null
				&& event.getPlayer().getItemInHand().getType() != Material.AIR
				&& event.getPlayer().getItemInHand().getType()
						.equals(data.getItem())) {
			ParticleTaskData taskData = data.getTaskData();
			taskData.setLocation(new ParticleOnEntityLocation(event
					.getRightClicked(), data.getRelativeVector()));
			int number = ParticlesManager.instance.addParticle(taskData);
			event.getPlayer().sendMessage(
					"[ParticleWorkshop] Particle number:" + number);
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
