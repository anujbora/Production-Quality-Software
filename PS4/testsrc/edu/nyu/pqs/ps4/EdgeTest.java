package edu.nyu.pqs.ps4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

public class EdgeTest {
  private Node<String> node1, node2;
  private Edge<String> edge;

  @Before
  public void setUp() {
    node1 = new Node<String>("Manhattan");
    node2 = new Node<String>("Brooklyn");
    edge = new Edge<String>(node1, node2, "20");
  }

  @Test
  public void testGetWeight() {
    assertEquals(edge.getWeight(), "20");
  }

  @Test
  public void testSetWeight() {
    edge.setWeight("40");
    assertEquals(edge.getWeight(), "40");
  }

  @Test
  public void testHasEdge_EdgeExists() {
    assertTrue(edge.hasEdge(node1, node2));
  }

  @Test
  public void testHasEdge_EdgeNotExists() {
    Node<String> node3 = new Node<String>("Bronx");
    assertFalse(edge.hasEdge(node1, node3));
  }

  @Test
  public void testFromNode() {
    assertEquals(edge.fromNode(), node1);
  }

  @Test
  public void testToNode() {
    assertEquals(edge.toNode(), node2);
  }

  @Test
  public void testToString() {
    String expectedResult = "Edge [First Node = Node [Value = Manhattan], "
        + "Second Node = Node [Value = Brooklyn], Weight = 20]";
    assertEquals(edge.toString(), expectedResult);
  }
}