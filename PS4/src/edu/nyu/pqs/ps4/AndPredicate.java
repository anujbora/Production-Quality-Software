package edu.nyu.pqs.ps4;

/**
 * And Predicate implementation of the Predicate interface
 * A value is considered valid if it passes in both predicates
 * which are provided
 * @author Anuj Bora
 * @param <T> AndPredicate is parameterized by type parameter T
 */
public class AndPredicate<T> implements Predicate<T> {
  Predicate<T> first;
  Predicate<T> second;

  public AndPredicate(Predicate<T> first, Predicate<T> second) {
    this.first = first;
    this.second = second;
  }

  @Override
  public boolean accept(T item) {
    return (first.accept(item) && second.accept(item));
  }
}