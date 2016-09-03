package edu.nyu.pqs.ps4;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

/**
 * Implementation of the Graph API. Users can :
 * <li> Add a node to the graph </li>
 * <li> Remove a node from the Graph </li>
 * <li> Add edge in-between nodes in the graph </li>
 * <li> Remove edge in-between nodes in the graph </li>
 * <li> Get iterators to perform Depth-first Search and
 * Breadth-first Search on the graph</li>
 * This implementation is of undirected graph and nodes with duplicate values
 * aren't allowed. The weights of the edges are optional. The graph doesn't
 * allows cycles but prevention is done for single node cycles only. Its users
 * responsibility to prevent cycles which involves more than one node.
 * @author Anuj Bora
 * @param <T> Graph is parameterized by type parameter T
 */
public class Graph<T> {
  private Map<T, Node<T>> nodes = new HashMap<T, Node<T>>();
  private Map<Iterator<T>, Boolean> iterators =
      new HashMap<Iterator<T>, Boolean>();

  /**
   * Searches that can be performed on the graph
   */
  public enum SearchType {
    /**
     * Depth-first Search
     */
    DFS,
    /**
     * Breadth-first Search
     */
    BFS
  }

  /**
   * Adds node to the graph
   * @param value of the node to be added
   * @throws IllegalArgumentException if node with given value already exists
   * @throws NullPointerException if value is null
   */
  public boolean addNode(T value) {
    if (value == null) {
      throw new NullPointerException("Value of the node cannot be Null");
    }
    if (nodes.containsKey(value)) {
      throw new IllegalArgumentException("Node with given value "
          + "already exists!)");
    }
    nodes.put(value, new Node<T>(value));
    invalidateIterators();
    return true;
  }

  /**
   * Adds edge between two vertices. Weight is initialized to empty string
   * @param value of the source node of the edge
   * @param value of the destination node of the edge
   */
  public boolean addEdge(T value1, T value2) {
    return addEdge(value1, value2, "");
  }

  /**
   * Adds edge between two vertices with the provided weight
   * @param source of the edge
   * @param destination of the edge
   * @param weight of the edge
   * @throws IllegalArgumentException if single node cycle is detected or
   *          either one or both vertices don't exist
   * @throws NullPointerException if any node is null
   */
  public boolean addEdge(T source, T destination, String weight) {
    if (source == null || destination == null) {
      throw new NullPointerException("Node cannot be null");
    }
    if (weight == null) {
      throw new NullPointerException("Weight cannot be null");
    }
    if (source == destination) {
      throw new IllegalArgumentException("Single Node Cycle cannot be added");
    }
    if ((nodes.get(source) == null) || (nodes.get(destination) == null)) {
      throw new IllegalArgumentException("Vertex does not exist");
    }
    Node<T> node1 = nodes.get(source);
    Node<T> node2 = nodes.get(destination);
    // As it is an undirected graph, edge will be added in both nodes
    boolean addEdgeToNode1 = node1.addEdge(node2, weight);
    boolean addEdgeToNode2 = node2.addEdge(node1, weight);
    invalidateIterators();
    return (addEdgeToNode1 && addEdgeToNode2);
  }

  /**
   * Removes a vertex and its edges from the graph
   * @param value to be removed
   * @return true if vertex was removed
   * @throws IllegalArgumentException if vertex doesn't exists
   */
  public boolean removeNode(T value) {
      if (!nodes.containsKey(value)) {
        throw new IllegalArgumentException("Vertex does not exist");
      }
      Node<T> currNode = nodes.get(value);
      for (Edge<T> e : currNode.getEdges()) {
        Node<T> currnode = e.toNode();
        currnode.removeEdge(currNode);
      }
      nodes.remove(value);
      invalidateIterators();
      return true;
  }

  /**
   * Removes edge between the two nodes
   * @param value of the source node
   * @param value of the destination node
   * @return  true if the edge was removed
   * @throws IllegalArgumentException if either one or both vertices
   *          don't exist
   */
  public boolean removeEdge(T source, T destination) {
    if ((nodes.get(source) == null) || (nodes.get(destination) == null)) {
      throw new IllegalArgumentException("Vertex does not exist");
    }
    Node<T> node1 = nodes.get(source);
    Node<T> node2 = nodes.get(destination);
    // As it is an undirected graph, edge has to be removed from both nodes
    boolean removeEdgeFromNode1 = node1.removeEdge(node2);
    boolean removeEdgeFromNode2 = node2.removeEdge(node1);
    invalidateIterators();
    return (removeEdgeFromNode1 && removeEdgeFromNode2);
  }

  /**
   * Invalidates all existing iterators.
   * This method is called on addition/deletion of edges/nodes.
   * As the graph structure changes on performing those operations, the
   * existing iterators may not provide correct values. So they are
   * invalidated.
   */
  private void invalidateIterators() {
    for (Iterator<T> i : iterators.keySet()) {
      iterators.replace(i, false);
    }
  }

  /**
   * Implementation of the Depth-first Search on the graph
   * User has to provide start node of the search. The search will be
   * performed on all the connected nodes to the provided node. Search will
   * not return nodes which don't have a path from provided node.
   */
  private class DFSIterator implements Iterator<T> {
    private Stack<Node<T>> stack = new Stack<Node<T>>();
    private Map<T, Boolean> visited = new HashMap<T, Boolean>();

    public DFSIterator(Node<T> node) {
      stack.push(node);
      visited.put(node.getValue(), true);
    }

    /**
     * {@inheritDoc}
     * @throws IllegalStateException if structure of graph has changed since
     *           this iterator was provided
     * @throws NoSuchElementException if no next element exists
     */
    @Override
    public T next() {
      if (iterators.get(this) == false) {
        throw new IllegalStateException("Nodes/Edges have been added or"
            + "deleted in the graph. Request a new iterator!");
      }
      if (stack.size() == 0) {
        throw new NoSuchElementException("No next element exists");
      }
      Node<T> currNode = stack.pop();
      for (Edge<T> e : currNode.getEdges()) {
        if (visited.get(e.toNode().getValue()) == null) {
          visited.put(e.toNode().getValue(), true);
          stack.add(e.toNode());
        }
      }
      return currNode.getValue();
    }

    /**
     * {@inheritDoc}
     * @throws IllegalStateException if structure of graph has changed since
     *           this iterator was provided
     * @return true if next element exists
     */
    @Override
    public boolean hasNext() {
      if (iterators.get(this) == false) {
        throw new IllegalStateException("Nodes/Edges have been added or"
            + "deleted in the graph. Request a new iterator!");
      }
      if (stack.size() != 0) {
        return true;
      }
      return false;
    }
  }

  /**
   * Implementation of the Breadth-first Search on the graph
   * User has to provide start node of the search. The search will be
   * performed on all the connected nodes to the provided node. Search will
   * not return nodes which don't have a path from provided node.
   */
  private class BFSIterator implements Iterator<T> {
    private Queue<Node<T>> queue = new LinkedList<Node<T>>();
    private Map<T, Boolean> visited = new HashMap<T, Boolean>();

    public BFSIterator(Node<T> node) {
      queue.add(node);
      visited.put(node.getValue(), true);
    }

    /**
     * {@inheritDoc}
     * @throws IllegalStateException if structure of graph has changed since
     *           this iterator was provided
     * @throws NoSuchElementException if no next element exists
     */
    @Override
    public T next() {
      if (iterators.get(this) == false) {
        throw new IllegalStateException("Nodes/Edges have been added or"
            + "deleted in the graph.");
      }
      if (queue.size() == 0) {
        throw new NoSuchElementException("No more Elements in the set");
      }
      Node<T> currNode = queue.poll();
      for (Edge<T> e : currNode.getEdges()) {
        if (visited.get(e.toNode().getValue()) == null) {
          visited.put(e.toNode().getValue(), true);
          queue.add(e.toNode());
        }
      }
      return currNode.getValue();
    }

    /**
     * {@inheritDoc}
     * @throws IllegalStateException if structure of graph has changed since
     *           this iterator was provided
     * @return true if next element exists
     */
    @Override
    public boolean hasNext() {
      if (iterators.get(this) == false) {
        throw new IllegalStateException("Nodes/Edges have been added or"
            + "deleted in the graph.");
      }
      if (queue.size() != 0) {
        return true;
      }
      return false;
    }
  }

  /**
   * Returns specified iterator
   * @param value of the node from which the search should be started
   * @param Search Type (BFS or DFS)
   * @return requested iterator for the graph
   * @throws IllegalArgumentException if node doesn't exists
   */
  public Iterator<T> getIterator(T value, SearchType s) {
    if (nodes.get(value) == null) {
      throw new IllegalArgumentException("Vertex doesn't Exists");
    }
    Iterator<T> iterator;
    if (s == SearchType.BFS) {
      iterator = new BFSIterator(nodes.get(value));
    } else {
      iterator = new DFSIterator(nodes.get(value));
    }
    iterators.put(iterator, true);
    return iterator;
  }

  /**
   * Returns number of nodes in the graph.
   * <b>Used for testing only. Not a part of public API.</b>
   * @return number of nodes in the graph
   */
  int getNodeCount() {
    return nodes.keySet().size();
  }

  /**
   * Returns number of edges to a node in the graph.
   * <b>Used for testing only. Not a part of public API.</b>
   * @param value of the node
   * @return number of edges to a nodes in the graph
   */
  int getNodeEdgeCount(T value) {
    return nodes.get(value).getEdges().size();
  }

  @Override
  public String toString() {
    return "Graph [Nodes = " + nodes + "]";
  }
}