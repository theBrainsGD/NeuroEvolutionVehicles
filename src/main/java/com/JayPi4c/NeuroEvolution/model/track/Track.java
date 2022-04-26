package com.JayPi4c.NeuroEvolution.model.track;

import com.JayPi4c.NeuroEvolution.model.Boundary;
import com.JayPi4c.NeuroEvolution.util.PVector;

import java.util.List;

public interface Track {

    /**
     * (Re-)builds the track.<br>
     * Afterwards the track is ready to be used and the attribute getters can be
     * called.
     * <br>
     * TODO: The track will be normalized in a square, which means, that the coordinates of the track will always be between 0 and 1. 
     * <br>
     * <b>This method must be called before any other function call in this
     * class.<b>
     */
    public void buildTrack();

    /**
     * Returns a PVector containing the position on which the vehicles should start
     * on the given track.
     * 
     * @return PVector containing the tracks start position
     * @see PVector
     */
    public PVector getStart();

    /**
     * Returns a list of boundries describing the checkpoints on the track. The
     * checkpoints are ordered and have to be reached after another.
     * 
     * @return a ordered list of all checkpoints for the generated track.
     * @see Boundary
     */
    public List<Boundary> getCheckpoints();

    /**
     * Returns a list of boundries describing the walls of the track. The bounries
     * may not be connected in any way. All boundries together form the track,
     * meaning that the elements do build the inner and outer track limits. If the
     * vehicle collides with any of these elements, it might be considered crashed.
     * 
     * @return a list of all boundries describing the track
     * @see Boundary
     */
    public List<Boundary> getWalls();

    /**
     * Returns a PVector which points into the direction the vehicles should drive.
     * This normally is the direction orthogonally to the start/finish line pointing
     * to the first checkpoint.
     *
     * @return starting direction on the given startpoint.
     * @see PVector
     */
    public PVector getStartVelocity();
}
