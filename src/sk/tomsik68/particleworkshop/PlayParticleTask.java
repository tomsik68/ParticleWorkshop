package sk.tomsik68.particleworkshop;

import java.util.UUID;

import org.bukkit.Location;

import sk.tomsik68.particleworkshop.api.IParticlePlaySituation;
import sk.tomsik68.particleworkshop.api.IParticlePlayer;
import sk.tomsik68.particleworkshop.api.LocationIterator;
import sk.tomsik68.particleworkshop.api.ParticlePlaySituations;

public class PlayParticleTask implements Runnable {
	private final LocationIterator locations;
	private final IParticlePlayer particle;
	private final int effectData;
	private boolean finished = false;
	private final IParticlePlaySituation[] rules;
	private final UUID owner;
	private final int count;
	private int taskNumber;
	private final ParticleTaskData data;

	public PlayParticleTask(ParticleTaskData origin,
			LocationIterator locations, IParticlePlayer particle, int data,
			UUID player, int number) {
		this(origin, locations, particle, data, player, number,
				ParticlePlaySituations.ALWAYS.normalize());
	}

	public PlayParticleTask(ParticleTaskData origin,
			LocationIterator locations, IParticlePlayer particle, int data,
			UUID player, int number, IParticlePlaySituation... rulz) {
		this(origin, locations, particle, data, player, 1, number, rulz);
	}

	public PlayParticleTask(ParticleTaskData origin,
			LocationIterator locations, IParticlePlayer particle, int data,
			UUID player, int count, int number, IParticlePlaySituation... rulz) {
		this.locations = locations;
		this.particle = particle;
		this.effectData = data;
		rules = rulz;
		owner = player;
		this.count = count;
		this.data = origin;
		this.taskNumber = number;
	}

	@Override
	public void run() {
		if (locations.hasNext()) {
			for (int c = 0; c < count; ++c) {
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
					particle.playParticle(loc.getWorld(), x, y, z, effectData);
			}
		} else
			remove();
	}

	public void remove() {
		finished = true;
	}

	public LocationIterator getIter() {
		return locations;
	}

	public IParticlePlayer getParticle() {
		return particle;
	}

	public int getEffectData() {
		return effectData;
	}

	public boolean hasFinished() {
		return finished;
	}

	public UUID getOwner() {
		return owner;
	}

	public int getCount() {
		return count;
	}

	public IParticlePlaySituation[] getRules() {
		return rules;
	}

	public ParticleTaskData getData() {
		return data;
	}

	public int getTaskNumber() {
		return taskNumber;
	}

	void setTaskNumber(int tn) {
		taskNumber = tn;
	}
}
