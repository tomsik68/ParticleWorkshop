package sk.tomsik68.particleworkshop.files.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import sk.tomsik68.particleworkshop.ParticleTaskData;
import sk.tomsik68.particleworkshop.api.ParticlePlaySituations;
import sk.tomsik68.particleworkshop.files.api.IDataIO;

public class ParticleTaskDataListIO implements IDataIO<List<ParticleTaskData>> {

	@Override
	public List<ParticleTaskData> load(InputStream in) throws Exception {
		ObjectInputStream ois = new ObjectInputStream(in);
		ArrayList<ParticleTaskData> result = new ArrayList<ParticleTaskData>();

		int count = ois.readInt();
		for (int i = 0; i < count; ++i) {
			UUID owner = readUUID(ois);
			ParticleTaskData element = new ParticleTaskData(owner);
			element.setParticleName(ois.readUTF());
			element.setSituation(ParticlePlaySituations.values()[ois.readInt()]);
			element.setCount(ois.readInt());
			element.setNumber(ois.readInt());
			element.setEffectData(ois.readInt());
			element.setFollow(ois.readBoolean());
			if (!element.isFollow()) {
				UUID worldId = readUUID(ois);
				element.setOneLocation(new Location(Bukkit.getWorld(worldId),
						ois.readDouble(), ois.readDouble(), ois.readDouble()));
			}
			element.setRepeat(ois.readBoolean());
			element.setRelativeVector(new Vector(ois.readDouble(), ois
					.readDouble(), ois.readDouble()));
			result.add(element);
		}

		return result;
	}

	@Override
	public void save(OutputStream out, List<ParticleTaskData> data)
			throws Exception {
		ObjectOutputStream oos = new ObjectOutputStream(out);

		oos.writeInt(data.size());
		for (ParticleTaskData e : data) {
			writeUUID(e.getOwnerId(), oos);
			oos.writeUTF(e.getParticleName());
			oos.writeInt(e.getSituation().ordinal());
			oos.writeInt(e.getCount());
			oos.writeInt(e.getNumber());
			oos.writeInt(e.getEffectData());
			oos.writeBoolean(e.isFollow());
			if (!e.isFollow()) {
				writeUUID(e.getOneLocation().getWorld().getUID(), oos);
				oos.writeDouble(e.getOneLocation().getX());
				oos.writeDouble(e.getOneLocation().getY());
				oos.writeDouble(e.getOneLocation().getZ());
			}
			oos.writeBoolean(e.isRepeat());
			oos.writeDouble(e.getRelativeVector().getX());
			oos.writeDouble(e.getRelativeVector().getY());
			oos.writeDouble(e.getRelativeVector().getZ());
		}
		oos.flush();
	}

	private final void writeUUID(UUID id, ObjectOutputStream oos)
			throws IOException {
		oos.writeLong(id.getLeastSignificantBits());
		oos.writeLong(id.getMostSignificantBits());
	}

	private final UUID readUUID(ObjectInputStream ois) throws IOException {
		long lsb = ois.readLong();
		long msb = ois.readLong();
		return new UUID(msb, lsb);
	}

}
