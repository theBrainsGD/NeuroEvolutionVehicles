package com.JayPi4c;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Frame extends JFrame {

	private static final long serialVersionUID = -3508396183945516167L;
	private ContentPanel cp;

	public Frame(String title) {
		super(title);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		add(cp = new ContentPanel());
		pack();
		setResizable(false);
		setVisible(true);
	}

	public void update() {
		cp.update();
		cp.repaint();
	}

}
