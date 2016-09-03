package edu.nyu.pqs.ps4;

/**
 * Implementation of the Predicate interface
 * A value is considered valid if it doesn't has character 'B' in it
 * @author Anuj Bora
 */
public class SkipBPredicate implements Predicate<String> {
  @Override
  public boolean accept(String item) {
    if (item.indexOf('B') == -1) {
      return true;
    }
    return false;
  }
}