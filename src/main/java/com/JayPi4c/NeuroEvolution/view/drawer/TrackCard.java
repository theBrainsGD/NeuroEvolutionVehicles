package com.JayPi4c.NeuroEvolution.view.drawer;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Getter;

@Getter
public class TrackCard extends Pane {

    private Label header;

    public TrackCard() {
        header = new Label();

        var vBox = new VBox(header);
        getChildren().add(vBox);
    }
}
