package com.example.app.model;

/**
 * A population of animals at time t.
 * t - time, N - total number of animals, B - number of births, D - number of deaths.
 */
public abstract class AnimalPopulation {

    private final int time;
    private final int totalN;
    private final int birthsB;
    private final int deathsD;

    protected AnimalPopulation(int time, int totalN, int birthsB, int deathsD) {
        if (time < 0) {
            throw new IllegalArgumentException("t must not be negative");
        }
        if (totalN <= 0) {
            throw new IllegalArgumentException("N must be greater than 0");
        }
        if (birthsB < 0 || deathsD < 0) {
            throw new IllegalArgumentException("B and D must not be negative");
        }
        this.time = time;
        this.totalN = totalN;
        this.birthsB = birthsB;
        this.deathsD = deathsD;
    }

    public int getTime() {
        return time;
    }

    public int getTotalN() {
        return totalN;
    }

    public int getBirthsB() {
        return birthsB;
    }

    public int getDeathsD() {
        return deathsD;
    }

    public BirthRate birthRate() {
        return new BirthRate(this);
    }

    public DeathRate deathRate() {
        return new DeathRate(this);
    }

    public GrowthRate growthRate() {
        return new GrowthRate(this);
    }
}
