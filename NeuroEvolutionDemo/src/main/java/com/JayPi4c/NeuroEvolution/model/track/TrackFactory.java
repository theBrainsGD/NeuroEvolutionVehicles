package com.JayPi4c.NeuroEvolution.model.track;

import java.util.List;

import com.JayPi4c.NeuroEvolution.plugins.track.Track;

import lombok.Getter;
import lombok.Setter;

public class TrackFactory {

    public static final String PERLIN_NOISE = "perlin_noise";
    public static final String CONVEX_HULL = "convex_hull";
    public static final String PART_TRACK = "part_track";
    @Getter
    @Setter
    private static List<Track> customTracks;

    public static Track createTrack(String type, int panelWidth, int panelHeight) {
        return switch (type) {
            case PERLIN_NOISE -> new PerlinTrack(panelWidth, panelHeight);
            case CONVEX_HULL -> new ConvexHullTrack(panelWidth, panelHeight);
            case PART_TRACK -> new PartTrack(panelWidth, panelHeight);
            default -> {
                for (Track track : customTracks)
                    if (track.getTrackName().equals(type))
                        yield track;
                yield new PerlinTrack(panelWidth, panelHeight);
            }
        };
    }
}