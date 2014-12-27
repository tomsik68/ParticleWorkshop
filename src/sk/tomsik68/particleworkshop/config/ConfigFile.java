package sk.tomsik68.particleworkshop.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import sk.tomsik68.permsguru.EPermissions;

public class ConfigFile {
	private final File file;
	private YamlConfiguration config;

	public ConfigFile(File dataFolder) {
		file = new File(dataFolder, "config.yml");
	}

	public void load(Plugin plugin) throws Exception {
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
			config = YamlConfiguration.loadConfiguration(new InputStreamReader(
					plugin.getResource("defconfig.yml")));
			save();
		}
		config = YamlConfiguration.loadConfiguration(file);
	}

	public void save() throws IOException {
		config.save(file);
	}

	public EPermissions getPermissions() {
		return EPermissions.parse(config.getString("perms"));
	}

	public int getRadius() {
		return config.getInt("radius");
	}

	public boolean isQuotaLimited() {
		return config.getBoolean("quota-limit-enabled");
	}
}
