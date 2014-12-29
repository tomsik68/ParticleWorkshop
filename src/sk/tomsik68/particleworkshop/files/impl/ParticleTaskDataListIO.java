package sk.tomsik68.particleworkshop.files.impl;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sk.tomsik68.particleworkshop.api.ParticlePlaySituations;
import sk.tomsik68.particleworkshop.files.api.IDataIO;
import sk.tomsik68.particleworkshop.logic.ParticleLocation;
import sk.tomsik68.particleworkshop.logic.ParticleTaskData;

public class ParticleTaskDataListIO implements IDataIO<List<ParticleTaskData>> {

	@Override
	public List<ParticleTaskData> load(InputStream in) throws Exception {
		ObjectInputStream ois = new ObjectInputStream(in);
		ArrayList<ParticleTaskData> result = new ArrayList<ParticleTaskData>();

		int count = ois.readInt();
		for (int i = 0; i < count; ++i) {
			UUID owner = ObjectStreamUtils.readUUID(ois);
			ParticleTaskData element = new ParticleTaskData();
			element.setOwnerId(owner);
			element.setParticleName(ois.readUTF());
			element.setSituation(ParticlePlaySituations.values()[ois.readInt()]);
			element.setCount(ois.readInt());
			element.setNumber(ois.readInt());
			element.setEffectData(ois.readInt());
			element.setLocation((ParticleLocation) ois.readObject());
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
			ObjectStreamUtils.writeUUID(e.getOwnerId(), oos);
			oos.writeUTF(e.getParticleName());
			oos.writeInt(e.getSituation().ordinal());
			oos.writeInt(e.getCount());
			oos.writeInt(e.getNumber());
			oos.writeInt(e.getEffectData());
			oos.writeObject(e.getLocation());
		}
		oos.flush();
	}
}
