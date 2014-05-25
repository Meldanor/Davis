package de.meldanor.davis.datastructure;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private List<List<Integer>> adj;
    private int vertices;
    private int edges;

    public Graph() {
        this(8);
    }

    public Graph(int size) {
        this.vertices = size;
        this.edges = 0;
        adj = new ArrayList<List<Integer>>(vertices);
        for (int i = 0; i < vertices; ++i)
            adj.add(new ArrayList<Integer>(vertices));
    }

    public void addEdge(int v, int w) {
        adj.get(v).add(w);
        adj.get(w).add(v);
        ++edges;
    }

    public List<Edge> getEdges(int vertice) {
        if (vertice > vertices)
            throw new IllegalArgumentException("Vertice out of bound! Vertice: " + vertice + "Current Vertices:" + vertices);

        List<Edge> result = new ArrayList<Edge>(adj.get(vertice).size());
        for (Integer end : adj.get(vertice)) {
            result.add(new Edge(vertice, end));
        }

        return result;

    }
    
    public List<Edge> getAllEdges() {
        List<Edge> result = new ArrayList<>();
        for(int i = 0 ; i < vertices; ++i) {
            List<Edge> ed = getEdges(i);
            for (Edge edge : ed) {
                if (!result.contains(edge))
                    result.add(edge);
            }
            
        }
        return result;
    }

    public int E() {
        return edges;
    }

    public int V() {
        return vertices;
    }

    public class Edge {
        private int start;
        private int end;

        protected Edge(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getEnd() {
            return end;
        }

        public int getStart() {
            return start;
        }

        @Override
        public String toString() {
            return "(" + start + "," + end + ")";
        }
        
        public boolean equals(Object obj) {
            if (!(obj instanceof Edge))
                return false;
            if (this == obj)
                return true;
            Edge e = (Edge)obj;
            return (start == e.start && end == e.end) || (start == e.end && end == e.start);
        }
    }

}
