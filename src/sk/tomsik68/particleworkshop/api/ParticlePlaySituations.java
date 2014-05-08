package sk.tomsik68.particleworkshop.api;

import sk.tomsik68.particleworkshop.impl.Always;
import sk.tomsik68.particleworkshop.impl.InWater;
import sk.tomsik68.particleworkshop.impl.OnGround;
import sk.tomsik68.particleworkshop.impl.Sneak;

public enum ParticlePlaySituations {
    ALWAYS(new Always()), SNEAK(new Sneak(true)), NO_SNEAK(new Sneak(false)), ON_GROUND(new OnGround(false)), ABOVE_GROUND(new OnGround(true)), IN_WATER(
            new InWater(true)), OUT_WATER(new InWater(false));

    private final IParticlePlaySituation check;

    private ParticlePlaySituations(IParticlePlaySituation adapter) {
        check = adapter;
    }

    public IParticlePlaySituation normalize() {
        return check;
    }
}
