package myo2key;

/**
 * Interface for the source side of the mapping; determines whether or not the user
 * satisfied the activation conditions with their myo.
 * @author Adam Cutler
 */
public interface MyoMapSource {

  /**
   * Determines whether the action was triggered, and to what degree.
   * @return 0 for not triggered, any value up to and including 1 if it was.
   */
  public double actionTriggered();
}
