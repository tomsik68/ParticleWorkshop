package sk.tomsik68.particleworkshop.logic;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import sk.tomsik68.particleworkshop.api.IParticlePlayer;
import sk.tomsik68.particleworkshop.api.LocationIterator;
import sk.tomsik68.particleworkshop.impl.AlwaysOnEntity;
import sk.tomsik68.particleworkshop.impl.OneLocation;
import sk.tomsik68.particleworkshop.players.ParticlePlayerRegistry;

public class ParticleTaskFactory {

	public static final PlayParticleTask createTaskOnEntity(
			ParticleTaskData data, Entity entity) {
		LocationIterator itr;
		if (data.getNumber() == -1) {
			data.setNumber(ParticlesManager.instance
					.getParticleTaskNumberFor(data.getOwnerId()));
		}
		if (data.isFollow()) {
			itr = new AlwaysOnEntity(entity, data.getRelativeVector(),
					data.isRepeat());
		} else
			itr = new OneLocation(entity.getLocation(), data.isRepeat());
		IParticlePlayer particlePlayer = ParticlePlayerRegistry.instance
				.getParticlePlayer(data.getParticleName());
		PlayParticleTask task = new PlayParticleTask(data, itr, particlePlayer,
				data.getEffectData(), data.getOwnerId(), data.getCount(),
				data.getNumber(), data.getSituation().normalize());
		return task;
	}

	public static final PlayParticleTask createTaskOnLocation(
			ParticleTaskData data, Location location) {
		if (data.getNumber() == -1) {
			data.setNumber(ParticlesManager.instance
					.getParticleTaskNumberFor(data.getOwnerId()));
		}
		LocationIterator itr;
		data.setOneLocation(location);
		itr = new OneLocation(location, data.isRepeat());
		IParticlePlayer particlePlayer = ParticlePlayerRegistry.instance
				.getParticlePlayer(data.getParticleName());
		PlayParticleTask task = new PlayParticleTask(data, itr, particlePlayer,
				data.getEffectData(), data.getOwnerId(), data.getCount(),
				data.getNumber(), data.getSituation().normalize());
		return task;
	}

	public static final PlayParticleTask createTask(ParticleTaskData data) {
		if (data.getNumber() == -1) {
			data.setNumber(ParticlesManager.instance
					.getParticleTaskNumberFor(data.getOwnerId()));
		}
		LocationIterator itr = null;
		if (!data.isFollow()) {
			itr = new OneLocation(data.getOneLocation(), data.isRepeat());
		}
		Validate.notNull(itr);
		IParticlePlayer particlePlayer = ParticlePlayerRegistry.instance
				.getParticlePlayer(data.getParticleName());
		PlayParticleTask task = new PlayParticleTask(data, itr, particlePlayer,
				data.getEffectData(), data.getOwnerId(), data.getCount(),
				data.getNumber(), data.getSituation().normalize());
		return task;
	}
}
