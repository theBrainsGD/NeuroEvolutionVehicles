package com.JayPi4c.NeuroEvolution.model.track;

import com.JayPi4c.NeuroEvolution.model.Boundary;
import com.JayPi4c.NeuroEvolution.util.PVector;

import java.util.List;

public interface Track {

    /**
     * Builds the track.<br>
     * Afterwards the track is ready to be used and the attribute getters can be called.
     */
    public void buildTrack();

    public PVector getStart();

    public PVector getEnd();

    public List<Boundary> getCheckpoints();

    public List<Boundary> getWalls();

    /**
     * Returns a PVector which points into the direction the vehicles should drive.
     *
     * @return
     */
    public PVector getStartVelocity();
}
