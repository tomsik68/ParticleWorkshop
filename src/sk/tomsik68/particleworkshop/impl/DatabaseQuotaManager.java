package sk.tomsik68.particleworkshop.impl;

import org.bukkit.entity.Player;

import sk.tomsik68.particleworkshop.ParticleWorkshopPlugin;
import sk.tomsik68.particleworkshop.api.ICostCalculator;
import sk.tomsik68.particleworkshop.api.QuotaManager;
import sk.tomsik68.particleworkshop.tasks.PlayParticleTask;

import com.avaje.ebean.EbeanServer;

public class DatabaseQuotaManager extends QuotaManager {

    private EbeanServer db;

    public DatabaseQuotaManager(ICostCalculator cc) {
        super(cc);
    }

    @Override
    public boolean canAfford(Player player, PlayParticleTask task) {
        float freeQ = getFreeQuota(player);
        float cost = getCost(task);
        return (freeQ >= cost);
    }

    @Override
    public float getFreeQuota(Player player) {
        QuotaData playerData = findPlayerData(player);
        return playerData.getFreeQuota();
    }

    private QuotaData findPlayerData(Player player) {
        return db.find(QuotaData.class).where().ieq("playerID", player.getUniqueId().toString()).findUnique();
    }

    @Override
    public void setFreeQuota(Player player, float quota) {
        QuotaData playerData = findPlayerData(player);
        playerData.setFreeQuota(quota);
        db.update(playerData);
    }

    @Override
    public void loadData(ParticleWorkshopPlugin plugin) {
        db = plugin.getDatabase();
        try {
            db.find(QuotaData.class).findRowCount();
        } catch (Exception e) {
            ParticleWorkshopPlugin.log.info("Installing DB due to first time usage...");
            plugin.installDDL();
            db.find(QuotaData.class).findRowCount();
        }
    }

    @Override
    public void saveData(ParticleWorkshopPlugin plugin) {
    }

    @Override
    public void addQuota(Player player, float add) {
        setFreeQuota(player, getFreeQuota(player) + add);
    }

    @Override
    public void removeQuota(Player player, float remove) {
        // fix values less than 0
        setFreeQuota(player, Math.max(getFreeQuota(player) - remove, 0));
    }

}
