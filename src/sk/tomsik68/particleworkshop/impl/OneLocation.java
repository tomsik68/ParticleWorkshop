package sk.tomsik68.particleworkshop.impl;

import org.bukkit.Location;

import sk.tomsik68.particleworkshop.api.LocationIterator;

public class OneLocation implements LocationIterator {
    private final Location loc;
    private boolean iterated = false, repeat;

    public OneLocation(Location location, boolean repeat) {
        loc = location;
        this.repeat = repeat;
    }

    @Override
    public boolean hasNext() {
        return repeat || (!repeat && !iterated);
    }

    @Override
    public Location next() {
        iterated = true;
        return loc;
    }

    @Override
    public void remove() {
    }

    @Override
    public String getName() {
        return "OneLocation";
    }

}
