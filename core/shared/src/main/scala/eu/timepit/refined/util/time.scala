package eu.timepit.refined
package util

import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric.Interval

/** Module for date and time related refined types. */
object time {

  /** An `Int` in the range from 1 to 12 representing the month-of-year. */
  type Month = Int Refined Interval.Closed[1, 12]

  /**
   * An `Int` in the range from 1 to 31 representing the day-of-month.
   * Note that the days from 29 to 31 are not valid for all months.
   */
  type Day = Int Refined Interval.Closed[1, 31]

  /** An `Int` in the range from 0 to 23 representing the hour-of-day. */
  type Hour = Int Refined Interval.Closed[0, 23]

  /** An `Int` in the range from 0 to 59 representing the minute-of-hour. */
  type Minute = Int Refined Interval.Closed[0, 59]

  /** An `Int` in the range from 0 to 59 representing the second-of-minute. */
  type Second = Int Refined Interval.Closed[0, 59]

  /** An `Int` in the range from 0 to 999 representing the millisecond-of-second. */
  type Millis = Int Refined Interval.Closed[0, 999]
}
