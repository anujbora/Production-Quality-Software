package edu.nyu.pqs.ps4;

/**
 * Implementation of the Predicate interface
 * A value is considered valid if it doesn't have character 'Q' in it
 * @author Anuj Bora
 */
public class SkipQPredicate implements Predicate<String> {
  @Override
  public boolean accept(String item) {
    if (item.indexOf('Q') == -1) {
      return true;
    }
    return false;
  }
}