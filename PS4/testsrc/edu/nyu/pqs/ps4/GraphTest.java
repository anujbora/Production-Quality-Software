package edu.nyu.pqs.ps4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import edu.nyu.pqs.ps4.Graph.SearchType;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

public class GraphTest {
  Graph<String> graph;

  @Before
  public void setUp() {
    graph = new Graph<String>();
  }

  @Test
  public void testAddNode() {
    assertTrue(graph.addNode("Manhattan"));
    assertEquals(graph.getNodeCount(), 1);
  }

  @Test(expected = NullPointerException.class)
  public void testAddNode_Null() {
    graph.addNode(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNode_AlreadyExists() {
    assertTrue(graph.addNode("Manhattan"));
    assertEquals(graph.getNodeCount(), 1);
    graph.addNode("Manhattan");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddEdge_NodeNotExist() {
    graph.addEdge("Manhattan", "Brooklyn");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddEdge_NullWeight() {
    assertTrue(graph.addNode("Manhattan"));
    assertTrue(graph.addNode("Manhattan"));
    graph.addEdge("Manhattan", "Brooklyn", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddEdge_SingleNodeCycle() {
    assertTrue(graph.addNode("Manhattan"));
    assertTrue(graph.addNode("Manhattan"));
    graph.addEdge("Manhattan", "Manhattan");
  }

  @Test
  public void testAddEdge() {
    assertTrue(graph.addNode("Manhattan"));
    assertTrue(graph.addNode("Brooklyn"));
    assertTrue(graph.addEdge("Manhattan", "Brooklyn"));
  }

  @Test(expected = NullPointerException.class)
  public void testAddEdge_Null() {
    graph.addEdge("Manhattan", null);
  }

  @Test
  public void testRemoveNode() {
    assertTrue(graph.addNode("Manhattan"));
    assertEquals(graph.getNodeCount(), 1);
    assertTrue(graph.removeNode("Manhattan"));
    assertEquals(graph.getNodeCount(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNode_NodeDoesntExists() {
    graph.removeNode("Manhattan");
  }

  @Test
  public void testRemoveNode_ValidateEdgeCount() {
    assertTrue(graph.addNode("Manhattan"));
    assertTrue(graph.addNode("Brooklyn"));
    assertTrue(graph.addNode("Bronx"));
    assertTrue(graph.addEdge("Manhattan", "Brooklyn"));
    assertTrue(graph.addEdge("Manhattan", "Bronx"));
    assertEquals(graph.getNodeEdgeCount("Brooklyn"), 1);
    assertEquals(graph.getNodeEdgeCount("Bronx"), 1);
    assertEquals(graph.getNodeEdgeCount("Manhattan"), 2);
    assertEquals(graph.getNodeCount(), 3);
    assertTrue(graph.removeNode("Manhattan"));
    assertEquals(graph.getNodeEdgeCount("Brooklyn"), 0);
    assertEquals(graph.getNodeEdgeCount("Bronx"), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveEdge_EdgeDoesntExists() {
    graph.removeEdge("Manhattan", "Brooklyn");
  }

  @Test
  public void testRemoveEdge() {
    assertTrue(graph.addNode("Manhattan"));
    assertTrue(graph.addNode("Brooklyn"));
    assertTrue(graph.addEdge("Manhattan", "Brooklyn"));
    assertEquals(graph.getNodeEdgeCount("Manhattan"), 1);
    assertEquals(graph.getNodeEdgeCount("Brooklyn"), 1);
    assertTrue(graph.removeEdge("Manhattan", "Brooklyn"));
    assertEquals(graph.getNodeEdgeCount("Manhattan"), 0);
    assertEquals(graph.getNodeEdgeCount("Brooklyn"), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetIterator_InvalidNode() {
    graph.getIterator("Connecticut", SearchType.BFS);
  }

  @Test
  public void testBFS() {
    assertTrue(graph.addNode("Manhattan"));
    assertTrue(graph.addNode("Brooklyn"));
    assertTrue(graph.addNode("Bronx"));
    assertTrue(graph.addNode("Queens"));
    assertTrue(graph.addNode("Staten Island"));
    assertTrue(graph.addEdge("Manhattan", "Brooklyn"));
    assertTrue(graph.addEdge("Manhattan", "Bronx"));
    assertTrue(graph.addEdge("Manhattan", "Queens"));
    assertTrue(graph.addEdge("Brooklyn", "Staten Island"));

    Iterator<String> iterator = graph.getIterator("Manhattan", SearchType.BFS);

    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Manhattan");
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Brooklyn");
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Bronx");
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Queens");
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Staten Island");
    assertFalse(iterator.hasNext());
  }

  @Test
  public void testDFS() {
    assertTrue(graph.addNode("Manhattan"));
    assertTrue(graph.addNode("Brooklyn"));
    assertTrue(graph.addNode("Bronx"));
    assertTrue(graph.addNode("Queens"));
    assertTrue(graph.addNode("Staten Island"));
    assertTrue(graph.addEdge("Manhattan", "Brooklyn"));
    assertTrue(graph.addEdge("Manhattan", "Bronx"));
    assertTrue(graph.addEdge("Manhattan", "Queens"));
    assertTrue(graph.addEdge("Brooklyn", "Staten Island"));

    Iterator<String> iterator = graph.getIterator("Manhattan", SearchType.DFS);

    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Manhattan");
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Queens");
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Bronx");
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Brooklyn");
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Staten Island");
    assertFalse(iterator.hasNext());
  }

  @Test(expected = IllegalStateException.class)
  public void testDFS_InconsistentGraph() {
    assertTrue(graph.addNode("Manhattan"));
    assertTrue(graph.addNode("Brooklyn"));
    assertTrue(graph.addNode("Bronx"));
    assertTrue(graph.addNode("Queens"));
    assertTrue(graph.addNode("Staten Island"));
    assertTrue(graph.addEdge("Manhattan", "Brooklyn"));
    assertTrue(graph.addEdge("Manhattan", "Bronx"));
    assertTrue(graph.addEdge("Manhattan", "Queens"));
    assertTrue(graph.addEdge("Brooklyn", "Staten Island"));

    Iterator<String> iterator = graph.getIterator("Manhattan", SearchType.DFS);

    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Manhattan");
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Queens");
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Bronx");
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Brooklyn");
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Staten Island");
    assertTrue(graph.addNode("New borough"));
    assertFalse(iterator.hasNext());
  }

  @Test(expected = IllegalStateException.class)
  public void testBFS_InconsistentGraph() {
    assertTrue(graph.addNode("Manhattan"));
    assertTrue(graph.addNode("Brooklyn"));
    assertTrue(graph.addNode("Bronx"));
    assertTrue(graph.addNode("Queens"));
    assertTrue(graph.addNode("Staten Island"));
    assertTrue(graph.addEdge("Manhattan", "Brooklyn"));
    assertTrue(graph.addEdge("Manhattan", "Bronx"));
    assertTrue(graph.addEdge("Manhattan", "Queens"));
    assertTrue(graph.addEdge("Brooklyn", "Staten Island"));

    Iterator<String> iterator = graph.getIterator("Manhattan", SearchType.BFS);

    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Manhattan");
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Brooklyn");
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Bronx");
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Queens");
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Staten Island");
    assertTrue(graph.addNode("New borough"));
    assertFalse(iterator.hasNext());
  }

  @Test
  public void testToString() {
    assertTrue(graph.addNode("Manhattan"));
    String expectedResult = "Graph [Nodes = "
        + "{Manhattan=Node [Value = Manhattan]}]";
    assertEquals(graph.toString(), expectedResult);
  }
}