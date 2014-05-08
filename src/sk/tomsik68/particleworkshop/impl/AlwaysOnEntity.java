package sk.tomsik68.particleworkshop.impl;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import sk.tomsik68.particleworkshop.api.LocationIterator;

public class AlwaysOnEntity implements LocationIterator {
    private final Entity entity;
    private boolean iterated = false, repeat = false;
    private final Vector relative;
    private static final Vector zero = new Vector(0, 0, 0);

    public AlwaysOnEntity(Entity p, boolean repeat) {
        this(p, zero, repeat);
    }

    public AlwaysOnEntity(Entity p, Vector relative, boolean repeat) {
        entity = p;
        this.relative = relative;
        this.repeat = repeat;
    }

    @Override
    public boolean hasNext() {
        return entity.isValid() && (repeat || (!repeat && !iterated));
    }

    @Override
    public Location next() {
        if (!iterated)
            iterated = true;
        return entity.getLocation().add(relative);
    }

    @Override
    public void remove() {
    }

    @Override
    public String getName() {
        return "AlwaysOnPlayer";
    }

}
