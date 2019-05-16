package com.JayPi4c;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class GeneticAlgorithm {

	public static final int POPULATION_SIZE = 100;
	public static final double MUTAION_RATE = 0.05;
	public static final int LIFESPAN = 50;
	public static final int SIGHT = 50;

	private int generation_count;

	private ArrayList<Vehicle> population;
	private ArrayList<Vehicle> savedVehicles;
	private Vehicle bestV = null;

	public GeneticAlgorithm(Track track) {
		generation_count = 0;
		population = new ArrayList<Vehicle>();
		savedVehicles = new ArrayList<Vehicle>();
		for (int i = 0; i < POPULATION_SIZE; i++) {
			population.add(new Vehicle(track.getStart(), null));
		}
	}

	public void update(Track track) {

		// final int CYCLES = 10;
		bestV = population.get(0);
		// for(int i = 0; i < CYCLES; i++) {

		for (Vehicle v : population) {
			v.look(track.getWalls());
			v.check(track.getCheckpoints());
			v.bounds();
			v.update();

			if (v.getFitness() > bestV.getFitness()) {
				bestV = v;
			}
		}

		for (int i = population.size() - 1; i >= 0; i--) {
			Vehicle v = population.get(i);
			if (v.isDead() || v.isFinished()) {
				savedVehicles.add(population.remove(i));
			}
		}

		if (population.size() == 0) {
			track.buildTrack();
			nextGeneration(track);
			generation_count++;
			System.out.println("Generation #" + generation_count);
		}

//	}
	}

	public void show(Graphics2D g) {

		for (int i = population.size() - 1; i >= 0; i--) {
			Vehicle v = population.get(i);
			v.show(g);

		}
		if (bestV != null)
			bestV.highlight(g);
		g.setColor(Color.BLACK);
		g.drawString("Generation #" + generation_count, 10, 20);
	}

	private void nextGeneration(Track track) {
		calculateFitness();
		population = new ArrayList<Vehicle>();
		for (int i = 0; i < POPULATION_SIZE; i++) {
			population.add(pickOne(track));
		}
		savedVehicles = new ArrayList<Vehicle>();
	}

	private Vehicle pickOne(Track track) {
		int index = 0;
		double r = Math.random();
		while (r > 0) {
			r = r - savedVehicles.get(index).getFitness();
			index++;
		}
		index--;
		Vehicle v = savedVehicles.get(index);
		Vehicle child = new Vehicle(track.getStart(), v.getBrain());

		child.mutate();
		return child;
	}

	private void calculateFitness() {
		for (Vehicle v : savedVehicles)
			v.calculateFitness();
		double sum = 0;
		for (Vehicle v : savedVehicles)
			sum += v.getFitness();
		for (Vehicle v : savedVehicles) {
			v.setFitness(v.getFitness() / sum);
		}
	}

	public int getGenerationCount() {
		return generation_count;
	}

}
