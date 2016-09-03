package edu.nyu.pqs.ps4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import edu.nyu.pqs.ps4.Graph.SearchType;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FilterIteratorTest {
  Predicate<String> skipM;
  Predicate<String> skipB;
  Predicate<String> skipQ;
  PredicateFactory<String> pf;
  Graph<String> graph;

  @Before
  public void setUp() {
    skipM = new SkipMPredicate();
    skipB = new SkipBPredicate();
    skipQ = new SkipQPredicate();
    pf = new PredicateFactory<String>();
    graph = new Graph<String>();
    graph.addNode("Manhattan");
    graph.addNode("Brooklyn");
    graph.addNode("Bronx");
    graph.addNode("Queens");
    graph.addNode("Staten Island");
    graph.addEdge("Manhattan", "Brooklyn");
    graph.addEdge("Manhattan", "Bronx");
    graph.addEdge("Manhattan", "Queens");
    graph.addEdge("Brooklyn", "Staten Island");
  }

  @Test(expected = NoSuchElementException.class)
  public void testBFS_SinglePredicate_NoNext() {
    Iterator<String> iterator = graph.getIterator("Manhattan", SearchType.BFS);
    iterator = new FilterIterator<String>(iterator, skipM);
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Brooklyn");
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Bronx");
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Queens");
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Staten Island");
    assertFalse(iterator.hasNext());
    iterator.next();
  }

  @Test
  public void testBFS_SinglePredicate() {
    Iterator<String> iterator = graph.getIterator("Manhattan", SearchType.BFS);
    iterator = new FilterIterator<String>(iterator, skipM);
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
  public void testBFS_ANDPredicate() {
    Iterator<String> iterator = graph.getIterator("Manhattan", SearchType.BFS);
    Predicate<String> currPredicate = pf.and(skipM, skipB);
    iterator = new FilterIterator<String>(iterator, currPredicate);
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Queens");
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Staten Island");
    assertFalse(iterator.hasNext());
  }

  @Test
  public void testBFS_ORPredicate() {
    Iterator<String> iterator = graph.getIterator("Manhattan", SearchType.BFS);
    Predicate<String> currPredicate = pf.or(skipM, skipB);
    iterator = new FilterIterator<String>(iterator, currPredicate);
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
  public void testBFS_NestedPredicates() {
    Iterator<String> iterator = graph.getIterator("Manhattan", SearchType.BFS);
    Predicate<String> currPredicate = pf.and(skipM, pf.not(skipQ));
    iterator = new FilterIterator<String>(iterator, currPredicate);
    assertTrue(iterator.hasNext());
    assertEquals(iterator.next(), "Queens");
    assertFalse(iterator.hasNext());
  }
}