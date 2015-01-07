package myo2key;

/**
 * Determins a mapping's priority. What priority group it belongs to and where
 * it is in the group.
 * @author Adam Cutler
 */
public class MyoMapPriority {
  // What priority group this priority belongs to.
  private int pId;
  // Where in the group this priority is.
  private int pVal;

  /**
   * Default constructor. Assigns this priority to the default group with lowest priority.
   */
  public MyoMapPriority() {
    pId = 0;
    pVal = 0;
  }

  /**
   * Creates a priority with the specified group and value.
   * @param priorityID The group this priority belongs to.
   * @param priorityValue Where in the group this priority is.
   */
  public MyoMapPriority(int priorityID, int priorityValue) {
    pId = priorityID;
    pVal = priorityValue;
  }

  /**
   * Accessor for priority id.
   * @return priority id.
   */
  public int getId() {
    return pId;
  }

  /**
   * Accessor for priority value.
   * @return priority value.
   */
  public int getValue() {
    return pVal;
  }
}
