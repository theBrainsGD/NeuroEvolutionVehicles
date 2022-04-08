package com.JayPi4c.NeuroEvolution.model.track;

import java.util.ArrayList;

import com.JayPi4c.NeuroEvolution.model.Boundary;
import com.JayPi4c.NeuroEvolution.util.PVector;

public interface Track {

    /**
     * Builds the track.<br>
     * Afterwards the track is ready to be used and the attribute getters can be called.
     */
    public void buildTrack();

    public PVector getStart();

    public PVector getEnd();

    public ArrayList<Boundary> getCheckpoints();

    public ArrayList<Boundary> getWalls();
}
