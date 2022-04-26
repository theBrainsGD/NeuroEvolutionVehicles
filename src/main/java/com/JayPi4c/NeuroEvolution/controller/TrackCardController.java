package com.JayPi4c.NeuroEvolution.controller;

import com.JayPi4c.NeuroEvolution.model.track.TrackFactory;
import com.JayPi4c.NeuroEvolution.view.MainStage;
import javafx.scene.control.ToggleGroup;

public class TrackCardController {

    public TrackCardController(MainStage mainStage) {
        int panelWidth = (int) mainStage.getMainPanel().getWidth();
        int panelHeight = (int) mainStage.getMainPanel().getHeight();
        var toggleGroup = new ToggleGroup();
        mainStage.getDrawer().getTrackCard().getPerlinTrackToggle().setOnAction(
                event -> mainStage.getGeneticAlgorithm().setTrack(TrackFactory.createTrack(TrackFactory.PERLIN_NOISE,
                        panelWidth, panelHeight)));
        mainStage.getDrawer().getTrackCard().getPerlinTrackToggle().setToggleGroup(toggleGroup);

        mainStage.getDrawer().getTrackCard().getConvexHullToggle().setOnAction(
                event -> mainStage.getGeneticAlgorithm().setTrack(TrackFactory.createTrack(TrackFactory.CONVEX_HULL,
                        panelWidth, panelHeight)));
        mainStage.getDrawer().getTrackCard().getConvexHullToggle().setToggleGroup(toggleGroup);
        mainStage.getDrawer().getTrackCard().getConvexHullToggle().setSelected(true);

        mainStage.getDrawer().getTrackCard().getPartTrackToggle().setOnAction(
                event -> mainStage.getGeneticAlgorithm().setTrack(TrackFactory.createTrack(TrackFactory.PART_TRACK,
                        panelWidth, panelHeight)));
        mainStage.getDrawer().getTrackCard().getPartTrackToggle().setToggleGroup(toggleGroup);
    }
}
