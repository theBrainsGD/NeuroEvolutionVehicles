package com.JayPi4c;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ContentPanel extends JPanel {

	private static final long serialVersionUID = -853777491964887707L;

	public static final int WIDTH = 700;
	public static final int HEIGHT = 700;
	Dimension size = new Dimension(WIDTH, HEIGHT);
	Track track = new Track();

	GeneticAlgorithm ga;

	public ContentPanel() {
		setPreferredSize(size);
		setSize(size);
		track.buildTrack();
		ga = new GeneticAlgorithm(track);
	}

	public void saveModel() {
		ga.saveModel();
	}

	public void loadModel() {
		ga.loadModel();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage bImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) bImage.getGraphics();
		// set the background white
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		// draw the track
		track.show(g2);
		ga.show(g2);
		g2.dispose();
		g.drawImage(bImage, 0, 0, null);
		g.dispose();

	}

	public void update() {
		ga.update(track);
	}

	double map(double value, double inputMin, double inputMax, double outputMin, double outputMax) {
		return outputMin + (outputMax - outputMin) * ((value - inputMin) / (inputMax - inputMin));
	}

}
