package com.JayPi4c.NeuroEvolution.plugins.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class BoundaryTest {

    @Test
    void constructorTest() {
        Boundary boundary = new Boundary(0, 0, 1, 1);
        assertEquals(boundary.getA(), new PVector(0, 0));
        assertEquals(boundary.getB(), new PVector(1, 1));

        boundary = new Boundary(new PVector(0, 0), new PVector(1, 1));
        assertEquals(boundary.getA(), new PVector(0, 0));
        assertEquals(boundary.getB(), new PVector(1, 1));

        assertThrows(IllegalArgumentException.class, () -> new Boundary(new PVector(0, 0), null));
        assertThrows(IllegalArgumentException.class, () -> new Boundary(null, new PVector(1, 1)));
        assertThrows(IllegalArgumentException.class, () -> new Boundary(null, null));
    }

    @Test
    void midPointTest() {
        Boundary boundary = new Boundary(0, 0, 1, 1);
        assertEquals(boundary.midPoint(), new PVector(0.5, 0.5));

        boundary = new Boundary(new PVector(-1, -1), new PVector(0.5, 0.5));
        assertEquals(boundary.midPoint(), new PVector(-0.25, -0.25));

        boundary = new Boundary(new PVector(0.5, 0.5), new PVector(-1, -1));
        assertEquals(boundary.midPoint(), new PVector(-0.25, -0.25));
    }

    @Test
    void createBoundariesTest(){
        List<PVector> points = new ArrayList<>();
        points.add(new PVector(0, 0));
        points.add(new PVector(1, 0));
        points.add(new PVector(1, 1));

        List<Boundary> boundaries = Boundary.createBoundaries(points, false);
        assertEquals(2, boundaries.size());
        assertEquals(new PVector(0, 0), boundaries.get(0).getA());
        assertEquals(new PVector(1, 0), boundaries.get(0).getB());
        assertEquals(new PVector(1, 0), boundaries.get(1).getA());
        assertEquals(new PVector(1, 1), boundaries.get(1).getB());


        boundaries = Boundary.createBoundaries(points, true);
        assertEquals(3, boundaries.size());
        assertEquals(new PVector(0, 0), boundaries.get(0).getA());
        assertEquals(new PVector(1, 0), boundaries.get(0).getB());
        assertEquals(new PVector(1, 0), boundaries.get(1).getA());
        assertEquals(new PVector(1, 1), boundaries.get(1).getB());
        assertEquals(new PVector(1, 1), boundaries.get(2).getA());
        assertEquals(new PVector(0, 0), boundaries.get(2).getB());
    }

}
