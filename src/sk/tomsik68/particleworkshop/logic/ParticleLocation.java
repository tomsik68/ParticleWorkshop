package sk.tomsik68.particleworkshop.logic;

import java.io.Externalizable;

import sk.tomsik68.particleworkshop.api.LocationIterator;

public abstract class ParticleLocation implements Externalizable {
	abstract LocationIterator createIterator();
}
