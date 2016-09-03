package edu.nyu.pqs.ps4;

/**
 * Or Predicate implementation of the Predicate interface
 * A value is considered valid if it passes any one or both of the provided
 * predicates.
 * @author Anuj Bora
 * @param <T> OrPredicate is parameterized by type parameter T
 */
public class OrPredicate<T>implements Predicate<T> {
  Predicate<T> first;
  Predicate<T> second;

  public OrPredicate(Predicate<T> first, Predicate<T> second){
    this.first = first;
    this.second = second;
  }

  @Override
  public boolean accept(T item) {
    return (first.accept(item) || second.accept(item));
  }
}