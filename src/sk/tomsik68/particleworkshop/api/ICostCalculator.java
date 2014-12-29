package sk.tomsik68.particleworkshop.api;

import sk.tomsik68.particleworkshop.logic.ParticleTaskData;

public interface ICostCalculator {
    public float getCost(ParticleTaskData data);
}
