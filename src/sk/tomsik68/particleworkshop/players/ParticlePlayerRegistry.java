package sk.tomsik68.particleworkshop.players;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Effect;

import sk.tomsik68.particleworkshop.api.IParticlePlayer;

public class ParticlePlayerRegistry {
    private final Map<String, IParticlePlayer> particlePlayers = new HashMap<String, IParticlePlayer>();
    public static ParticlePlayerRegistry instance = new ParticlePlayerRegistry();

    private ParticlePlayerRegistry() {

    }

    public void registerParticlePlayer(String effectUpperCase, IParticlePlayer effectPlayer) {
        particlePlayers.put(effectUpperCase, effectPlayer);
    }

    public IParticlePlayer getParticlePlayer(String particle) {
        if (particlePlayers.containsKey(particle))
            return particlePlayers.get(particle);
        return particlePlayers.get(particle.toUpperCase());
    }

    public void registerDefaultEffects(int playerRadius) {
        for (Effect effect : Effect.values()) {
            registerParticlePlayer(effect.name(), createStandardParticlePlayer(effect, playerRadius));
        }
    }

    private IParticlePlayer createStandardParticlePlayer(Effect effect, int radius) {
        return new BukkitParticlePlayer(effect, radius);
    }

    public Set<String> getParticlePlayerNames() {
        return particlePlayers.keySet();
    }
}
