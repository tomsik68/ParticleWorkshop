package sk.tomsik68.particleworkshop.files.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NameAlreadyBoundException;

import sk.tomsik68.particleworkshop.ParticleWorkshopPlugin;
import sk.tomsik68.particleworkshop.files.api.DataFile;
import sk.tomsik68.particleworkshop.logic.ParticleTaskData;

public class ParticleTasksDataFile extends DataFile<List<ParticleTaskData>> {

	public ParticleTasksDataFile(File file) {
		super(file);
		try {
			register(1, new ParticleTaskDataListIOv1());
		} catch (NameAlreadyBoundException e) {
			ParticleWorkshopPlugin.log
					.info("A fatally stupid error has occured(flatfile IO ID conflict). Please contact author of the plugin...");
			ParticleWorkshopPlugin.log.throwing(
					ParticleTasksDataFile.class.getSimpleName(), "<init>", e);
		}
	}

	@Override
	protected List<ParticleTaskData> getEmptyData() {
		return new ArrayList<ParticleTaskData>();
	}

	@Override
	protected int getDefaultIO() {
		return 1;
	}

}
