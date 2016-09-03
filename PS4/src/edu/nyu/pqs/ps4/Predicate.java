package edu.nyu.pqs.ps4;

/**
 * The user of this interface has precise control over
 * rules to decide which values satisfy the predicate
 * @author Anuj Bora
 * @param <T> Predicate is parameterized by type parameter T
 */
public interface Predicate<T> {
  boolean accept(T item);
}