package sk.tomsik68.particleworkshop.api;

import sk.tomsik68.particleworkshop.logic.PlayParticleTask;

public interface ICostCalculator {
    public float getCost(PlayParticleTask task);
}
