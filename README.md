# Animal Population Dynamics

A JavaFX application that models and visualizes population dynamics of **Deer**, **Horses** and **Cattle** over time.

## Mathematical model

| Symbol | Meaning |
|--------|---------|
| `t` | time |
| `N` | total number of animals |
| `B` | number of births (count) |
| `D` | number of deaths (count) |
| `b` | birth rate |
| `d` | death rate |
| `r` | growth rate |

Note that capital `B` and `D` are counts, while lowercase `b` and `d` are rates.

- **Birth rate:** `b = B / N`
- **Death rate:** `d = D / N`
- **Growth rate:** `r = b - d`
- **Exponential growth:** `dN/dt = (b - d) * N = r * N`, whose solution is

  `N(t) = N0 * e^(r * t)`

  where `N0` is the starting number of animals.

## Visualization

Each animal type has its own tab with two graphs:

1. **Exponential growth** – the curve `N(t) = N0 * e^(r * t)`, using `N0` and `r` from the first time step (`t = 0`).
2. **Rates over time** – `b`, `d` and `r` plotted at each time step `t`.

The application uses sample data for `t = 0..9`. The population changes between steps as `N(t+1) = N(t) + B(t) - D(t)`.

## Project structure

```
src/main/java/
├── module-info.java
└── com/example/app/
    ├── App.java                    # JavaFX application, sample data and charts
    └── model/
        ├── AnimalPopulation.java   # base class: t, N, B, D
        ├── Deer.java
        ├── Horses.java
        ├── Cattle.java
        ├── BirthRate.java          # b = B / N
        ├── DeathRate.java          # d = D / N
        ├── GrowthRate.java         # r = b - d
        └── ExponentialGrowth.java  # N(t) = N0 * e^(r * t)
```

## Usage

```java
Deer deer = new Deer(0, 200, 30, 10);   // t, N, B, D
deer.birthRate().getValue();            // b = 0.15
deer.deathRate().getValue();            // d = 0.05
deer.growthRate().getValue();           // r = 0.10

new ExponentialGrowth(deer).populationAt(5);  // N(5) = 200 * e^(0.1 * 5)
```

## Requirements

- Java 25
- Maven 3.9+
- JavaFX 25 (downloaded automatically by Maven)

## Build and run

```
mvn compile
mvn javafx:run
```
