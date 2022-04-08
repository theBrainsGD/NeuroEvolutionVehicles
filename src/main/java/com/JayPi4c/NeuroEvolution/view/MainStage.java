package com.JayPi4c.NeuroEvolution.view;

import com.JayPi4c.NeuroEvolution.controller.MainStageController;
import com.JayPi4c.NeuroEvolution.controller.SimulationController;
import com.JayPi4c.NeuroEvolution.model.GeneticAlgorithm;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class MainStage extends Stage {

	private MainStageController mainStageController;
	private SimulationController simulationController;

	private MenuBar menuBar;
	private Menu settingsMenu;
	private MenuItem saveModel;
	private MenuItem loadModel;

	private ToolBar toolBar;
	private Button resetButtonToolbar;
	private Button startButtonToolbar;
	private Button pauseButtonToolbar;

	private InformationPanel informationPanel;
	private MainPanel mainPanel;

	private Scene mainStageScene;

	private int mainStageWidth = 400;
	private int mainStageHeight = 500;

	private GeneticAlgorithm geneticAlgorithm;

	public MainStage() {
		setTitle("Neuro Evolution Demo");

		geneticAlgorithm = new GeneticAlgorithm(mainStageWidth, mainStageWidth);

		createMenubar();
		createToolbar();

		mainStageController = new MainStageController(this);
		simulationController = new SimulationController(this, geneticAlgorithm);

		mainPanel = new MainPanel(geneticAlgorithm);
		StackPane holder = new StackPane(mainPanel);
		informationPanel = new InformationPanel(geneticAlgorithm);
		var vbox = new VBox(menuBar, toolBar, informationPanel, holder);

		mainStageScene = new Scene(vbox);
		setScene(mainStageScene);

		mainPanel.paint();

		setResizable(false);
		show();
	}

	private void createMenubar() {
		log.debug("Creating Menubar");

		saveModel = new MenuItem("save Model");
		loadModel = new MenuItem("load Model");
		settingsMenu = new Menu("settings", null, saveModel, loadModel);

		menuBar = new MenuBar(settingsMenu);
	}

	private void createToolbar() {
		log.debug("Creating Toolbar");

		resetButtonToolbar = new Button("R");
		startButtonToolbar = new Button("S");
		pauseButtonToolbar = new Button("P");
		toolBar = new ToolBar(startButtonToolbar, pauseButtonToolbar, resetButtonToolbar);
	}

}
