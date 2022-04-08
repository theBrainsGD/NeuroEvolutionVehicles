package main.java.com.JayPi4c.NeuroEvolution.model;

import java.awt.Color;
import java.awt.Graphics2D;

import main.java.com.JayPi4c.NeuroEvolution.util.PVector;

public class Boundary {
	private PVector a, b;

	public Boundary(double x1, double y1, double x2, double y2) {
		a = new PVector(x1, y1);
		b = new PVector(x2, y2);
	}

	public PVector midPoint() {
		return new PVector((a.x + b.x) * 0.5, (a.y + b.y) * 0.5);
	}

	public void show(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.drawLine((int) a.x, (int) a.y, (int) b.x, (int) b.y);
	}

	public void show(Graphics2D g, Color c) {
		g.setColor(c);
		g.drawLine((int) a.x, (int) a.y, (int) b.x, (int) b.y);
	}

	public PVector getA() {
		return a;
	}

	public PVector getB() {
		return b;
	}

}
