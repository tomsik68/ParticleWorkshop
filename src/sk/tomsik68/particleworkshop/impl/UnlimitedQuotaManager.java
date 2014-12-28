package sk.tomsik68.particleworkshop.impl;

import org.bukkit.entity.Player;

import sk.tomsik68.particleworkshop.ParticleWorkshopPlugin;
import sk.tomsik68.particleworkshop.api.ICostCalculator;
import sk.tomsik68.particleworkshop.api.QuotaManager;
import sk.tomsik68.particleworkshop.logic.PlayParticleTask;

public class UnlimitedQuotaManager extends QuotaManager {

    public UnlimitedQuotaManager() {
        super(new ICostCalculator() {
            @Override
            public float getCost(PlayParticleTask task) {
                return 0;
            }
        });
    }

    @Override
    public boolean canAfford(Player player, PlayParticleTask task) {
        return true;
    }

    @Override
    public float getFreeQuota(Player player) {
        return 1;
    }

    @Override
    public void setFreeQuota(Player player, float quota) {
    }

    @Override
    public void addQuota(Player player, float add) {
    }

    @Override
    public void removeQuota(Player player, float remove) {
    }

    @Override
    public void loadData(ParticleWorkshopPlugin plugin) {
    }

    @Override
    public void saveData(ParticleWorkshopPlugin plugin) {
    }

}
