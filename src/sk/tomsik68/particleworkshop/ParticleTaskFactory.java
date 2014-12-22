package sk.tomsik68.particleworkshop;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import sk.tomsik68.particleworkshop.api.IParticlePlayer;
import sk.tomsik68.particleworkshop.api.LocationIterator;
import sk.tomsik68.particleworkshop.impl.AlwaysOnEntity;
import sk.tomsik68.particleworkshop.impl.OneLocation;
import sk.tomsik68.particleworkshop.players.ParticlePlayerRegistry;
import sk.tomsik68.particleworkshop.tasks.PlayParticleTask;

public class ParticleTaskFactory {

	public static final PlayParticleTask createTaskOnEntity(
			ParticleTaskData data, Entity entity) {
		LocationIterator itr;

		if (data.isFollow()) {
			itr = new AlwaysOnEntity(entity, data.getRelativeVector(),
					data.isRepeat());
		} else
			itr = new OneLocation(entity.getLocation().add(
					data.getRelativeVector()), data.isRepeat());
		IParticlePlayer particlePlayer = ParticlePlayerRegistry.instance
				.getParticlePlayer(data.getParticleName());
		PlayParticleTask task = new PlayParticleTask(itr, particlePlayer,
				data.getEffectData(), data.getOwnerId(), data.getCount(), data
						.getSituation().normalize());
		return task;
	}

	public static final PlayParticleTask createTaskOnLocation(
			ParticleTaskData data, Location location) {
		LocationIterator itr;
		itr = new OneLocation(location.add(data.getRelativeVector()),
				data.isRepeat());
		IParticlePlayer particlePlayer = ParticlePlayerRegistry.instance
				.getParticlePlayer(data.getParticleName());
		PlayParticleTask task = new PlayParticleTask(itr, particlePlayer,
				data.getEffectData(), data.getOwnerId(), data.getCount(), data
						.getSituation().normalize());
		return task;

	}
}
