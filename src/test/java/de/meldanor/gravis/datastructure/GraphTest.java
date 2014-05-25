package de.meldanor.gravis.datastructure;

import static org.junit.Assert.*;

import org.junit.Test;

import de.meldanor.davis.datastructure.Graph;

public class GraphTest {

    @Test
    public void createGraphTest() {
        Graph g = new Graph(10);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);

        assertEquals("[(1,2), (1,3), (1,4)]", g.getEdges(1).toString());
        assertEquals("[(2,1)]", g.getEdges(2).toString());
        assertEquals("[(3,1)]", g.getEdges(3).toString());
        assertEquals("[(4,1)]", g.getEdges(4).toString());
    }

}
