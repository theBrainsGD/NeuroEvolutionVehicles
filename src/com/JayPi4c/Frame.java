package com.JayPi4c;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(options);
		setJMenuBar(menuBar);

		pack();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	public void update() {
		cp.update();
		cp.repaint();
	}

}
