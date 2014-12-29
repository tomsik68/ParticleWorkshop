package sk.tomsik68.particleworkshop.logic;

import java.util.UUID;

import sk.tomsik68.particleworkshop.api.ParticlePlaySituations;

public class ParticleTaskData {
	private String particleName;
	private int effectData;
	private ParticlePlaySituations situation;
	private UUID ownerId;
	private int count, number = -1;
	private ParticleLocation location;

	public ParticleTaskData() {
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	ParticleTaskData deepCopy() {
		ParticleTaskData clone = new ParticleTaskData();
		clone.setOwnerId(ownerId);
		clone.setCount(count);
		clone.setEffectData(effectData);
		clone.setLocation(location);
		clone.setParticleName(particleName);
		clone.setSituation(situation);
		return clone;
	}

	public void setOwnerId(UUID ownerId2) {
		ownerId = ownerId2;
	}

	public ParticleLocation getLocation() {
		return location;
	}

	public void setLocation(ParticleLocation location) {
		this.location = location;
	}

}
