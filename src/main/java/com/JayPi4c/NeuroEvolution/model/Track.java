package main.java.com.JayPi4c.NeuroEvolution.model;

import java.awt.Graphics2D;
import java.util.ArrayList;

import main.java.com.JayPi4c.NeuroEvolution.util.OpenSimplexNoise;
import main.java.com.JayPi4c.NeuroEvolution.util.PVector;
import main.java.com.JayPi4c.NeuroEvolution.view.ContentPanel;

public class Track {
	public static final int PARTS = 120;
	public static final int CHECKPOINT_DIVIDER = 4;
	public static final int NOISE_MAX = 12;
	public static final int HALF_TRACK_WIDTH = 30;

	private PVector start, end;

	private ArrayList<Boundary> walls;
	private ArrayList<Boundary> checkpoints;

	public void buildTrack() {
		ArrayList<PVector> ptsInner = new ArrayList<PVector>();
		ArrayList<PVector> ptsOuter = new ArrayList<PVector>();
		checkpoints = new ArrayList<Boundary>();
		OpenSimplexNoise noise = new OpenSimplexNoise((long) (Math.random() * 1000000));
		for (int i = 0; i < PARTS; i++) {
			double a = map(i, 0, PARTS, 0, 2 * Math.PI);
			// for (double a = 0; a < Math.PI * 2; a += 0.1) {
			double xoff = map(Math.cos(a), -1, 1, 0, NOISE_MAX);
			double yoff = map(Math.sin(a), -1, 1, 0, NOISE_MAX);
			double r = map(noise.eval(xoff, yoff), -1, 1, ContentPanel.WIDTH * 0.25, ContentPanel.WIDTH * 0.4);
			double innerX = Math.cos(a) * (r - HALF_TRACK_WIDTH) + ContentPanel.WIDTH * 0.5;
			double innerY = Math.sin(a) * (r - HALF_TRACK_WIDTH) + ContentPanel.HEIGHT * 0.5;
			ptsInner.add(new PVector(innerX, innerY));

			double outerX = Math.cos(a) * (r + HALF_TRACK_WIDTH) + ContentPanel.WIDTH * 0.5;
			double outerY = Math.sin(a) * (r + HALF_TRACK_WIDTH) + ContentPanel.HEIGHT * 0.5;
			ptsOuter.add(new PVector(outerX, outerY));
			if (i % CHECKPOINT_DIVIDER == 0)
				checkpoints.add(new Boundary(innerX, innerY, outerX, outerY));

		}
		walls = new ArrayList<Boundary>();
		walls.addAll(createBoundaries(ptsInner, true));
		walls.addAll(createBoundaries(ptsOuter, true));

		start = checkpoints.get(0).midPoint();
		end = checkpoints.get(checkpoints.size() - 1).midPoint();

	}

	public void show(Graphics2D g) {
		for (Boundary b : walls)
			b.show(g);
		g.drawOval((int) start.x, (int) start.y, 5, 5);
	}

	public PVector getStart() {
		return start.copy();
	}

	public PVector getEnd() {
		return end.copy();
	}

	public ArrayList<Boundary> getWalls() {
		return walls;
	}

	public ArrayList<Boundary> getCheckpoints() {
		return checkpoints;
	}

	private ArrayList<Boundary> createBoundaries(ArrayList<PVector> pts, boolean closed) {
		ArrayList<Boundary> boundaries = new ArrayList<Boundary>();

		for (int i = 0; i < pts.size() - (closed ? 0 : 1); i++) {
			PVector p1 = pts.get(i);
			PVector p2 = pts.get((i + 1) % pts.size());
			boundaries.add(new Boundary(p1.x, p1.y, p2.x, p2.y));
		}
		return boundaries;
	}

	double map(double value, double inputMin, double inputMax, double outputMin, double outputMax) {
		return outputMin + (outputMax - outputMin) * ((value - inputMin) / (inputMax - inputMin));
	}

}
