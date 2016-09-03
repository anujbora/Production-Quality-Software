package edu.nyu.pqs.ps4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class NodeTest {
  private Node<String> node;

  @Before
  public void setUp() {
    node = new Node<String>("Manhattan");
  }

  @Test
  public void testCreateNode() {
    assertEquals(node.getValue(), "Manhattan");
  }

  @Test
  public void testAddEdge() {
    assertEquals(node.getEdges().size(), 0);
    Node<String> node2 = new Node<String>("Brooklyn");
    assertTrue(node.addEdge(node2, "20"));
    assertEquals(node.getEdges().size(), 1);
  }

  @Test
  public void testAddExistingEdge() {
    assertEquals(node.getEdges().size(), 0);
    Node<String> node2 = new Node<String>("Brooklyn");
    assertTrue(node.addEdge(node2, "20"));
    assertEquals(node.getEdges().size(), 1);
    assertEquals(node.getEdges().get(0).getWeight(), "20");
    assertTrue(node.addEdge(node2, "40"));
    assertEquals(node.getEdges().size(), 1);
    assertEquals(node.getEdges().get(0).getWeight(), "40");
  }

  @Test
  public void testRemoveEdge_EdgeExists() {
    assertEquals(node.getEdges().size(), 0);
    Node<String> node2 = new Node<String>("Brooklyn");
    assertTrue(node.addEdge(node2, "20"));
    assertEquals(node.getEdges().size(), 1);
    assertTrue(node.removeEdge(node2));
    assertEquals(node.getEdges().size(), 0);
  }

  public void testRemoveEdge_EdgeNotExists() {
    Node<String> node2 = new Node<String>("Brooklyn");
    assertEquals(node.getEdges().size(), 0);
    assertFalse(node.removeEdge(node2));
  }

  @Test
  public void testToString() {
    String expectedResult = "Node [Value = Manhattan]";
    assertEquals(node.toString(), expectedResult);
  }
}