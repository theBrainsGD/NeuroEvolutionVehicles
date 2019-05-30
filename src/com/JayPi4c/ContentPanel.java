package com.JayPi4c;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ContentPanel extends JPanel {

	private static final long serialVersionUID = -853777491964887707L;

	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	Dimension size = new Dimension(2 * WIDTH, HEIGHT);
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
		BufferedImage bImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) bImage.getGraphics();
		// set the background white
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, WIDTH, HEIGHT);
		// draw the track
		track.show(g2);
		ga.show(g2);
		g2.dispose();

		BufferedImage bImage2 = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D) bImage2.getGraphics();
		// set the brackground white
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		ga.showPOV(g2d, track);
		// draw some serious shit!
		// g2d.setColor(Color.RED);
		// g2d.drawString("some serious shit!", 50, 50);
		g2d.dispose();

		g.drawImage(bImage2, WIDTH, 0, null);

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
