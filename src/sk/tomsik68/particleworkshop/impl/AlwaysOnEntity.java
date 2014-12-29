package sk.tomsik68.particleworkshop.impl;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import sk.tomsik68.particleworkshop.api.LocationIterator;

public class AlwaysOnEntity implements LocationIterator {
	private final Entity entity;
	private final Vector relative;
	private static final Vector zero = new Vector(0, 0, 0);

	public AlwaysOnEntity(Entity p) {
		this(p, zero);
	}

	public AlwaysOnEntity(Entity p, Vector relative) {
		entity = p;
		this.relative = relative;
	}

	@Override
	public boolean hasNext() {
		return entity.isValid();
	}

	@Override
	public Location next() {
		return entity.getLocation().add(relative);
	}

	@Override
	public void remove() {
	}

}
