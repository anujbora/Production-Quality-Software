package edu.nyu.pqs.ps4;

/**
 * Represents an edge in a graph.
 * @author Anuj Bora
 * @param <T> Edge is parameterized by type parameter T
 */
class Edge<T> {
  private Node<T> node1;
  private Node<T> node2;
  private String weight;

  Edge(Node<T> node1, Node<T> node2, String weight) {
    this.node1 = node1;
    this.node2 = node2;
    this.weight = weight;
  }

  Node<T> fromNode() {
    return node1;
  }

  Node<T> toNode() {
    return node2;
  }

  /**
   * Returns true if edge exists in-between two nodes
   * @param source node
   * @param destination node
   * @return true if edge exists in-between two nodes
   */
  boolean hasEdge(Node<T> node1, Node<T> node2) {
    return (this.node1 == node1 && this.node2 == node2);
  }

  String getWeight() {
    return weight;
  }

  void setWeight(String weight) {
    this.weight = weight;
  }

  @Override
  public String toString() {
    return "Edge [First Node = " + node1 + ", Second Node = " + node2 + ", "
        + "Weight = " + weight + "]";
  }
}