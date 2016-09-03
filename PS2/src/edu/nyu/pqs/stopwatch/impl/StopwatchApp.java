package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.nyu.pqs.stopwatch.api.Stopwatch;

/**
 * Thread-safe implementation which enables following operations on a stopwatch :
 * <ul>
 * <li> Start </li>
 * <li> Stop </li>
 * <li> Record Lap</li>
 * <li> Reset stopwatch </li>
 * <li> Provides list of all the lap times that have been recorded </li>
 * </ul>
 * It handles exception like starting already started stopwatch,
 * stopping non-running stopwatch, or creating a lap on non-running stopwatch.
 * @author Anuj Bora
 */
public class StopwatchApp implements Stopwatch {
  private final String id;
  private final List<Long> laps;
  /*
   * Used for providing exclusive access to stopwatchRunning variable
   * using synchronization
   */
  private final Object stateLock = new Object();
  /*
   * Used for providing exclusive access  all mutable class-wide shared 
   * objects and variable except the stopWatchRunning variable using
   * synchronization
   */
  private final Object dataLock = new Object();
  private boolean stopwatchRunning = false;
  /*
   * Stores the time when last lap() or stop() was called
   */
  private Long previousTime = 0L;
  private Long lastLap = 0L;
  /*
   * idleTime stores the time spent in idle state i.e. the time spent
   * after pressing stop() and before pressing start() again. We need 
   * to store this time as this time should not be considered for
   * calculating a lap and hence should be subtracted while calculating
   * lap
   */
  private Long idleTime = 0L;
  /*
   * firstStart keeps track whether start() is called for the first time
   */
  private boolean firstStart = true;

  StopwatchApp(String id) {
    this.id = id;
    this.laps = new ArrayList<Long>();
  }

  /**
   * {@inheritDoc}
   * Returns the id of the stopwatch
   * @return id of the stopwatch
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * {@inheritDoc}
   * Starts the stopwatch  and updates the previous time
   * to current system time if start() is called for the first time
   * Throws an exception if the method is called on an already
   * running stopwatch
   * @throws IllegalStateException if stopwatch is already running
   */
  @Override
  public void start() {
    Long currentTime = System.nanoTime();
    synchronized(stateLock) {
      if(stopwatchRunning) {
        throw new IllegalStateException("Stopwatch already running!");
      } else {
        stopwatchRunning = true;
      }
    }
    synchronized(dataLock) {
      /*
       * If laps list is not empty implies start() is called after a
       * stop() without clearing laps list i.e. continue ignoring stop
       */
      if(!laps.isEmpty()) {
        lastLap = laps.get(laps.size() - 1);
        /*
         * Remove the last lap as it has to be ignored
         */
        laps.remove(laps.size()-1);
        firstStart = false;
        idleTime = currentTime - previousTime;
        if(!laps.isEmpty()) {
          /*
           * Update previous time to the time when last lap or last start()
           * was called
           */
          previousTime = previousTime - laps.get(laps.size() - 1);
        } else {
          previousTime = previousTime - lastLap;
        }
      }
      /*
       * If this is the first time of calling start(), previous time will
       * be updated to current time and will be used for next lap() or stop()
       * But if start() was called second time, we won't be using current time
       * to calculate future laps. Instead we will be using previous time
       * which was set last time. Hence, no need to update previous time
       */
      if(firstStart) {
        previousTime = currentTime;
      }
    }
  }

  /**
   * {@inheritDoc}
   * Adds a lap to the list by subtracting previous time and
   * idle time from current time
   * Throws an exception if the method is called when stopwatch is not
   * running
   * @throws IllegalStateException if stopwatch is not running and
   */
  @Override
  public void lap() {
    Long currentTime = System.nanoTime();
    synchronized(stateLock) {
      if(!stopwatchRunning) {
        throw new IllegalStateException("Stopwatch is not running!");
      }
    }
    /*
     * Subtract previous time and idleTime from current time to obtain lap
     */
    synchronized(dataLock) {
      laps.add(currentTime - previousTime - idleTime);
      /*
       * Reset idleTime to 0. Its value will be non-zero only when start()
       * is called after calling stop()
       */
      idleTime = 0L;
      previousTime = currentTime;
    }
  }

  /**
   * {@inheritDoc}
   * Stops the stopwatch by creating new lap of current time and setting
   * stopwatchRunning's value to false
   */
  @Override
  public void stop() {
    lap();
    synchronized(stateLock) {
      stopwatchRunning = false;
    }
  }

  /**
   * {@inheritDoc}
   * Resets the stopwatch by clearing laps data from the laps list and setting
   * stopwatchRunning's value to false
   */
  @Override
  public void reset() {
    synchronized(stateLock) {
      stopwatchRunning = false;
    }
    synchronized(dataLock) {
      laps.clear();
    }
  }

  /**
   * {@inheritDoc}
   * Copies all the timings from laps list to a new list by converting
   * them to milliseconds and returns the new list
   * @return List of all the current laps in Milliseconds
   */
  @Override
  public List<Long> getLapTimes() {
    List<Long> currentLapTimes = new ArrayList<Long>();
    synchronized(dataLock) {
      for(Long l : laps) {
        Long inMilliseconds = TimeUnit.MILLISECONDS.convert(l, TimeUnit.NANOSECONDS);
        currentLapTimes.add(inMilliseconds);
      }
    }
    return currentLapTimes;
  }

  @Override
  public String toString() {
    /*
     * stateLock lock must always be obtained before dataLock when
     * both are used in nested manner
     */
    synchronized(stateLock) {
      synchronized(dataLock) {
        return "StopwatchApp [id=" + id + ", laps=" + laps + ", " +
            "stopwatchRunning=" + stopwatchRunning + "]";
      }
    }
  }
}