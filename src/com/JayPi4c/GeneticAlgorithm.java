package com.JayPi4c;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GeneticAlgorithm {

	public static final int POPULATION_SIZE = 100;
	public static final double MUTAION_RATE = 0.05;
	public static final int LIFESPAN = 40;
	public static final int SIGHT = 50;
	public static int CYCLES = 1;

	private int generation_count;

	private ArrayList<Vehicle> population;
	private ArrayList<Vehicle> savedVehicles;
	private Vehicle bestV = null;

	public GeneticAlgorithm(Track track) {
		generation_count = 0;
		population = new ArrayList<Vehicle>();
		savedVehicles = new ArrayList<Vehicle>();
		for (int i = 0; i < POPULATION_SIZE; i++) {
			population.add(new Vehicle(track.getStart(), null, null));
		}
	}

	public void update(Track track) {
		bestV = population.get(0);
		for (int c = 0; c < CYCLES; c++) {

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

		}
	}

	public void show(Graphics2D g) {
		if (bestV != null)
			bestV.highlight(g);

		for (int i = population.size() - 1; i >= 0; i--) {
			Vehicle v = population.get(i);
			if (!v.equals(bestV))
				v.show(g);

		}

		g.setColor(Color.BLACK);
		g.drawString("Generation #" + generation_count, 10, 20);
	}

	public void showPOV(Graphics2D g, Track track) {
		if (bestV != null) {
			double part = 0.1;
			double wSq = ContentPanel.WIDTH * ContentPanel.WIDTH;// * part;
			double scene[] = bestV.getScene(track.getWalls());
			double w = ContentPanel.WIDTH / (double) scene.length;
			for (int i = 0; i < scene.length; i++) {
				double d = scene[i];
				double sq = d * d;
				int b = 25;
				if (sq <= wSq)
					b = (int) map(sq, 0, wSq, 235, 25);
				double h = 0;
				if (d <= ContentPanel.WIDTH)
					h = map(d, 0, ContentPanel.WIDTH, ContentPanel.HEIGHT - 10, 0);
				try {
					g.setColor(new Color(b, b, b));
				} catch (IllegalArgumentException exc) {
					// System.out.println("B ist: " + b);
				}
				double x = i * w;
				double y = (ContentPanel.HEIGHT - h) * 0.5;

				g.fillRect((int) x, (int) y, (int) w + 1, (int) h);
			}
		}
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
		Vehicle child = new Vehicle(track.getStart(), v.getStartVelocity(), v.getBrain());

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

	public void saveModel() {
		if (bestV != null) {
			try {
				bestV.getBrain().serialize();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void loadModel() {
		JFileChooser chooser = new JFileChooser(new File(".").getAbsolutePath());
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Neural Network files", "nn");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		NeuralNetworkMutating nn = new NeuralNetworkMutating(6, 12, 2, 0);
		try {
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File f = chooser.getSelectedFile();
				nn = NeuralNetworkMutating.deserialize(f);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		for (int i = population.size() - 1; i >= 0; i--) {
			population.get(i).setBrain(nn.copy());
		}
	}

	double map(double value, double inputMin, double inputMax, double outputMin, double outputMax) {
		return outputMin + (outputMax - outputMin) * ((value - inputMin) / (inputMax - inputMin));
	}

}
