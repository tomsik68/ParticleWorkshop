package sk.tomsik68.particleworkshop;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class ParticlesManager implements Runnable {
	private final HashSet<PlayParticleTask> tasks = new HashSet<PlayParticleTask>();
	private final HashSet<PlayParticleTask> tasksToAdd = new HashSet<PlayParticleTask>();
	private final HashSet<PlayParticleTask> tasksToRemove = new HashSet<PlayParticleTask>();

	private final PlayerParticleNumberData ppnd = new PlayerParticleNumberData();

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

	@Override
	public void run() {
		synchronized (tasks) {
			if (!tasksToAdd.isEmpty()) {
				tasks.addAll(tasksToAdd);
				tasksToAdd.clear();
			}
			for (PlayParticleTask task : tasks) {
				task.run();
				if (task.hasFinished()) {
					tasksToRemove.add(task);
				}
			}
			if (!tasksToRemove.isEmpty()) {
				tasks.removeAll(tasksToRemove);
				tasksToRemove.clear();
			}
		}

	}

	public List<ParticleTaskData> getParticles() {
		ArrayList<ParticleTaskData> result = new ArrayList<ParticleTaskData>();
		synchronized (tasks) {
			for (PlayParticleTask task : tasks) {
				result.add(task.getData());
			}
		}
		return result;
	}

	public void createTasks(List<ParticleTaskData> particleTasksData) {
		for (ParticleTaskData taskData : particleTasksData) {
			addTask(ParticleTaskFactory.createTask(taskData));
		}
	}

	public int getParticleTaskNumberFor(UUID player) {
		return ppnd.getNextFor(player);
	}

}
