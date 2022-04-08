package com.JayPi4c.NeuroEvolution.model;

import com.JayPi4c.NeuroEvolution.util.PVector;

import lombok.Getter;

@Getter
public class Boundary {
	private PVector a;
	private PVector b;

	public Boundary(double x1, double y1, double x2, double y2) {
		a = new PVector(x1, y1);
		b = new PVector(x2, y2);
	}

	public PVector midPoint() {
		return new PVector((a.x + b.x) * 0.5, (a.y + b.y) * 0.5);
	}
}