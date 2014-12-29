package sk.tomsik68.particleworkshop.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.scheduler.BukkitScheduler;

import sk.tomsik68.particleworkshop.ParticleWorkshopPlugin;
import sk.tomsik68.particleworkshop.files.impl.ParticleTasksDataFile;

public class ParticlesManager {
	private final HashMap<UUID, ParticleListsUUIDThread> tasksByWorld = new HashMap<>();

	private ParticleListsUUIDThread dynamicPlayerTasks = new ParticleListsUUIDThread();

	private final PlayerParticleNumberData ppnd = new PlayerParticleNumberData();

	private ParticleTasksDataFile dataFile;

	private BukkitScheduler scheduler;

	public static ParticlesManager instance = new ParticlesManager();

	private ParticlesManager() {
	}

	int getParticleTaskNumberFor(UUID player) {
		return ppnd.getNextFor(player);
	}

	public void loadData(ParticleWorkshopPlugin particleWorkshopPlugin)
			throws Exception {
		dataFile = new ParticleTasksDataFile(new File(
				particleWorkshopPlugin.getDataFolder(), "particles.bin"));
		List<ParticleTaskData> tasksData = dataFile.loadData();
		for (ParticleTaskData taskData : tasksData) {
			ppnd.updateParticleData(taskData.getOwnerId(), taskData.getNumber());
			addParticle(taskData);
		}
	}

	public void saveData(ParticleWorkshopPlugin particleWorkshopPlugin)
			throws Exception {
		ArrayList<ParticleTaskData> tasksData = new ArrayList<ParticleTaskData>();

		for (ParticleListsUUIDThread particleLists : tasksByWorld.values()) {
			Collection<List<PlayParticleTask>> listCollection = particleLists
					.getLists();
			for (List<PlayParticleTask> taskList : listCollection) {
				for (PlayParticleTask task : taskList) {
					tasksData.add(task.getData());
				}
			}
		}

		Collection<List<PlayParticleTask>> listCollection = dynamicPlayerTasks
				.getLists();
		for (List<PlayParticleTask> taskList : listCollection) {
			for (PlayParticleTask task : taskList) {
				tasksData.add(task.getData());
			}
		}

		dataFile.saveData(tasksData);
	}

	public void scheduleTasks(BukkitScheduler scheduler) {
		this.scheduler = scheduler;
		for (ParticleListsUUIDThread thread : tasksByWorld.values()) {
			scheduleTask(thread);
		}
		scheduleTask(dynamicPlayerTasks);
	}

	private void scheduleTask(ParticleListsUUIDThread thread) {
		scheduler.runTaskTimer(ParticleWorkshopPlugin.getInstance(), thread,
				88L, 4L);
	}

	public int addParticle(ParticleTaskData data) {
		PlayParticleTask task = ParticleTaskFactory.createTask(data);
		if (data.getLocation() instanceof StaticWorldLocation) {
			UUID worldId = ((StaticWorldLocation) data.getLocation())
					.getWorld();
			synchronized (tasksByWorld) {
				if (!tasksByWorld.containsKey(worldId)) {
					ParticleListsUUIDThread t = new ParticleListsUUIDThread();
					tasksByWorld.put(worldId, t);
					scheduleTask(t);
				}
				tasksByWorld.get(worldId).queueTask(task, data.getOwnerId());
			}
		} else {
			dynamicPlayerTasks.queueTask(task, data.getOwnerId());
		}
		return data.getNumber();
	}
}
