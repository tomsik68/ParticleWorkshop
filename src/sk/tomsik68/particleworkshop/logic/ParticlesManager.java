package sk.tomsik68.particleworkshop.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.Validate;
import org.bukkit.scheduler.BukkitScheduler;

import sk.tomsik68.particleworkshop.ParticleWorkshopPlugin;
import sk.tomsik68.particleworkshop.files.impl.ParticleTasksDataFile;

public class ParticlesManager {
	// each world has its thread
	private final HashMap<UUID, ParticleListsUUIDThread> tasksByWorld = new HashMap<>();
	private final ParticleListsUUIDThread dynamicPlayerTasks = new ParticleListsUUIDThread();
	private final UUIDToTaskListMap tasksByPlayer = new UUIDToTaskListMap();

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
			addParticle(taskData, false);
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

	public List<ParticleTaskData> getDataOf(UUID player) {
		List<ParticleTaskData> result = new ArrayList<>();
		List<PlayParticleTask> tasks = tasksByPlayer.getTasksFor(player);
		if (tasks != null)
			for (PlayParticleTask task : tasks) {
				result.add(task.getData());
			}
		return Collections.unmodifiableList(result);

	}

	public void scheduleTasks(BukkitScheduler scheduler) {
		this.scheduler = scheduler;
		for (ParticleListsUUIDThread thread : tasksByWorld.values()) {
			scheduleTask(thread);
		}
		scheduleTask(dynamicPlayerTasks);
	}

	private void scheduleTask(ParticleListsUUIDThread thread) {
		Validate.notNull(thread);
		Validate.notNull(scheduler);
		scheduler.runTaskTimer(ParticleWorkshopPlugin.getInstance(), thread,
				88L, 4L);
	}

	public int addParticle(ParticleTaskData data) {
		return addParticle(data, true);
	}

	private int addParticle(ParticleTaskData data, boolean schedule) {
		PlayParticleTask task = ParticleTaskFactory.createTask(data);
		tasksByPlayer.addTask(task.getOwner(), task);
		if (data.getLocation() instanceof StaticWorldLocation) {
			UUID worldId = ((StaticWorldLocation) data.getLocation())
					.getWorld();
			synchronized (tasksByWorld) {
				if (!tasksByWorld.containsKey(worldId)) {
					ParticleListsUUIDThread t = new ParticleListsUUIDThread();
					tasksByWorld.put(worldId, t);
					if (schedule)
						scheduleTask(t);
				}
				tasksByWorld.get(worldId).queueTask(task, data.getOwnerId());
			}
		} else {
			dynamicPlayerTasks.queueTask(task, data.getOwnerId());
		}
		return data.getNumber();
	}

	public boolean removeParticle(UUID player, int number) {
		return tasksByPlayer.removeTask(player, number);
	}
}
