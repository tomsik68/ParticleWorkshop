package sk.tomsik68.particleworkshop.api;

import org.bukkit.entity.Player;

import sk.tomsik68.particleworkshop.ParticleWorkshopPlugin;
import sk.tomsik68.particleworkshop.logic.PlayParticleTask;

public abstract class QuotaManager {
    private final ICostCalculator calc;

    public QuotaManager(ICostCalculator cc) {
        calc = cc;
    }

    public final float getCost(PlayParticleTask task) {
        return calc.getCost(task);
    }

    public boolean canAfford(Player player, PlayParticleTask task) {
        float freeQ = getFreeQuota(player);
        float cost = getCost(task);
        return (freeQ >= cost);
    }

    public abstract float getFreeQuota(Player player);

    public abstract void setFreeQuota(Player player, float quota);

    public abstract void addQuota(Player player, float add);

    public abstract void removeQuota(Player player, float remove);

    public abstract void loadData(ParticleWorkshopPlugin plugin);

    public abstract void saveData(ParticleWorkshopPlugin plugin);
}
