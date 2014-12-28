package sk.tomsik68.particleworkshop.impl;

import sk.tomsik68.particleworkshop.api.ICostCalculator;
import sk.tomsik68.particleworkshop.logic.PlayParticleTask;

public class DefaultCostCalculator implements ICostCalculator {
    @Override
    public float getCost(PlayParticleTask task) {
        float rulesValue = task.getRules().length;
        float iterValue = 0;
        if (task.getIter() instanceof AlwaysOnEntity) {
            iterValue = 0.75f;
        } else if (task.getIter() instanceof OneLocation) {
            iterValue = 0.35f;
        }

        return task.getCount() * (task.getRules().length + iterValue + rulesValue);
    }

}
