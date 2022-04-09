package com.JayPi4c.NeuroEvolution.view.drawer;

import com.jfoenix.controls.JFXToggleNode;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Getter;

@Getter
public class TrackCard extends Pane {

    private Label header;
    private JFXToggleNode perlinTrackToggle;
    private Label perlinTrackLabel;
    private JFXToggleNode convexHullToggle;
    private Label convexHullLabel;

    public TrackCard() {
        header = new Label();

        perlinTrackToggle = new JFXToggleNode();
        perlinTrackLabel = new Label();
        perlinTrackToggle.setGraphic(perlinTrackLabel);
        convexHullToggle = new JFXToggleNode();
        convexHullLabel = new Label();
        convexHullToggle.setGraphic(convexHullLabel);

        var vBox = new VBox(header);
        vBox.getChildren().addAll(perlinTrackToggle, convexHullToggle);
        getChildren().add(vBox);
    }
}
