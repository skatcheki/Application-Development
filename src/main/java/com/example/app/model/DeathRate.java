package com.example.app.model;

/** Death rate: d = D / N */
public class DeathRate {

    private final double d;

    public DeathRate(AnimalPopulation population) {
        this.d = (double) population.getDeathsD() / population.getTotalN();
    }

    public double getValue() {
        return d;
    }
}
