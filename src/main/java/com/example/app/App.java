package com.example.app;

import java.util.ArrayList;
import java.util.List;

import com.example.app.model.AnimalPopulation;
import com.example.app.model.Cattle;
import com.example.app.model.Deer;
import com.example.app.model.ExponentialGrowth;
import com.example.app.model.Horses;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    /** Creates a population of a specific animal for given t, N, B, D. */
    private interface PopulationFactory {
        AnimalPopulation create(int time, int totalN, int birthsB, int deathsD);
    }

    @Override
    public void start(Stage stage) {
        // Sample data: births B and deaths D for each time step t = 0..9
        List<AnimalPopulation> deer = buildTimeline(Deer::new, 200,
                new int[]{30, 34, 28, 40, 36, 25, 22, 30, 38, 35},
                new int[]{10, 12, 15, 14, 20, 26, 24, 18, 16, 19});
        List<AnimalPopulation> horses = buildTimeline(Horses::new, 80,
                new int[]{8, 9, 7, 10, 6, 5, 8, 9, 11, 10},
                new int[]{4, 5, 6, 4, 7, 8, 6, 5, 4, 6});
        List<AnimalPopulation> cattle = buildTimeline(Cattle::new, 500,
                new int[]{60, 55, 70, 65, 50, 45, 58, 62, 66, 64},
                new int[]{30, 35, 32, 40, 48, 50, 42, 38, 36, 40});

        TabPane tabs = new TabPane(
                createTab("Deer", deer),
                createTab("Horses", horses),
                createTab("Cattle", cattle));

        stage.setTitle("Application Development");
        stage.setScene(new Scene(tabs, 900, 800));
        stage.show();
    }

    /** Builds populations over time, where N at t+1 = N + B - D at t. */
    private static List<AnimalPopulation> buildTimeline(PopulationFactory factory, int startN,
                                                        int[] births, int[] deaths) {
        List<AnimalPopulation> timeline = new ArrayList<>();
        int totalN = startN;
        for (int t = 0; t < births.length; t++) {
            timeline.add(factory.create(t, totalN, births[t], deaths[t]));
            totalN = totalN + births[t] - deaths[t];
        }
        return timeline;
    }

    private static Tab createTab(String name, List<AnimalPopulation> timeline) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("t (time)");
        yAxis.setLabel("rate");

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle(name + ": rates over time");

        XYChart.Series<Number, Number> birthSeries = new XYChart.Series<>();
        birthSeries.setName("b = B / N");
        XYChart.Series<Number, Number> deathSeries = new XYChart.Series<>();
        deathSeries.setName("d = D / N");
        XYChart.Series<Number, Number> growthSeries = new XYChart.Series<>();
        growthSeries.setName("r = b - d");

        for (AnimalPopulation population : timeline) {
            int t = population.getTime();
            birthSeries.getData().add(new XYChart.Data<>(t, population.birthRate().getValue()));
            deathSeries.getData().add(new XYChart.Data<>(t, population.deathRate().getValue()));
            growthSeries.getData().add(new XYChart.Data<>(t, population.growthRate().getValue()));
        }
        chart.getData().add(birthSeries);
        chart.getData().add(deathSeries);
        chart.getData().add(growthSeries);

        LineChart<Number, Number> exponentialChart = createExponentialChart(name, timeline);
        VBox content = new VBox(exponentialChart, chart);
        VBox.setVgrow(exponentialChart, Priority.ALWAYS);
        VBox.setVgrow(chart, Priority.ALWAYS);

        Tab tab = new Tab(name, content);
        tab.setClosable(false);
        return tab;
    }

    /** Plots N(t) = N0 * e^(r * t), using N0 and r from the first point of the timeline. */
    private static LineChart<Number, Number> createExponentialChart(String name, List<AnimalPopulation> timeline) {
        ExponentialGrowth growth = new ExponentialGrowth(timeline.getFirst());
        double endT = timeline.getLast().getTime();

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("t (time)");
        yAxis.setLabel("N (animals)");
        yAxis.setForceZeroInRange(false);

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle(String.format("%s: exponential growth, N0 = %.0f, r = %.3f",
                name, growth.getStartN0(), growth.getR()));
        chart.setCreateSymbols(false);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("N(t) = N0 * e^(r * t)");
        for (double t = 0; t <= endT + 1e-9; t += 0.1) {
            series.getData().add(new XYChart.Data<>(t, growth.populationAt(t)));
        }
        chart.getData().add(series);
        return chart;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
