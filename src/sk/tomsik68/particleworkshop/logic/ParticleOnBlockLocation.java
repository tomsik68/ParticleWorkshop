package sk.tomsik68.particleworkshop.logic;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import sk.tomsik68.particleworkshop.api.LocationIterator;
import sk.tomsik68.particleworkshop.impl.OneLocation;

public class ParticleOnBlockLocation extends StaticWorldLocation {
	private int x, y, z;

	public ParticleOnBlockLocation() {

	}

	public ParticleOnBlockLocation(Location location) {
		super(location.getWorld().getUID());
		x = location.getBlockX();
		y = location.getBlockY();
		z = location.getBlockZ();
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		super.readExternal(in);
		x = in.readInt();
		y = in.readInt();
		z = in.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);
	}

	@Override
	LocationIterator createIterator() {
		return new OneLocation(new Location(Bukkit.getWorld(worldId), x, y, z));
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

}
