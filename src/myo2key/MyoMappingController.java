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
   * The mapId that will be assigned to the next created map.
   */
  //private static int nextMapId = 0;

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
   * Adds a Myo2KeyMapping to track and assigns it a mapId if it does not already have one.
   * @param map The mapping to add.
   * @return Null normally, or if map has a mapId that another map has, replaces that and returns it.
   */
  public Myo2KeyMapping addMapping(Myo2KeyMapping map) {
    Myo2KeyMapping oldMap = null;
    if(map.mapId == -1) {
      int newId;
      do {
        newId = (int)(Math.random() * 1000);
      } while(getByMapId(newId) != null);
      map.mapId = newId;
    } else {
      oldMap = getByMapId(map.mapId);
      if(oldMap != null) {
        mappings.remove(oldMap);
      }
    }

    mappings.add(getPriorityIndex(map.getPriority()), map);
    return oldMap;
  }

  /**
   * Get a handled Myo2KeyMapping with the specified mapId.
   * @param mapId The mapId to look for.
   * @return The mapping if found, null if not.
   */
  public Myo2KeyMapping getByMapId(int mapId) {
    for(Myo2KeyMapping map : mappings) {
      if(map.mapId == mapId) {
        return map;
      }
    }
    return null;
  }

  /**
   * Finds the index in the list that a certain priority would be at.
   * @param prio The priority to use when searching.
   * @return The index.
   */
  protected int getPriorityIndex(MyoMapPriority prio) {
    int index = 0;
    for(Myo2KeyMapping map : mappings) {
      if(prio.getId() < map.getPriority().getId() || (prio.getId() == map.getPriority().getId() && prio.getValue() <= map.getPriority().getValue())) {
        return index;
      }
      index++;
    }
    return index;
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
