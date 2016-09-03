package edu.nyu.pqs.ps4;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node in a graph. It has package-private access.
 * @author Anuj Bora
 * @param <T> Node is parameterized by type parameter T
 */
class Node<T> {
  private T value;
  private List<Edge<T>> edges = new ArrayList<Edge<T>>();

  Node(T node) {
    this.value = node;
  }

  T getValue() {
    return value;
  }

  /**
   * It adds edge in-between two nodes. If edge already exists, only the
   * weight of the edge is updated
   * @param destination node of the edge
   * @param weight of the edge
   * @return true if edge added or updated
   */
  boolean addEdge(Node<T> node, String weight) {
    for (Edge<T> e : edges) {
      if (e.hasEdge(this, node)) {
        e.setWeight(weight);
        return true;
      }
    }
    Edge<T> newEdge = new Edge<T>(this, node, weight);
    return edges.add(newEdge);
  }

  /**
   * It removes an edge in-between two nodes.
   * @param destination node of the edge
   * @return true if edge is removed
   */
  boolean removeEdge(Node<T> node) {
    for (int i = 0; i < edges.size(); i++) {
      if (edges.get(i).toNode() == node) {
        edges.remove(i);
        return true;
      }
    }
    return false;
  }

  /**
   * Returns list of all edges going out from a node
   * @return list of edges going out from the node
   */
  List<Edge<T>> getEdges() {
    List<Edge<T>> allEdges = new ArrayList<Edge<T>>();
    for (Edge<T> e : edges) {
      allEdges.add(e);
    }
    return allEdges;
  }

  @Override
  public String toString() {
    return "Node [Value = " + value + "]";
  }
}