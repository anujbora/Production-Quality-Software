package edu.nyu.pqs.ps4;

/**
 * Implementation of the Predicate interface
 * A value is considered valid if it doesn't have character 'M' in it
 * @author Anuj Bora
 */
public class SkipMPredicate implements Predicate<String> {
  @Override
  public boolean accept(String item) {
    if (item.indexOf('M') == -1) {
      return true;
    }
    return false;
  }
}