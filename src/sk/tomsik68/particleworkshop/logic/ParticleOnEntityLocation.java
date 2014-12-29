package sk.tomsik68.particleworkshop.logic;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import sk.tomsik68.particleworkshop.api.LocationIterator;
import sk.tomsik68.particleworkshop.files.impl.ObjectStreamUtils;
import sk.tomsik68.particleworkshop.impl.AlwaysOnEntity;

public class ParticleOnEntityLocation extends ParticleLocation {

	private UUID entityId;
	private Vector relVector;

	public ParticleOnEntityLocation() {
	}

	public ParticleOnEntityLocation(Entity entity, Vector vect) {
		relVector = vect;
		entityId = entity.getUniqueId();
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		entityId = ObjectStreamUtils.readUUID(in);
		relVector = new Vector(in.readInt(), in.readInt(), in.readInt());
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		ObjectStreamUtils.writeUUID(entityId, out);
		out.writeInt(relVector.getBlockX());
		out.writeInt(relVector.getBlockY());
		out.writeInt(relVector.getBlockZ());
	}

	@Override
	LocationIterator createIterator() {
		List<World> worlds = Bukkit.getWorlds();
		Entity theEntity = null;
		for (World world : worlds) {
			List<Entity> entities = world.getEntities();
			for (Entity entity : entities) {
				if (entity.getUniqueId().equals(entityId)) {
					theEntity = entity;
					break;
				}
			}
			if (theEntity != null)
				break;
		}

		return new AlwaysOnEntity(theEntity, relVector);
	}

}
