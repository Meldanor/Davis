package de.meldanor.davis.gui;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import de.meldanor.davis.datastructure.Graph;

public class MainGUI extends Application {

    private Slider nodesSlider;
    private Slider iterationsSlider;
    private Slider temperatureSlider;
    private GraphCanvas canvas;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Davis");

        BorderPane bPane = new BorderPane();
        Pane p = new Pane();
        

        canvas = new GraphCanvas(500, 500);
        canvas.widthProperty().bind(p.widthProperty());
        canvas.heightProperty().bind(p.heightProperty());
        p.getChildren().add(canvas);
        bPane.setCenter(p);

        VBox controlPane = new VBox(20.0);
        controlPane.setPadding(new Insets(100, 20, 10, 10));

        Label sliderLabel = new Label("Nodes");
        controlPane.getChildren().add(sliderLabel);
        this.nodesSlider = new Slider(10, 100, 10);
        this.nodesSlider.setMinorTickCount(5);
        this.nodesSlider.setMajorTickUnit(25);
        this.nodesSlider.setShowTickLabels(true);
        this.nodesSlider.setShowTickMarks(true);
        controlPane.getChildren().add(nodesSlider);

        Label iterationsLabel = new Label("Iterations");
        controlPane.getChildren().add(iterationsLabel);
        this.iterationsSlider = new Slider(0, 10, 1);
        this.iterationsSlider.setMinorTickCount(1);
        this.iterationsSlider.setMajorTickUnit(1);
        this.iterationsSlider.setShowTickLabels(true);
        this.iterationsSlider.setShowTickMarks(true);
        controlPane.getChildren().add(iterationsSlider);
        
        Label temperatureLabel = new Label("Temperature");
        controlPane.getChildren().add(temperatureLabel);
        this.temperatureSlider = new Slider(10, 500, 100);
        this.temperatureSlider.setMinorTickCount(10);
        this.temperatureSlider.setMajorTickUnit(100);
        this.temperatureSlider.setShowTickLabels(true);
        this.temperatureSlider.setShowTickMarks(true);
        controlPane.getChildren().add(temperatureSlider);

        Button startButton = new Button("Generate");
        startButton.setOnAction(e -> generateGraphView());
        controlPane.getChildren().add(startButton);

        bPane.setRight(controlPane);

        Scene scene = new Scene(bPane);
        stage.setScene(scene);
        stage.show();
    }

    private void generateGraphView() {
        Graph graph = generateRandomGraph();
        canvas.draw(graph, (int) iterationsSlider.getValue(), (int)temperatureSlider.getValue());
    }

    private Graph generateRandomGraph() {

        int size = (int) nodesSlider.getValue();
        Graph g = new Graph(size);

        Random rand = new Random();
        for (int i = 0; i < size; ++i) {
            int edges = rand.nextInt(size / 3);
            Set<Integer> used = new HashSet<Integer>();
            for (int j = 0; j < edges; ++j) {
                int pair = 0;
                do {
                    pair = rand.nextInt(size);
                } while (used.contains(pair));
                g.addEdge(i, pair);
            }
        }

        return g;
    }

}
