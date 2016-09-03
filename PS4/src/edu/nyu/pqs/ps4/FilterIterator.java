package edu.nyu.pqs.ps4;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the Filter Iterator. It implements Iterator interface.
 * @author Anuj Bora
 * @param <T> FilterIterator is parameterized by type parameter T
 */
public class FilterIterator<T> implements Iterator<T> {
  private Iterator<T> iterator;
  private Predicate<T> predicate;
  private T nextItem;
  private boolean nextIsValid;

  public FilterIterator(Iterator<T> iterator, Predicate<T> predicate) {
    this.iterator = iterator;
    this.predicate = predicate;
  }

  /**
   * {@inheritDoc}
   * Checks if there's a value which satisfies the predicate.
   * @return true if a value exists which satisfies the predicate
   */
  @Override
  public boolean hasNext() {
    if (nextIsValid) {
      return true;
    }
    while (iterator.hasNext()) {
      nextItem = iterator.next();
      if (predicate.accept(nextItem)) {
        nextIsValid = true;
        return true;
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   * Returns a value which satisfies the predicate (if there's any).
   * @return Value which satisfies the predicate
   * @throws NoSuchElementException() if no value satisfies the predicate
   */
  @Override
  public T next() {
    if (nextIsValid) {
      nextIsValid = false;
      T val = nextItem;
      nextItem = null;
      return val;
    }
    if (hasNext()) {
      return next();
    }
    throw new NoSuchElementException("No more Elements in the set");
  }
}