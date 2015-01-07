package myo2key;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Object representation of a mapping from doing something with a myo to something
 * happening on the computer.
 * @author Adam Cutler
 */
public class Myo2KeyMapping {
  /**
   * If one of the triggers is a movement, get a measure of how fast it was for later
   * A list of things you have to do with the myo in order to activate this mapping.
   * The last one in the list is the primary trigger who's value will be passed to the modifier.
   */
  ArrayList<MyoMapSource> triggers;

  /**
   * Modifies how the action is fired.
   */
  MyoMapModifier modifier;

  /**
   * The action associated with this mapping.
   */
  MyoMapAction action;

  /**
   * The priority of this mapping in regards to other mappings.
   */
  MyoMapPriority priority;

  /**
   * A variable to store the computed scale until the action is fired.
   */
  private double actionInput = 0;

  /**
   * Constructor.
   * @param mod A modifier that controls how this mapping is fired.
   * @param act The action the mapping performs when fired.
   * @param prio The priority this mapping has.
   * @param sources One or more MyoMapSources to be used as triggers for this mapping.
   */
  public Myo2KeyMapping(MyoMapModifier mod, MyoMapAction act, MyoMapPriority prio, MyoMapSource... sources) {
    triggers = new ArrayList<MyoMapSource>(Arrays.asList(sources));
    modifier = mod;
    action = act;
    priority = prio;
  }

  /**
   * Constructor. Uses a default priority.
   * @param mod A modifier that controls how this mapping is fired.
   * @param act The action the mapping performs when fired.
   * @param sources One or more MyoMapSources to be used as triggers for this mapping.
   */
  public Myo2KeyMapping(MyoMapModifier mod, MyoMapAction act, MyoMapSource... sources) {
    this(mod, act, new MyoMapPriority(), sources);
  }

  /**
   * Constructor. Uses an on-demand modifier and default priority.
   * @param act The action the mapping performs when fired.
   * @param sources One or more MyoMapSources to be used as triggers for this mapping.
   */
  public Myo2KeyMapping(MyoMapAction act, MyoMapSource... sources) {
    this(new OnDemandMapModifier(), act, new MyoMapPriority(), sources);
  }

  /**
   * Accessor for the priority.
   * @return The mapping's priority.
   */
  public MyoMapPriority getPriority() {
    return priority;
  }

  /**
   * Computes the value of the scale used when firing the action and stores it.
   * @param delta The amount of time passed in milliseconds since the last cycle.
   * @return Whether or not the action will need to be fired.
   */

  public boolean computeScale(long delta) {
    double scale = 0.0;
    for(MyoMapSource source : triggers) {
      scale = source.actionTriggered(delta);
    }
    System.out.println("scale: " + scale);
    scale = modifier.modifyAction(scale, delta);
    actionInput = scale;
    System.out.println("actionInput: " + scale);
    return scale != 0.0;
  }

  /**
   * Fires this mapping's action and zeroes the stored input scalar.
   */
  public void fireAction() {
    System.out.println("fireAction: " + actionInput);
    if(actionInput != 0.0) {
      action.fireAction(actionInput);
      actionInput = 0.0;
    }
  }

}
