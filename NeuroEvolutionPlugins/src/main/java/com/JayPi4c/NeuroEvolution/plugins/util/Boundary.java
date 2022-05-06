package com.JayPi4c.NeuroEvolution.plugins.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a boundary on the track.
 * 
 * @author JayPi4c
 */
public class Boundary {

    private PVector a;
    private PVector b;

    public Boundary(double x1, double y1, double x2, double y2) {
        a = new PVector(x1, y1);
        b = new PVector(x2, y2);
    }

    public Boundary(PVector a, PVector b) {
        this.a = a;
        this.b = b;
    }

    public PVector midPoint() {
        return new PVector((a.x + b.x) * 0.5, (a.y + b.y) * 0.5);
    }

    public static List<Boundary> createBoundaries(List<PVector> pts, boolean closed) {
        List<Boundary> boundaries = new ArrayList<>();

        for (int i = 0; i < pts.size() - (closed ? 0 : 1); i++) {
            PVector p1 = pts.get(i);
            PVector p2 = pts.get((i + 1) % pts.size());
            boundaries.add(new Boundary(p1.x, p1.y, p2.x, p2.y));
        }
        return boundaries;
    }

    public PVector getA() {
        return a;
    }

    public PVector getB() {
        return b;
    }

}