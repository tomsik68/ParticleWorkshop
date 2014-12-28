package sk.tomsik68.particleworkshop;

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

	public int getNextFor(UUID player) {
		int result = getNumberFor(player);
		setNumberFor(player, Integer.valueOf(result + 1));
		return result;
	}
}
