package de.meldanor.davis.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Vector2d;

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

        double area = width * height;
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
            vert.pos = new Vector2d(x, y);
            vert.disp = new Vector2d(0.0, 0.0);
            vertices.add(vert);
        }
    }

    private double fa(double x) {
        return (x * x) / k;
    }

    private double fr(double z) {
        return (k * k) / z;
    }

    public class GraphVertice {

        Vector2d pos;
        Vector2d disp;
        int vertice;

        @Override
        public String toString() {
            return vertice + "";
        }
    }

    public List<GraphVertice> start(int iterations, int temperature) {
        this.initVertices();
        if (iterations == 0)
            return vertices;

        int t = temperature;
        int tTick = temperature / iterations;

        for (int i = 1; i <= iterations; ++i) {
            // calculate repulsive forces
            for (GraphVertice v : vertices) {
                // each vertex has two vectors: .pos and .disp
                v.disp = new Vector2d(0, 0);
                for (GraphVertice u : vertices) {
                    if (u != v) {
                        Vector2d difference = new Vector2d(0, 0);
                        difference.sub(v.pos, u.pos);
                        double fr = fr(difference.length());
                        difference.normalize();
                        difference.scale(fr);

                        v.disp.add(v.disp, difference);
                    }
                }
            }

            // calculate attractive forces
            for (Edge e : edges) {
                GraphVertice v = vertices.get(e.getStart());
                GraphVertice u = vertices.get(e.getEnd());

                Vector2d difference = new Vector2d();
                difference.sub(v.pos, u.pos);

                double fa = fa(difference.length());
                difference.normalize();
                difference.scale(fa);

                v.disp.sub(v.disp, difference);
                u.disp.add(u.disp, difference);
            }

            // limit the maximum displacement of the temperature t
            // and then prevent from being displaced outside frame
            for (GraphVertice v : vertices) {
                double min = Math.min(v.disp.length(), t);
                v.disp.normalize();
                v.disp.scale(min);
                v.pos.add(v.pos, v.disp);
                v.pos.x = Math.min(W / 2, Math.max(-W / 2, v.pos.x));
                v.pos.y = Math.min(L / 2, Math.max(-L / 2, v.pos.y));
            }
            // reduce the temperature
            t = t - tTick;
        }

        return vertices;
    }
}
