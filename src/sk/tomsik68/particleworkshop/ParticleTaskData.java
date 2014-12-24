package sk.tomsik68.particleworkshop;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import sk.tomsik68.particleworkshop.api.ParticlePlaySituations;

public class ParticleTaskData {
	private String particleName;
	private boolean follow, repeat;
	private int effectData;
	private ParticlePlaySituations situation;
	private Vector relativeVector;
	private final UUID ownerId;
	private int count;
	private Location oneLocation;

	public ParticleTaskData(UUID owner) {
		this.ownerId = owner;
	}

	public boolean isFollow() {
		return follow;
	}

	public void setFollow(boolean follow) {
		this.follow = follow;
	}

	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	public int getEffectData() {
		return effectData;
	}

	public void setEffectData(int data) {
		this.effectData = data;
	}

	public ParticlePlaySituations getSituation() {
		return situation;
	}

	public void setSituation(ParticlePlaySituations situation) {
		this.situation = situation;
	}

	public Vector getRelativeVector() {
		return relativeVector;
	}

	public void setRelativeVector(Vector relativeVector) {
		this.relativeVector = relativeVector;
	}

	public String getParticleName() {
		return particleName;
	}

	public void setParticleName(String particleName) {
		this.particleName = particleName;
	}

	public UUID getOwnerId() {
		return ownerId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Location getOneLocation() {
		return oneLocation;
	}

	public void setOneLocation(Location oneLocation) {
		this.oneLocation = oneLocation;
	}

}
