package com.example.app.model;

/** Birth rate: b = B / N */
public class BirthRate {

    private final double b;

    public BirthRate(AnimalPopulation population) {
        this.b = (double) population.getBirthsB() / population.getTotalN();
    }

    public double getValue() {
        return b;
    }
}
