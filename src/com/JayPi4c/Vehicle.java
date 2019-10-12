package com.JayPi4c;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.JayPi4c.utils.PVector;

public class Vehicle {
	public static final double MAX_SPEED = 5;
	public static final double MAX_FORCE = 0.2;

	private PVector pos, vel, acc;
	private PVector startVel;

	private double fitness;

	private boolean dead;
	private boolean finished;

	private int sight = GeneticAlgorithm.SIGHT;
	private ArrayList<Ray> rays;
	private int counter;
	private int index;
	private int lifespan = GeneticAlgorithm.LIFESPAN;

	private Boundary goal;

	private GenericNeuralNetwork brain;
	private int lap;

	public Vehicle(PVector start, PVector startVel, GenericNeuralNetwork nn) {
		fitness = 0;
		lap = 0;
		dead = false;
		finished = false;
		counter = 0;
		index = 0;
		goal = null;
		pos = start.copy();
		if (startVel == null)
			vel = new PVector(Math.random() * 2 - 1, Math.random() * 2 - 1);
		else
			vel = startVel.copy();
		this.startVel = vel.copy();
		acc = new PVector();

		rays = new ArrayList<Ray>();
		for (int a = -45; a < 45; a += 15) {
			rays.add(new Ray(pos, Math.toRadians(a)));
		}

		if (nn != null)
			brain = (GenericNeuralNetwork) nn.copy();
		else
			brain = new GenericNeuralNetwork(rays.size(), rays.size() * 2, 2, 0, GeneticAlgorithm.MUTAION_RATE);
	}

	public Vehicle(PVector start) {
		this(start, null, null);
	}

	public void mutate() {
		brain.mutate(GeneticAlgorithm.MUTAION_RATE);
	}

	public PVector getStartVelocity() {
		return startVel.copy();
	}

	public void setStartVelocity(PVector vel) {
		this.startVel = vel.copy();
	}

	public GenericNeuralNetwork getBrain() {
		return (GenericNeuralNetwork) brain.copy();
	}

	public void setBrain(GenericNeuralNetwork nn) {
		brain = nn;
	}

	public void applyForce(PVector force) {
		acc.add(force);
	}

	public void look(ArrayList<Boundary> walls) {

		double inputs[][] = new double[1][rays.size()];
		for (int i = 0; i < rays.size(); i++) {
			Ray ray = rays.get(i);
			double record = sight;
			for (Boundary wall : walls) {
				PVector pt = ray.cast(wall);
				if (pt != null) {
					double d = PVector.dist(pos, pt);
					if (d < record && d < sight) {
						record = d;
					}
				}
			}

			if (record < 5) {
				dead = true;
			}
			inputs[0][i] = map(record, 0, 50, 1, 0);
		}

		double output[][] = brain.query(inputs).toArray();
		double angle = map(output[0][0], 0, 1, -Math.PI, Math.PI);
		double speed = map(output[1][0], 0, 1, 0, MAX_SPEED);
		angle += vel.heading();
		PVector steering = PVector.fromAngle(angle);
		steering.setMag(speed);
		steering.sub(vel);
		steering.limit(MAX_FORCE);
		applyForce(steering);

	}

	public double[] getScene(ArrayList<Boundary> walls) {// , Graphics2D g) {
		double scene[] = new double[500];
		double heading = vel.heading();
		for (int i = 0; i < scene.length; i++) {
			double angle = heading + map(i, 0, scene.length, Math.toRadians(-40), Math.toRadians(40));
			Ray ray = new Ray(pos, angle);
			// ray.show(g);
			// PVector closest = null;
			double record = 999999;
			for (int j = 0; j < walls.size(); j++) {
				Boundary w = walls.get(j);
				PVector pt = ray.cast(w);
				if (pt != null) {
					double d = PVector.dist(pt, pos);
					double a = ray.getDirection().heading() + vel.heading();
					d *= Math.cos(a);
					if (d < record) {
						record = d;
						// closest = pt;
					}
				}
			}
			scene[i] = record;
		}
		return scene;
	}

	public void update() {
		if (!dead && !finished) {
			pos.add(vel);
			vel.add(acc);
			vel.limit(MAX_SPEED);
			acc.mult(0);
			counter++;
			if (counter > lifespan) {
				dead = true;
			}

			for (Ray r : rays) {
				r.rotate(vel.heading());
			}
		}
	}

	public void check(ArrayList<Boundary> checkpoints) {
		if (!finished) {
			goal = checkpoints.get(index);
			double d = pldistance(goal.getA(), goal.getB(), pos.x, pos.y);
			if (d < 5) {
				index = ++index % checkpoints.size();
				if (index == 0) {
					lap++;
					if (--lifespan < 15)
						lifespan = 15;
				}
				fitness++;
				counter = 0;
			}
		}
	}

	public void bounds() {
		if (this.pos.x > ContentPanel.WIDTH || this.pos.x < 0 || this.pos.y > ContentPanel.HEIGHT || this.pos.y < 0)
			this.dead = true;
	}

	public void calculateFitness() {
		fitness = Math.pow(2, fitness);
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public void show(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.translate(pos.x, pos.y);
		g.rotate(vel.heading());
		g.drawRect(-5, -2, 10, 5);
		g.rotate(-vel.heading());
		g.translate(-pos.x, -pos.y);
	}

	public void highlight(Graphics2D g) {
		if (goal != null)
			goal.show(g, Color.BLUE);
		for (Ray r : rays)
			r.show(g);
		g.setColor(Color.BLACK);
		g.drawString("Lap #" + lap, 10, 40);
		g.drawString("Frames left: " + (lifespan - counter), 10, 60);
		g.setColor(Color.RED);
		g.translate(pos.x, pos.y);
		g.rotate(vel.heading());
		g.drawRect(-5, -2, 10, 5);
		g.rotate(-vel.heading());
		g.translate(-pos.x, -pos.y);
	}

	public boolean isDead() {
		return dead;
	}

	public boolean isFinished() {
		return finished;
	}

	private double pldistance(PVector p1, PVector p2, double x, double y) {
		double num = Math.abs((p2.y - p1.y) * x - (p2.x - p1.x) * y + p2.x * p1.y - p2.y * p1.x);
		double den = PVector.dist(p1, p2);
		return num / den;
	}

	double map(double value, double inputMin, double inputMax, double outputMin, double outputMax) {
		return outputMin + (outputMax - outputMin) * ((value - inputMin) / (inputMax - inputMin));
	}
}
