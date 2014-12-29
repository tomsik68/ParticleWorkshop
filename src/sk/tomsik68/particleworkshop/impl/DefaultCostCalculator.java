package sk.tomsik68.particleworkshop.impl;

import sk.tomsik68.particleworkshop.api.ICostCalculator;
import sk.tomsik68.particleworkshop.logic.ParticleTaskData;

public class DefaultCostCalculator implements ICostCalculator {
	@Override
	public float getCost(ParticleTaskData task) {
		float rulesValue = 1;
		float iterValue = 0;
		// TODO: need to know whether OneLocation or AlwaysOnEntity
		iterValue = 0.75f;
		return task.getCount() * (iterValue + rulesValue);
	}

}
