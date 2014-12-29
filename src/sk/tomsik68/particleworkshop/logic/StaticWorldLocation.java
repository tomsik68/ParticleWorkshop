package sk.tomsik68.particleworkshop.logic;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.UUID;

import sk.tomsik68.particleworkshop.files.impl.ObjectStreamUtils;

public abstract class StaticWorldLocation extends ParticleLocation {
	protected UUID worldId;

	public StaticWorldLocation() {
	}

	public StaticWorldLocation(UUID world) {
		worldId = world;
	}

	@Override
	public void readExternal(ObjectInput arg0) throws IOException,
			ClassNotFoundException {
		worldId = ObjectStreamUtils.readUUID(arg0);
	}

	@Override
	public void writeExternal(ObjectOutput arg0) throws IOException {
		ObjectStreamUtils.writeUUID(worldId, arg0);
	}

	public UUID getWorld() {
		return worldId;
	}

	public void setWorld(UUID world) {
		this.worldId = world;
	}

}
