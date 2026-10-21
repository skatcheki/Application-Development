package com.example.app.model;

/**
 * Exponential growth model: dN/dt = (b - d) * N = r * N,
 * whose solution is N(t) = N0 * e^(r * t).
 */
public class ExponentialGrowth {

    private final double startN0;
    private final double r;

    public ExponentialGrowth(AnimalPopulation start) {
        this.startN0 = start.getTotalN();
        this.r = start.growthRate().getValue();
    }

    public double getStartN0() {
        return startN0;
    }

    public double getR() {
        return r;
    }

    /** N(t) = N0 * e^(r * t) */
    public double populationAt(double t) {
        return startN0 * Math.exp(r * t);
    }
}
