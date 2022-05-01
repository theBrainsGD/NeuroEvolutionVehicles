package com.JayPi4c.NeuroEvolution.model.track;

import com.JayPi4c.NeuroEvolution.plugins.track.Track;
import com.JayPi4c.NeuroEvolution.plugins.util.Boundary;
import com.JayPi4c.NeuroEvolution.plugins.util.PVector;
import com.JayPi4c.NeuroEvolution.util.OpenSimplexNoise;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class PerlinTrack implements Track {

    private int parts = 900;
    private int checkpointDivider = 30;
    private int noiseMax = 8;
    private int halfTrackWidth = 30;
    private int panelWidth;
    private int panelHeight;

    @Getter
    private PVector start;
    @Getter
    private PVector end;

    @Getter
    private List<Boundary> walls;
    @Getter
    private List<Boundary> checkpoints;

    public String getTrackName(){
        return "PerlinTrack";
    }

    protected PerlinTrack(int panelWidth, int panelHeight) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
    }

    @Override
    public void buildTrack() {
        ArrayList<PVector> ptsInner = new ArrayList<>();
        ArrayList<PVector> ptsOuter = new ArrayList<>();
        checkpoints = new ArrayList<>();
        OpenSimplexNoise noise = new OpenSimplexNoise((long) (Math.random() * 1000000));
        for (int i = 0; i < parts; i++) {
            double a = map(i, 0, parts, 0, 2 * Math.PI);
            double xoff = map(Math.cos(a), -1, 1, 0, noiseMax);
            double yoff = map(Math.sin(a), -1, 1, 0, noiseMax);
            double r = map(noise.eval(xoff, yoff), -1, 1, panelWidth * 0.25, panelWidth * 0.4);
            double innerX = Math.cos(a) * (r - halfTrackWidth) + panelWidth * 0.5;
            double innerY = Math.sin(a) * (r - halfTrackWidth) + panelHeight * 0.5;
            ptsInner.add(new PVector(innerX, innerY));

            double outerX = Math.cos(a) * (r + halfTrackWidth) + panelWidth * 0.5;
            double outerY = Math.sin(a) * (r + halfTrackWidth) + panelHeight * 0.5;
            ptsOuter.add(new PVector(outerX, outerY));
            if (i % checkpointDivider == 0)
                checkpoints.add(new Boundary(innerX, innerY, outerX, outerY));

        }
        walls = new ArrayList<>();
        walls.addAll(Boundary.createBoundaries(ptsInner, true));
        walls.addAll(Boundary.createBoundaries(ptsOuter, true));

        start = checkpoints.get(0).midPoint();
        end = checkpoints.get(checkpoints.size() - 1).midPoint();
    }

    @Override
    public PVector getStartVelocity() {
        Boundary boundary = checkpoints.get(0);
		PVector v = PVector.sub(boundary.getA(), boundary.getB());
		v.rotate(-Math.PI * 0.5);
		v.normalize();
		return v;
    }

    /**
     * taken from Processing
     * 
     * @param value
     * @param inputMin
     * @param inputMax
     * @param outputMin
     * @param outputMax
     * @return
     */
    private double map(double value, double inputMin, double inputMax, double outputMin, double outputMax) {
        return outputMin + (outputMax - outputMin) * ((value - inputMin) / (inputMax - inputMin));
    }

}
