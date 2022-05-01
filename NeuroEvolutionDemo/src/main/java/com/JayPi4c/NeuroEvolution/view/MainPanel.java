package com.JayPi4c.NeuroEvolution.view;

import java.util.List;

import com.JayPi4c.NeuroEvolution.model.GeneticAlgorithm;
import com.JayPi4c.NeuroEvolution.model.Vehicle;
import com.JayPi4c.NeuroEvolution.plugins.track.Track;
import com.JayPi4c.NeuroEvolution.plugins.util.Boundary;
import com.JayPi4c.NeuroEvolution.util.Observable;
import com.JayPi4c.NeuroEvolution.util.Observer;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MainPanel extends Canvas implements Observer {
	private GeneticAlgorithm ga;

	public MainPanel(GeneticAlgorithm ga) {
		this.ga = ga;
		ga.addObserver(this);
		setWidth(400);
		setHeight(400);
		paint();
	}

	public void paint() {
		GraphicsContext gc = getGraphicsContext2D();
		gc.setFill(Color.GREEN);
		gc.fillRect(0, 0, getWidth(), getHeight());

		// draw the track
		gc.setStroke(Color.BLACK);
		Track track = ga.getTrack();
		for (Boundary b : track.getWalls()) {
			gc.strokeLine(b.getA().x, b.getA().y, b.getB().x, b.getB().y);
		}
		gc.strokeOval(track.getStart().x, track.getStart().y, 5, 5);
		// draw the checkpoints
		gc.setStroke(Color.RED);
		for (Boundary b : track.getCheckpoints()) {
			gc.strokeLine(b.getA().x, b.getA().y, b.getB().x, b.getB().y);
		}

		// draw the vehicles
		List<Vehicle> vehicles = ga.getPopulation();
		gc.setStroke(Color.BLACK);
		for (int i = 0; i < vehicles.size(); i++) {
			Vehicle v = vehicles.get(i);
			if (v.equals(ga.getPrevBest()))
				continue;
			drawVehicle(v, gc);
		}
		gc.setStroke(Color.RED);
		drawVehicle(ga.getPrevBest(), gc);
	}

	private void drawVehicle(Vehicle v, GraphicsContext gc) {
		if (v == null)
			return;
		gc.save();
		gc.translate(v.getPos().x, v.getPos().y);
		gc.rotate(Math.toDegrees(v.getVel().heading()));
		gc.strokeRect(-5, -2, 10, 5);
		gc.restore();
	}

	@Override
	public void update(Observable observable) {
		Platform.runLater(this::update);
	}

	private void update() {
		paint();
	}

}
