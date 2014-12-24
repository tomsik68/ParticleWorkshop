package sk.tomsik68.particleworkshop;

import org.bukkit.Material;
import org.bukkit.util.Vector;

public class PlayerWandData {
	private final ParticleTaskData taskData;
	private final Material item;
	public static final Vector ZERO_VECTOR = new Vector(0, 0, 0);
	private Vector relativeVector = ZERO_VECTOR;

	public PlayerWandData(ParticleTaskData data, Material item) {
		this.taskData = data;
		this.item = item;
	}

	public ParticleTaskData getTaskData() {
		return taskData;
	}

	public Material getItem() {
		return item;
	}

	public void setRelativeVector(Vector relativeVector) {
		this.relativeVector = relativeVector;
	}

	public Vector getRelativeVector() {
		return relativeVector;
	}
}
