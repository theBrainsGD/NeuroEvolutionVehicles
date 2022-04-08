package com.JayPi4c.NeuroEvolution.controller;

import com.JayPi4c.NeuroEvolution.model.GeneticAlgorithm;
import com.JayPi4c.NeuroEvolution.view.MainStage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimulationController {

	private GeneticAlgorithm ga;

	private boolean simulationRunning = false;

	private Simulation simulation;

	public SimulationController(MainStage mainStage, GeneticAlgorithm ga) {
		this.ga = ga;
		mainStage.getStartButtonToolbar().setOnAction(e -> {
			if (!isSimulationRunning())
				start();
			else
				resume();
		});
		mainStage.getPauseButtonToolbar().setOnAction(e -> pause());
		mainStage.getResetButtonToolbar().setOnAction(e -> reset());

	}

	public void pause() {
		if (isSimulationRunning())
			simulation.setPaused(true);
	}

	public void start() {
		log.debug("Starting new simulation");
		simulationRunning = true;
		ga.reset();
		simulation = new Simulation(ga, this);
		simulation.setDaemon(true);
		simulation.start();
	}

	public void resume() {
		if (isSimulationRunning() && simulation.isPaused()) {
			simulation.setPaused(false);
			synchronized (simulation.getLock()) {
				simulation.getLock().notifyAll();
			}
		}
	}

	public void stop() {
		if (isSimulationRunning()) {
			simulation.setStopped(true);
			simulation.setPaused(false);
			simulation.interrupt();
			synchronized (simulation.getLock()) {
				simulation.getLock().notifyAll();
			}
		}
	}

	public void reset() {
		stop();
		start();
	}

	public boolean isSimulationRunning() {
		return simulation != null || simulationRunning;
	}

	public void finish() {
		log.debug("finishing the simulation");
	}

}
