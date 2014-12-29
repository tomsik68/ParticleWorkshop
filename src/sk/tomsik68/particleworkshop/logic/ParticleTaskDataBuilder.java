package sk.tomsik68.particleworkshop.logic;

import java.util.UUID;

import org.apache.commons.lang.Validate;
import org.bukkit.util.Vector;

import sk.tomsik68.particleworkshop.api.ParticlePlaySituations;

public class ParticleTaskDataBuilder {
	private ParticleTaskData data;

	public ParticleTaskDataBuilder() {
		data = new ParticleTaskData();
	}

	public ParticleTaskDataBuilder setOwner(UUID owner) {
		data.setOwnerId(owner);
		return this;
	}

	public ParticleTaskDataBuilder setCount(int count) {
		data.setCount(count);
		return this;
	}

	public ParticleTaskDataBuilder setEffectData(int effectData) {
		data.setEffectData(effectData);
		return this;
	}

	public ParticleTaskDataBuilder setSituation(ParticlePlaySituations situation) {
		data.setSituation(situation);
		return this;
	}

	public ParticleTaskDataBuilder setParticleName(String particleName) {
		data.setParticleName(particleName);
		return this;
	}

	public ParticleTaskDataBuilder setLocation(ParticleLocation location) {
		data.setLocation(location);
		return this;
	}

	public ParticleTaskData build() {
		if (data.getSituation() == null)
			data.setSituation(ParticlePlaySituations.ALWAYS);
		Validate.notNull(data.getParticleName(),
				"Particle name must be specified!");
		Validate.notEmpty(data.getParticleName(),
				"Particle name must be specified!");
		return data;
	}
}
