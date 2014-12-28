package sk.tomsik68.particleworkshop.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.bukkit.scheduler.BukkitScheduler;

import sk.tomsik68.particleworkshop.ParticleWorkshopPlugin;
import sk.tomsik68.particleworkshop.files.impl.ParticleTasksDataFile;

public class ParticlesManager {
	private final HashMap<UUID, List<PlayParticleTask>> tasksByWorld = new HashMap<UUID, List<PlayParticleTask>>();

	private final HashSet<PlayParticleTask> tasksToAdd = new HashSet<PlayParticleTask>();
	private final HashSet<PlayParticleTask> tasksToRemove = new HashSet<PlayParticleTask>();

	private final PlayerParticleNumberData ppnd = new PlayerParticleNumberData();

	private ParticleTasksDataFile dataFile;

	public static ParticlesManager instance = new ParticlesManager();

	private ParticlesManager() {
	}

	public void addTask(PlayParticleTask task) {
		synchronized (tasksToAdd) {
			tasksToAdd.add(task);
		}
	}

	public void removeTask(PlayParticleTask task) {
		synchronized (tasksToRemove) {
			tasksToRemove.add(task);
		}
	}

	/*
	 * @Override public void run() { synchronized (tasks) { if
	 * (!tasksToAdd.isEmpty()) { tasks.addAll(tasksToAdd); tasksToAdd.clear(); }
	 * for (PlayParticleTask task : tasks) { task.run(); if (task.hasFinished())
	 * { tasksToRemove.add(task); } } if (!tasksToRemove.isEmpty()) {
	 * tasks.removeAll(tasksToRemove); tasksToRemove.clear(); } } }
	 */

	public int getParticleTaskNumberFor(UUID player) {
		return ppnd.getNextFor(player);
	}

	public void loadData(ParticleWorkshopPlugin particleWorkshopPlugin)
			throws Exception {
		dataFile = new ParticleTasksDataFile(new File(
				particleWorkshopPlugin.getDataFolder(), "particles.bin"));
		List<ParticleTaskData> tasksData = dataFile.loadData();
		for (ParticleTaskData taskData : tasksData) {
			ppnd.updateParticleData(taskData.getOwnerId(), taskData.getNumber());
			addTask(ParticleTaskFactory.createTask(taskData));
		}
	}

	public void saveData(ParticleWorkshopPlugin particleWorkshopPlugin)
			throws Exception {
		ArrayList<ParticleTaskData> tasksData = new ArrayList<ParticleTaskData>();
		synchronized (tasks) {
			for (PlayParticleTask task : tasks) {
				tasksData.add(task.getData());
			}
		}
		dataFile.saveData(tasksData);
	}

	public void scheduleTasks(BukkitScheduler scheduler) {

	}

}
