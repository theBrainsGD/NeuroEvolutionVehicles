package com.JayPi4c;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class Frame extends JFrame {

	private static final long serialVersionUID = -3508396183945516167L;
	private ContentPanel cp;

	public Frame(String title) {
		super(title);
		add(cp = new ContentPanel());
		JMenu options = new JMenu("Options");
		JMenuItem saveButton = new JMenuItem("Save model");
		saveButton.addActionListener(e -> {
			cp.saveModel();
		});
		JMenuItem loadButton = new JMenuItem("Load model");
		loadButton.addActionListener(e -> {
			cp.loadModel();
		});
		options.add(saveButton);
		options.add(loadButton);
		JMenu settings = new JMenu("Settings");
		JMenuItem cycles = new JMenuItem("set cycles");
		cycles.addActionListener(e -> {
			try {
				GeneticAlgorithm.CYCLES = Integer.parseInt(
						JOptionPane.showInputDialog(this, "cycles", "Set cycles", JOptionPane.QUESTION_MESSAGE));
			} catch (NumberFormatException exc) {
				exc.printStackTrace();
			}

		});
		settings.add(cycles);
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(options);
		menuBar.add(settings);
		setJMenuBar(menuBar);

		pack();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void update() {
		cp.update();
		cp.repaint();
	}

}
