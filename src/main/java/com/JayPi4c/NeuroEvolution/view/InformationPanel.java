package com.JayPi4c.NeuroEvolution.view;

import com.JayPi4c.NeuroEvolution.model.GeneticAlgorithm;
import com.JayPi4c.NeuroEvolution.util.Observable;
import com.JayPi4c.NeuroEvolution.util.Observer;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class InformationPanel extends Pane implements Observer {

	private TextField generationCount;
	private TextField currentScore;
	private TextField bestFitness;

	public InformationPanel(GeneticAlgorithm ga) {
		ga.addObserver(this);
		generationCount = new TextField("gen: #0");
		generationCount.setPrefWidth(70);
		generationCount.setEditable(false);
		currentScore = new TextField("Score: ");
		currentScore.setPrefWidth(75);
		currentScore.setEditable(false);
		bestFitness = new TextField("max Fit:");
		bestFitness.setPrefWidth(80);
		bestFitness.setEditable(false);
		var hBox = new HBox(generationCount, currentScore, bestFitness);
		getChildren().add(hBox);

	}

	@Override
	public void update(Observable observable) {
		if (observable instanceof GeneticAlgorithm geneticAlgorithm) {
			Platform.runLater(() -> {
				generationCount.setText("gen #" + geneticAlgorithm.getGenerationCount());
				currentScore.setText("Score: ");
				bestFitness.setText("max Fit: ");
			});
		}
	}
}
