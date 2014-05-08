package sk.tomsik68.particleworkshop.api;

import sk.tomsik68.particleworkshop.tasks.PlayParticleTask;

public interface ICostCalculator {
    public float getCost(PlayParticleTask task);
}
