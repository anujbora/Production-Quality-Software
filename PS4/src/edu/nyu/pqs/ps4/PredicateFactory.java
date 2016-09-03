package edu.nyu.pqs.ps4;

/**
 * PredicateFactory returns required instance of either AndPredicate
 * or OrPredicate or NotPredicate
 * @author Anuj Bora
 * @param <T> PredicateFactory is parameterized by type parameter T
 */
public class PredicateFactory<T> {
  /**
   * @param first predicate
   * @param second predicate
   * @return AndPredicate
   */
  public AndPredicate<T> and(Predicate<T> first, Predicate<T> second) {
    return new AndPredicate<T>(first, second);
  }

  /**
   * @param first predicate
   * @param second predicate
   * @return OrPredicate
   */
  public OrPredicate<T> or(Predicate<T> first, Predicate<T> second) {
    return new OrPredicate<T>(first, second);
  }

  /**
   * @param predicate
   * @return NotPredicate
   */
  public NotPredicate<T> not(Predicate<T> predicate) {
    return new NotPredicate<T>(predicate);
  }
}