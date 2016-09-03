package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import edu.nyu.pqs.stopwatch.api.Stopwatch;

/**
 * The StopwatchFactory is a thread-safe factory class for Stopwatch objects.
 * It maintains references to all created Stopwatch objects and provides a
 * convenient method for getting a list of those objects.
 *
 */
public class StopwatchFactory {
  public static final Map<String, Stopwatch> stopwatches =
      new ConcurrentHashMap<String, Stopwatch>();

  /**
   * Creates and returns a new Stopwatch object
   * @param id The identifier of the new object
   * @return The new Stopwatch object
   * @throws IllegalArgumentException if <code>id</code> is empty, null, or
   *     already taken.
   */
  public static Stopwatch getStopwatch(String id) {
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("ID cannot be null or empty");
    } else {
      Stopwatch s = new StopwatchApp(id);
      Stopwatch result = stopwatches.putIfAbsent(id, s);
      if(result == null) {
        return s;
      } else {
        /*
         * Dereference created no longer needed objects so that they will be
         * garbage collected more sooner
         */
        s = null;
        result = null;
        throw new IllegalArgumentException("Stopwatch with given "
            + "ID already exists");
      }
    }
  }

  /**
   * Returns a list of all created stopwatches
   * @return a List of all the created Stopwatch objects.  Returns an empty
   * list if no Stopwatches have been created.
   */
  public static List<Stopwatch> getStopwatches() {
    return new ArrayList<Stopwatch>(stopwatches.values());
  }
}