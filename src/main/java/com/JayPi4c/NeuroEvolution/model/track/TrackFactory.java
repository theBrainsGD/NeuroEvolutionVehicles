package com.JayPi4c.NeuroEvolution.model.track;

public class TrackFactory {

    public static final String PERLIN_NOISE = "perlin_noise";
    public static final String CONVEX_HULL = "convex_hull";
    public static final String PART_TRACK = "part_track";

    public static Track createTrack(String type, int panelWidth, int panelHeight) {
        return switch (type) {
            case PERLIN_NOISE -> new PerlinTrack(panelWidth, panelHeight);
            case CONVEX_HULL -> new ConvexHullTrack(panelWidth, panelHeight);
            case PART_TRACK -> new PartTrack(panelWidth, panelHeight);
            default -> new PerlinTrack(panelWidth, panelHeight);
        };
    }
}