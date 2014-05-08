package sk.tomsik68.particleworkshop;

import java.util.HashSet;

import sk.tomsik68.particleworkshop.tasks.PlayParticleTask;

public class ParticlesManager implements Runnable {
    private final HashSet<PlayParticleTask> tasks = new HashSet<PlayParticleTask>();
    private final HashSet<PlayParticleTask> tasksToAdd = new HashSet<PlayParticleTask>();
    private final HashSet<PlayParticleTask> tasksToRemove = new HashSet<PlayParticleTask>();
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

}
