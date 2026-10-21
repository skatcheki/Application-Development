package com.example.app.model;

/** Growth rate: r = b - d (lowercase rates, not the capital counts B and D) */
public class GrowthRate {

    private final double r;

    public GrowthRate(AnimalPopulation population) {
        double b = new BirthRate(population).getValue();
        double d = new DeathRate(population).getValue();
        this.r = b - d;
    }

    public double getValue() {
        return r;
    }
}
