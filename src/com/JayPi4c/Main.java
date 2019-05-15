package com.JayPi4c;

public class Main {
	public static void main(String args[]) {
		// setup

		Frame f = new Frame("Neuro-Evolution-Game");

		// loop
		while (true) {
			try {
				// Das programm soll mit 30fps laufen
				Thread.sleep(1000 / 30);
				f.update();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
