package edu.nyu.pqs.ps4;

/**
 * Not Predicate implementation of the Predicate interface
 * A value is considered valid if it doesn't satisfies the provided
 * predicate.
 * @author Anuj Bora
 * @param <T> NotPredicate is parameterized by type parameter T
 */
public class NotPredicate<T> implements Predicate<T> {
  Predicate<T> predicate;

  public NotPredicate(Predicate<T> predicate) {
    this.predicate = predicate;
  }

  @Override
  public boolean accept(T item) {
    return !predicate.accept(item);
  }
}