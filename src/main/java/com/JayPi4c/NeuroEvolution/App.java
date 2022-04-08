package com.JayPi4c.NeuroEvolution;

import com.JayPi4c.NeuroEvolution.view.MainStage;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() throws Exception {
		log.debug("Initializing Application");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		log.debug("Starting Application");
		new MainStage();
	}

	@Override
	public void stop() throws Exception {
		log.debug("Stopping Application");
	}

}
