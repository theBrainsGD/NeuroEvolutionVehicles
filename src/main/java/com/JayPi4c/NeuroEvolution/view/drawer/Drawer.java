package com.JayPi4c.NeuroEvolution.view.drawer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Drawer extends JFXDrawer {

	private StackPane pane;

	private Button drawerPaneOneButton;
	private Button drawerPaneTwoButton;
	private Button drawerPaneLanguageButton;
	private Button drawerDoneButton;

	private CardOne cardOne;
	private CardTwo cardTwo;
	private LanguageCard languageCard;

	public Drawer() {
		pane = new StackPane();

		drawerPaneOneButton = new JFXButton();
		drawerPaneTwoButton = new JFXButton();
		drawerPaneLanguageButton = new JFXButton();
		drawerDoneButton = new JFXButton();

		VBox buttonPane = new VBox(drawerPaneOneButton, drawerPaneTwoButton, drawerPaneLanguageButton,
				drawerDoneButton);
		Pane cardsPane = new StackPane();

		cardOne = new CardOne();
		cardTwo = new CardTwo();
		languageCard = new LanguageCard();

		cardsPane.getChildren().add(cardOne);

		addSwitchPaneAction(drawerPaneOneButton, cardsPane, cardOne);
		addSwitchPaneAction(drawerPaneTwoButton, cardsPane, cardTwo);
		addSwitchPaneAction(drawerPaneLanguageButton, cardsPane, languageCard);

		HBox drawerContent = new HBox(buttonPane, cardsPane);
		pane.getChildren().addAll(drawerContent);

		setSidePane(pane);
		setDefaultDrawerSize(200);
		setResizeContent(true);
		setOverLayVisible(false);
		setResizableOnDrag(true);

		drawerDoneButton.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
			log.debug("Closing drawer");
			close();
		});
	}

	private void addSwitchPaneAction(Button button, Pane parent, Pane pane) {
		button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
			parent.getChildren().clear();
			parent.getChildren().add(pane);
		});
	}
}
