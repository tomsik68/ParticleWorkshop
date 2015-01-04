package sk.tomsik68.particleworkshop.logic;

import java.util.UUID;

import org.bukkit.Location;

import sk.tomsik68.particleworkshop.api.IParticlePlaySituation;
import sk.tomsik68.particleworkshop.api.IParticlePlayer;
import sk.tomsik68.particleworkshop.api.LocationIterator;

class PlayParticleTask implements Runnable {
	private final LocationIterator locations;
	private final IParticlePlayer particle;
	private boolean finished = false;
	private final IParticlePlaySituation[] rules;
	private final ParticleTaskData data;

	public PlayParticleTask(ParticleTaskData origin,
			LocationIterator locations, IParticlePlayer particle) {
		this.locations = locations;
		this.particle = particle;
		this.data = origin;
		rules = new IParticlePlaySituation[1];
		rules[0] = data.getSituation().normalize();
	}

	@Override
	public void run() {
		if (locations.hasNext()) {
			for (int c = 0; c < getCount(); ++c) {
				Location loc = locations.next();
				int x, y, z;
				x = loc.getBlockX();
				y = loc.getBlockY();
				z = loc.getBlockZ();
				boolean plays = true;
				for (IParticlePlaySituation rule : rules) {
					// TODO get player by UUID plays &= rule.playsEffect(loc, );
					if (!plays)
						break;
				}
				if (plays)
					particle.playParticle(loc.getWorld(), x, y, z,
							getEffectData());
			}
		} else
			remove();
	}

	void remove() {
		finished = true;
	}

	public LocationIterator getIter() {
		return locations;
	}

	public IParticlePlayer getParticle() {
		return particle;
	}

	public int getEffectData() {
		return data.getEffectData();
	}

	public boolean hasFinished() {
		return finished;
	}

	public UUID getOwner() {
		return data.getOwnerId();
	}

	public int getCount() {
		return data.getCount();
	}

	public IParticlePlaySituation[] getRules() {
		return rules;
	}

	public ParticleTaskData getData() {
		return data;
	}

	public int getTaskNumber() {
		return data.getNumber();
	}

	void setTaskNumber(int tn) {
		data.setNumber(tn);
	}
}
