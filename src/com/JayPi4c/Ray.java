package com.JayPi4c;

import java.awt.Color;
import java.awt.Graphics2D;

import com.JayPi4c.utils.PVector;

public class Ray {
	private PVector pos;
	private PVector dir;

	Ray(PVector pos, double angle) {
		this.pos = pos;
		this.dir = PVector.fromAngle(angle);
	}

	public void setAngle(double angle) {
		this.dir = PVector.fromAngle(angle);
	}

	public void lookAt(double x, double y) {
		dir.x = x - this.pos.x;
		dir.y = y - this.pos.y;
		dir.normalize();
	}

	public void show(Graphics2D g) {
		g.setColor(new Color(0, 0, 0, 150));
		g.translate((int) pos.x, (int) pos.y);
		g.drawLine(0, 0, (int) (dir.x * GeneticAlgorithm.SIGHT), (int) (dir.y * GeneticAlgorithm.SIGHT));
		g.translate((int) -pos.x, (int) -pos.y);
	}

	public PVector cast(Boundary wall) {
		double x1 = wall.getA().x;
		double y1 = wall.getA().y;
		double x2 = wall.getB().x;
		double y2 = wall.getB().y;

		double x3 = this.pos.x;
		double y3 = this.pos.y;
		double x4 = this.pos.x + this.dir.x;
		double y4 = this.pos.y + this.dir.y;

		double den = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
		if (den == 0) {
			return null;
		}

		double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / den;
		double u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / den;
		if (t > 0 && t < 1 && u > 0) {
			PVector pt = new PVector(x1 + t * (x2 - x1), y1 + t * (y2 - y1));
			return pt;
		} else {
			return null;
		}
	}

	public PVector getDirection() {
		return this.dir.copy();
	}
}