package sk.tomsik68.particleworkshop.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

class UUIDToTaskListMap {
	private final HashMap<UUID, List<PlayParticleTask>> mappings = new HashMap<>();

	void addTask(UUID uuid, PlayParticleTask task) {
		List<PlayParticleTask> listForThisID = mappings.get(uuid);
		if (listForThisID == null)
			listForThisID = new ArrayList<PlayParticleTask>();
		listForThisID.add(task);
		mappings.put(uuid, listForThisID);
	}

	boolean removeTask(UUID uuid, int taskNumber) {
		List<PlayParticleTask> listForThisID = mappings.get(uuid);
		boolean removed = false;
		if (listForThisID != null) {
			int i = 0;
			while (i < listForThisID.size() && !removed) {
				PlayParticleTask task = listForThisID.get(i);
				if (task.getTaskNumber() == taskNumber) {
					task.remove();
					removed = true;
					listForThisID.remove(i);
				}
				++i;
			}
			mappings.put(uuid, listForThisID);
		}
		return removed;
	}

	Collection<List<PlayParticleTask>> getTaskLists() {
		return Collections.unmodifiableCollection(mappings.values());
	}

	List<PlayParticleTask> getTasksFor(UUID player) {
		if (mappings.containsKey(player))
			return Collections.unmodifiableList(mappings.get(player));
		else
			return null;
	}
}
