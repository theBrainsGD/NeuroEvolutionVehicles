package com.JayPi4c.NeuroEvolution.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.JayPi4c.GenericNeuralNetwork;
import com.JayPi4c.NeuroEvolution.util.PVector;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Vehicle {

	@Setter
	private UUID id;

	private double maxSpeed = 5;
	private double maxForce = 0.2;

	private int sight = 100;
	private int lifespan = 35;
	private int lifeCounter;

	private PVector pos;
	private PVector vel;
	private PVector acc;
	private PVector startVel;

	private int checkPointFitness;
	private int lapFitness;
	@Setter
	private double fitness;
	private boolean dead;

	private ArrayList<Ray> rays;

	private int lapCount;
	private int checkpointIndex;

	private GenericNeuralNetwork brain;

	// TODO change initial velocity to be perpendicular to the first checkpoint
	public Vehicle(PVector start, PVector startVel, GenericNeuralNetwork nn, double mutationRate) {
		id = UUID.randomUUID();
		checkPointFitness = 0;
		lapFitness = 0;
		lapCount = 0;
		lifeCounter = 0;
		dead = false;
		checkpointIndex = 0;
		pos = start.copy();
		if (startVel == null)
			vel = new PVector(Math.random() * 2 - 1, Math.random() * 2 - 1);
		else
			vel = startVel.copy();
		this.startVel = vel.copy();
		acc = new PVector();
		rays = new ArrayList<>();
		for (int a = -45; a <= 45; a += 15) {
			rays.add(new Ray(pos, Math.toRadians(a)));
		}

		if (nn != null) {
			brain = nn.copy();
		} else
			brain = new GenericNeuralNetwork(rays.size(), rays.size() * 2, 2, 0, mutationRate);
	}

	public Vehicle(PVector start, double mutationRate) {
		this(start, null, null, mutationRate);
	}

	public void mutate() {
		brain.mutate();
	}

	public PVector getStartVelocity() {
		return this.startVel.copy();
	}

	public void setStartVelocity(PVector vel) {
		this.startVel = vel.copy();
	}

	public GenericNeuralNetwork getBrain() {
		return brain.copy();
	}

	public void setBrain(GenericNeuralNetwork nn) {
		brain = nn;
	}

	public void applyForce(PVector force) {
		acc.add(force);
	}

	public void look(List<Boundary> walls) {
		double[][] inputs = new double[1][rays.size()];
		for (int i = 0; i < rays.size(); i++) {
			Ray ray = rays.get(i);
			double rec = sight;
			for (Boundary wall : walls) {
				PVector pt = ray.cast(wall);
				if (pt != null) {
					double d = PVector.dist(pos, pt);
					if (d < rec)
						rec = d;
				}
			}
			if (rec < 5) { // vehicle crashed in the wall
				dead = true;
				return;
			}
			inputs[0][i] = map(rec, 0, sight, 1, 0);
		}
		double[][] output = brain.query(inputs).toArray();
		double angle = map(output[0][0], 0, 1, -Math.PI, Math.PI);
		double speed = map(output[1][0], 0, 1, 0, maxSpeed);
		angle += vel.heading();
		PVector steering = PVector.fromAngle(angle);
		steering.setMag(speed);
		steering.sub(vel);
		steering.limit(maxForce);
		applyForce(steering);
	}

	public void update() {
		if (!dead) {
			pos.add(vel);
			vel.add(acc);
			vel.limit(maxSpeed);
			acc.mult(0);
			lifeCounter++;
			if (lifeCounter > lifespan) {
				dead = true;
			}
			rays = new ArrayList<>();
			for (int a = -45; a <= 45; a += 15) {
				rays.add(new Ray(this.pos, Math.toRadians(a) + this.vel.heading()));
			}
		}
	}

	public void check(List<Boundary> checkpoints) {
		Boundary goal = checkpoints.get(checkpointIndex);
		double d = pldistance(goal.getA(), goal.getB(), pos.x, pos.y);
		if (d < 5) {
			checkpointIndex = ++checkpointIndex % checkpoints.size();
			if (checkpointIndex == 0) {
				lapCount++;
				lapFitness++;
				if (lapCount % 2 == 0) {
					lifespan--;
				}
			}
			checkPointFitness++;
			lifeCounter = 0;
		}
	}

	public void calculateFitness() {
		fitness = Math.pow(2, checkPointFitness);
	}

	private double pldistance(PVector p1, PVector p2, double x, double y) {
		double num = Math.abs((p2.y - p1.y) * x - (p2.x - p1.x) * y + p2.x * p1.y - p2.y * p1.x);
		double den = PVector.dist(p1, p2);
		return num / den;
	}

	/**
	 * Taken from Processing
	 * 
	 * @param value
	 * @param inputMin
	 * @param inputMax
	 * @param outputMin
	 * @param outputMax
	 * @return
	 */
	private double map(double value, double inputMin, double inputMax, double outputMin, double outputMax) {
		return outputMin + (outputMax - outputMin) * ((value - inputMin) / (inputMax - inputMin));
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof Vehicle v && v.id.equals(id);
	}
}
