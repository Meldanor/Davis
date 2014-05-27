package de.meldanor.davis.gui;

import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import de.meldanor.davis.datastructure.Graph;
import de.meldanor.davis.datastructure.Graph.Edge;
import de.meldanor.davis.gui.FruchtermannReingoldAlgorithm.GraphVertice;

public class GraphCanvas extends Canvas {

    private Font standardFont;

    public GraphCanvas(int x, int y) {
        super(x, y);
        standardFont = new Font("Sans Serif", 24);
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefHeight(double width) {
        return getWidth();
    }

    @Override
    public double prefWidth(double height) {
        return getHeight();
    }

    public void draw(Graph graph, int iterations, int temperature) {

        GraphicsContext g = this.getGraphicsContext2D();
        g.clearRect(0, 0, getWidth(), getHeight());
        g.setFont(standardFont);
        g.setFill(Color.RED);

        FruchtermannReingoldAlgorithm al = new FruchtermannReingoldAlgorithm(graph, getWidth() - 100, getHeight() - 100);
        List<GraphVertice> vertices = al.start(iterations, temperature);

        for (GraphVertice graphVertice : vertices) {
            if (Double.isNaN(graphVertice.pos.getX()) || Double.isNaN(graphVertice.pos.getX())) {
                g.fillText("Calculator error!", (getWidth() / 2) - 80, getHeight() / 2);
                return;
            }
        }

        for (GraphVertice vertice : vertices) {
            drawVertice(g, vertice, vertices, graph);
        }
    }

    private void drawVertice(GraphicsContext g, GraphVertice vertice, List<GraphVertice> allVertices, Graph graph) {
        int circleSize = 20;
        List<Edge> edges = graph.getEdges(vertice.vertice);
        g.setStroke(Color.BLACK);
        for (Edge edge : edges) {
            GraphVertice end = allVertices.get(edge.getEnd());
            g.strokeLine(vertice.pos.getX() + (circleSize / 2), vertice.pos.getY() + (circleSize / 2), end.pos.getX() + (circleSize / 2), end.pos.getY() + (circleSize / 2));
        }
        g.setStroke(Color.RED);
        g.fillOval(vertice.pos.getX(), vertice.pos.getY(), circleSize, circleSize);

    }

}
