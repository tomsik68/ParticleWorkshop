package sk.tomsik68.particleworkshop.logic;

import java.util.HashMap;
import java.util.UUID;

public class PlayerParticleNumberData {
	private HashMap<UUID, Integer> playerData = new HashMap<>();

	public PlayerParticleNumberData() {
	}

	private int getNumberFor(UUID player) {
		Integer result = playerData.get(player);
		if (result == null) {
			return 0;
		} else {
			return result;
		}
	}

	private void setNumberFor(UUID player, Integer number) {
		playerData.put(player, number);
	}

	// this is used while loading data. All tasks will be looped through and
	// this method will be called with each task
	// this method will make sure player has the latest particle number
	void updateParticleData(UUID player, int number) {
		if (getNumberFor(player) < number + 1) {
			setNumberFor(player, number + 1);
		}
	}

	public int getNextFor(UUID player) {
		int result = getNumberFor(player);
		setNumberFor(player, Integer.valueOf(result + 1));
		return result;
	}
}
