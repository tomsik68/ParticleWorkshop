package sk.tomsik68.particleworkshop;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import sk.tomsik68.particleworkshop.api.QuotaManager;
import sk.tomsik68.particleworkshop.commands.PWSCommand;
import sk.tomsik68.particleworkshop.config.ConfigFile;
import sk.tomsik68.particleworkshop.impl.DatabaseQuotaManager;
import sk.tomsik68.particleworkshop.impl.DefaultCostCalculator;
import sk.tomsik68.particleworkshop.impl.QuotaData;
import sk.tomsik68.particleworkshop.impl.UnlimitedQuotaManager;
import sk.tomsik68.particleworkshop.listeners.PWSWandUsageListener;
import sk.tomsik68.particleworkshop.players.ParticlePlayerRegistry;

public class ParticleWorkshopPlugin extends JavaPlugin {
    public static Logger log;
    private ConfigFile config;
    public static QuotaManager quotaManager;

    @Override
    public void onEnable() {

        log = getLogger();
        log.info("Loading Configuration...");
        config = new ConfigFile(getDataFolder());
        try {
            config.load(this);
            log.info("Configuration loaded.");
        } catch (Exception e) {
            log.severe("Could not load configuration :(");
            e.printStackTrace();
        }
        if (config.isQuotaLimited()) {
            quotaManager = new DatabaseQuotaManager(new DefaultCostCalculator());
        } else
            quotaManager = new UnlimitedQuotaManager();
        ParticlePlayerRegistry.instance.registerDefaultEffects(config.getRadius());
        log.info("Registering commands...");
        getCommand("pws").setExecutor(new PWSCommand(config.getPermissions()));
        log.info("Enabling task...");
        getServer().getScheduler().runTaskTimer(this, ParticlesManager.instance, 5, 3);
        getServer().getPluginManager().registerEvents(new PWSWandUsageListener(), this);
        log.info("ParticleWorkshop is now enabled. Have fun :)");
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static ParticleWorkshopPlugin getInstance() {
        return (ParticleWorkshopPlugin) Bukkit.getPluginManager().getPlugin("ParticleWorkshop");
    }

    @Override
    public void installDDL() {
        super.installDDL();
    }

    @Override
    public List<Class<?>> getDatabaseClasses() {
        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        classes.add(QuotaData.class);
        return classes;
    }

}
