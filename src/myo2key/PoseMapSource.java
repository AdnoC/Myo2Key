package myo2key;

import com.thalmic.myo.Pose;
/**
 * Interface for the source side of the mapping; determines whether or not the user
 * satisfied the activation conditions with their myo.
 * @author Adam Cutler
 */
public class PoseMapSource implements MyoMapSource {

  private Pose myPose;
  private boolean wasActive = false;

  private long buffer = 0;
  private long bufferSize;

  /**
   * Creates a new PoseMapSource with the specified pose and buffer size.
   * @param pose The Myo Pose this source corresponds to.
   * @param bufferSize The length of the buffer in milliseconds.
   */
  public PoseMapSource(Pose pose, long bufferSize) {
    this.myPose = pose;
    this.bufferSize = bufferSize;
  }

  /**
   * Creates a new PoseMapSource with the specified pose and a buffer of 100ms.
   * @param pose The Myo Pose this source corresponds to.
   */
  public PoseMapSource(Pose pose) {
    this.myPose = pose;
    this.bufferSize = 100;
  }

  /**
   * Checks if the buffer was consumed and updates it. 
   * @param active Whether or not the correct pose is being held.
   * @param delta The amount of time since the last cycle.
   * @return An updated active value that takes into account the buffer deadzone.
   */
  protected boolean checkBuffer(boolean active, long delta) {
    // If the buffer is larger than the maximum, set it to max.
    if(buffer > bufferSize) {
      buffer = bufferSize;
    }
    // If the state did not change, update the buffer then
    // return the original active value.
    if(active == wasActive) {
      // If the buffer is less than zero, change it so it is no less than zero.
      if(buffer < 0) {
        buffer = 0;
      }

      // If the buffer is not back to full, increment it by the amount of time passed.
      if(buffer < bufferSize) {
        buffer += delta;
      }
      return active;
    // If the state changed, reduce the buffer and return the correct value of active
    // depending on the buffer.
    } else {
      // Reduce the buffer by the amount of time that passed since the last cycle.
      buffer -= delta;
      // If the buffer is depleted, change the stored state and return the original active value.
      if(buffer <= 0) {
        wasActive = active;
        buffer = -1 * buffer;
        return active;
      // If we still have a buffer, return the previous state of active.
      } else {
        return wasActive;
      }
    }
  }

  /**
   * Determines whether the action was triggered, and to what degree.
   * @param delta How long since the last time this ws called.
   * @return 0 for not triggered, any value up to and including 1 if it was.
   */
  public double actionTriggered(long delta) {
    boolean active = myPose.getType() == DeviceDataStorage.getInstance().getPose().getType();
    active = checkBuffer(active, delta);
    return active ? 1.0 : 0.0;
  }

  /**
   * Accessor for buffer size.
   * @return The size of this object's buffer.
   */
  public long getBufferSize() {
    return bufferSize;
  }

  /**
   * Accessor for the pose.
   * @return The pose this object is looking for.
   */
  public Pose getPose() {
    return myPose;
  }
}

