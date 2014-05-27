package de.meldanor.davis.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.geometry.Point2D;
import de.meldanor.davis.datastructure.Graph;
import de.meldanor.davis.datastructure.Graph.Edge;

public class FruchtermannReingoldAlgorithm {

    private double W;
    private double L;
    private Graph graph;

    private List<GraphVertice> vertices;
    private List<Edge> edges;

    private double k;

    public FruchtermannReingoldAlgorithm(Graph graph, double width, double height) {
        this.graph = graph;
        this.edges = graph.getAllEdges();
        this.W = width;
        this.L = height;

        double area = W * L;
        this.k = Math.sqrt((area / graph.V()));
    }

    private void initVertices() {

        this.vertices = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < graph.V(); ++i) {
            GraphVertice vert = new GraphVertice();
            vert.vertice = i;

            double x = rand.nextInt((int) W) + 1;
            double y = rand.nextInt((int) L) + 1;
            vert.pos = new Point2D(x, y);
            vert.disp = new Point2D(0.0, 0.0);
            vertices.add(vert);
        }
    }

    private double fa(double x) {
        return (x * x) / k;
    }

    private double fr(double z) {
        if (z == 0.0) {
            throw new IllegalArgumentException("Divide by zero!");
        }
        return (k * k) / z;
    }

    public class GraphVertice {

        Point2D pos;
        Point2D disp;
        int vertice;

        @Override
        public String toString() {
            return "GraphVertice [pos=" + pos + ", disp=" + disp + ", vertice=" + vertice + "]";
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this)
                return true;
            if (obj == null || !(obj instanceof GraphVertice))
                return false;
            return ((GraphVertice) obj).vertice == vertice;
        }
    }

    public List<GraphVertice> start(int iterations, int temperature) {
        this.initVertices();
        if (iterations == 0)
            return vertices;

        int t = temperature;
        int tTick = temperature / iterations;

        for (int i = 1; i <= 1000; ++i) {
            // calculate repulsive forces
            for (GraphVertice v : vertices) {
                // each vertex has two vectors: .pos and .disp
                v.disp = new Point2D(0, 0);
                for (GraphVertice u : vertices) {
                    if (!v.equals(u)) {
                        Point2D difference = v.pos.subtract(u.pos);
                        if (difference.magnitude() == 0) {
                            continue;
                        }
                        double fr = 0.0;
                        try {
                            fr = fr(difference.magnitude());
                        } catch (Exception e) {
                            System.out.println("Iteration: " + i);
                            System.out.println("V: " + v + ";U: " + u);
                            System.out.println(vertices);
                            throw e;
                        }
                        difference = difference.normalize();
                        difference = difference.multiply(fr);

                        v.disp = v.disp.add(difference);
                    }
                }
            }

            // calculate attractive forces
            for (Edge e : edges) {
                GraphVertice v = vertices.get(e.getStart());
                GraphVertice u = vertices.get(e.getEnd());

                Point2D difference = v.pos.subtract(u.pos);
                double fa = fa(difference.magnitude());
                difference = difference.normalize();
                difference = difference.multiply(fa);

                v.disp = v.disp.subtract(difference);
                u.disp = u.disp.add(difference);
            }

            // limit the maximum displacement of the temperature t
            // and then prevent from being displaced outside frame
            for (GraphVertice v : vertices) {
                // v.pos := v.pos + ( v. disp/ |v.disp|) * min ( v.disp, t );
                v.pos = v.pos.add(v.pos.normalize().multiply(Math.min(v.disp.magnitude(), t)));
                double newX = Math.min(W / 2, Math.max(-W / 2, v.pos.getX()));
                double newY = Math.min(L / 2, Math.max(-L / 2, v.pos.getY()));
                v.pos = new Point2D(newX, newY);
            }
            // reduce the temperature
            t = t - tTick;
        }

        return vertices;
    }
}
