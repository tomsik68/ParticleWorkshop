package sk.tomsik68.particleworkshop.files.impl;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.UUID;

public class ObjectStreamUtils {
	public static final void writeUUID(UUID id, ObjectOutput oos)
			throws IOException {
		oos.writeLong(id.getLeastSignificantBits());
		oos.writeLong(id.getMostSignificantBits());
	}

	public static final UUID readUUID(ObjectInput ois) throws IOException {
		long lsb = ois.readLong();
		long msb = ois.readLong();
		return new UUID(msb, lsb);
	}
}
