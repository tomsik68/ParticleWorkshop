package sk.tomsik68.particleworkshop.logic;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

class ParticleListsUUIDThread implements Runnable {
	private class RemoveTaskData {
		public UUID uuid;
		public int taskNumber;

		public RemoveTaskData(UUID id, int number) {
			uuid = id;
			taskNumber = number;
		}
	}

	private class AddTaskData {
		public UUID uuid;
		public PlayParticleTask task;

		public AddTaskData(UUID id, PlayParticleTask task) {
			this.uuid = id;
			this.task = task;
		}
	}

	// private final HashMap<UUID, List<PlayParticleTask>> tasksPerID = new
	// HashMap<>();
	private final UUIDToTaskListMap tasks = new UUIDToTaskListMap();
	private final HashSet<AddTaskData> tasksToAdd = new HashSet<>();
	private final HashSet<RemoveTaskData> tasksToRemove = new HashSet<>();

	void queueTask(PlayParticleTask task, UUID id) {
		synchronized (tasksToAdd) {
			tasksToAdd.add(new AddTaskData(id, task));
		}
	}

	private void removeTask(UUID id, int taskNumber) {
		synchronized (tasksToRemove) {
			tasksToRemove.add(new RemoveTaskData(id, taskNumber));
		}
	}

	@Override
	public void run() {
		Collection<List<PlayParticleTask>> lists = tasks.getTaskLists();
		for (List<PlayParticleTask> list : lists) {
			for (PlayParticleTask task : list) {
				task.run();
				if (task.hasFinished())
					removeTask(task.getOwner(), task.getTaskNumber());
			}
		}
		synchronized (tasksToAdd) {
			for (AddTaskData atd : tasksToAdd) {
				tasks.addTask(atd.uuid, atd.task);
			}
			tasksToAdd.clear();
		}
		synchronized (tasksToRemove) {
			for (RemoveTaskData data : tasksToRemove) {
				tasks.removeTask(data.uuid, data.taskNumber);
			}
			tasksToRemove.clear();
		}
	}

	public Collection<List<PlayParticleTask>> getLists() {
		return tasks.getTaskLists();
	}

}
