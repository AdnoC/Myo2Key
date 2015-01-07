package myo2key;

import java.util.ArrayList;

/**
 * Handles all the mappings, storing them as well as running them.
 *
 * @author Adam Cutler
 * @version 0
 */
public class MyoMappingController {

  /**
   * The time when the previous cycle occured.
   */
  private long prevCycleTimestamp;

  /**
   * List of all mappings. Sorted by priority.
   * Ascending pId as primary and descending pVal as secondary.
   */
  protected ArrayList<Myo2KeyMapping> mappings;

  /**
   * Creates a new MyoMappingController.
   */
  public MyoMappingController() {
    prevCycleTimestamp = System.currentTimeMillis();
    mappings = new ArrayList<Myo2KeyMapping>();
  }

  /**
   * Prepares all the mappings then fires all their actions.
   */
  public void runMappings() {
    fireActions(prepareMappings());
  }

  /**
   * Fires the actions of all mappings in a list.
   * @param maps The list of actions.
   */
  public void fireActions(ArrayList<Myo2KeyMapping> maps) {
    for(Myo2KeyMapping map : maps) {
      map.fireAction();
    }
  }

  /**
   * Makes a list of mappings to be fired.
   * @return The list of mappings to be fired.
   */
  public ArrayList<Myo2KeyMapping> prepareMappings() {
    long timestamp = System.currentTimeMillis();
    long delta = timestamp - prevCycleTimestamp;
    prevCycleTimestamp = timestamp;

    ArrayList<Myo2KeyMapping> preppedMappings = new ArrayList<Myo2KeyMapping>();

    // For each mapping, if there are no priority conflicts and it can be fired, add it to the list.
    for(Myo2KeyMapping map : mappings) {
      // Get all the prepared mappings in the priority group.
      ArrayList<Myo2KeyMapping> tmpMps = getHighestPriorityMapping(map.getPriority().getId(), preppedMappings);
      // If there were no prepped mappings or the values are the same, prep the mapping.
      if(tmpMps.isEmpty() || tmpMps.get(0).getPriority().getValue() == map.getPriority().getValue()) {
        // If the mapping can be fired, add it to the list.
        if(map.computeScale(delta)) {
          preppedMappings.add(map);
        }
      }
    }

    return preppedMappings;
  }

  /**
   * Finds the highest value mappings for a certain priority group.
   * @param pId The id of the group to look for.
   * @param maps The list of mappings to look in.
   * @return A list of mappings in the specified group that have the highest value.
   */
  public ArrayList<Myo2KeyMapping> getHighestPriorityMapping(int pId, ArrayList<Myo2KeyMapping> maps) {
    ArrayList<Myo2KeyMapping> matches = new ArrayList<Myo2KeyMapping>();
    for(Myo2KeyMapping map : maps) {
     if(map.getPriority().getId() == pId) {
       if(matches.isEmpty() || matches.get(0).getPriority().getValue() == map.getPriority().getValue()) {
         matches.add(map);
       } else if(!matches.isEmpty()) {
         return matches;
       }
     } else if(!matches.isEmpty()) {
       return matches;
     }
    }
    return matches;
  }

}
