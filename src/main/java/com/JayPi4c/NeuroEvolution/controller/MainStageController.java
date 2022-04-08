package com.JayPi4c.NeuroEvolution.controller;

import com.JayPi4c.NeuroEvolution.view.MainStage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainStageController {

	public MainStageController(MainStage mainStage) {
		mainStage.getResetButtonToolbar().setOnAction(e -> reset());
		mainStage.getSaveModel().setOnAction(e -> log.debug("Save current model"));
		mainStage.getLoadModel().setOnAction(e -> log.debug("load new model"));
	}

	public void reset() {
		log.debug("Reset GA");
	}
}
