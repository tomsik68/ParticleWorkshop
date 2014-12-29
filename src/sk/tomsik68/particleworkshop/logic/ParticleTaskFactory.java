package sk.tomsik68.particleworkshop.logic;

import org.apache.commons.lang.Validate;
import sk.tomsik68.particleworkshop.api.IParticlePlayer;
import sk.tomsik68.particleworkshop.api.LocationIterator;
import sk.tomsik68.particleworkshop.players.ParticlePlayerRegistry;

class ParticleTaskFactory {

	static final PlayParticleTask createTask(ParticleTaskData data) {
		if (data.getNumber() == -1) {
			data.setNumber(ParticlesManager.instance
					.getParticleTaskNumberFor(data.getOwnerId()));
		}
		LocationIterator itr = data.getLocation().createIterator();

		Validate.notNull(itr);
		IParticlePlayer particlePlayer = ParticlePlayerRegistry.instance
				.getParticlePlayer(data.getParticleName());
		Validate.notNull(particlePlayer);
		PlayParticleTask task = new PlayParticleTask(data, itr, particlePlayer);
		return task;
	}
}
