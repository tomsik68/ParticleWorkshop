package sk.tomsik68.particleworkshop.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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

	private final HashMap<UUID, List<PlayParticleTask>> tasksPerID = new HashMap<>();
	private final HashSet<AddTaskData> tasksToAdd = new HashSet<AddTaskData>();
	private final HashSet<RemoveTaskData> tasksToRemove = new HashSet<RemoveTaskData>();

	void queueTask(PlayParticleTask task, UUID id) {
		synchronized (tasksToAdd) {
			tasksToAdd.add(new AddTaskData(id, task));
		}
	}

	void removeTask(UUID id, int taskNumber) {
		synchronized (tasksToRemove) {
			tasksToRemove.add(new RemoveTaskData(id, taskNumber));
		}
	}

	@Override
	public void run() {
		Collection<List<PlayParticleTask>> lists = tasksPerID.values();
		for (List<PlayParticleTask> list : lists) {
			for (PlayParticleTask task : list) {
				task.run();
			}
		}
		synchronized (tasksToAdd) {
			for (AddTaskData atd : tasksToAdd) {
				List<PlayParticleTask> taskListForID = tasksPerID.get(atd.uuid);
				if (taskListForID == null)
					taskListForID = new ArrayList<>();
				taskListForID.add(atd.task);
				tasksPerID.put(atd.uuid, taskListForID);
			}
			tasksToAdd.clear();
		}
		synchronized (tasksToRemove) {
			for (RemoveTaskData data : tasksToRemove) {
				List<PlayParticleTask> taskListForID = tasksPerID
						.get(data.uuid);
				if (taskListForID != null) {
					for (int i = 0; i < taskListForID.size(); ++i) {
						PlayParticleTask task = taskListForID.get(i);
						if (task.getTaskNumber() == data.taskNumber) {
							taskListForID.remove(i);
							break;
						}
					}
				}
			}
			tasksToRemove.clear();
		}
	}

	public Collection<List<PlayParticleTask>> getLists() {
		return tasksPerID.values();
	}

}
