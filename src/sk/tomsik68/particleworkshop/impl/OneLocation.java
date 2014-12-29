package sk.tomsik68.particleworkshop.impl;

import org.bukkit.Location;

import sk.tomsik68.particleworkshop.api.LocationIterator;

public class OneLocation implements LocationIterator {
	private final Location loc;

	public OneLocation(Location location) {
		loc = location;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public Location next() {
		return loc;
	}

	@Override
	public void remove() {
	}

}
