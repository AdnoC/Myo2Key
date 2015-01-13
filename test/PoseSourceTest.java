import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

import myo2key.DeviceDataStorage;
import myo2key.PoseMapSource;
import com.thalmic.myo.Pose;
import com.thalmic.myo.enums.PoseType;


public class PoseSourceTest {

  final static double epsilon = .01;
  final static long cooldown = 100;
  final static double ON = 1.0;
  final static double OFF = 0.0;
  DeviceDataStorage dds = DeviceDataStorage.getInstance();
  Pose fist = new Pose(PoseType.FIST);
  Pose rest = new Pose(PoseType.REST);
  PoseMapSource pms = null;

  @Before
  public void intiatePoseMapSource() {
    pms = new PoseMapSource(fist, cooldown);
  }

  @Test
  public void zeroDelta() {
    dds.onPose(null, 1000, fist);
    assertEquals(pms.actionTriggered(0), ON, epsilon);
    assertEquals(pms.actionTriggered(0), ON, epsilon);
    assertEquals(pms.actionTriggered(0), ON, epsilon);
    dds.onPose(null, 1000, rest);
    assertEquals(pms.actionTriggered(0), OFF, epsilon);
    assertEquals(pms.actionTriggered(0), OFF, epsilon);
    assertEquals(pms.actionTriggered(0), OFF, epsilon);
  }

  @Test
  public void correctPoseTest() {
    dds.onPose(null, 1000, fist);
    assertEquals(pms.actionTriggered(0), ON, epsilon);
    assertEquals(pms.actionTriggered(cooldown), ON, epsilon);
    dds.onPose(null, 1000, rest);
    assertEquals(pms.actionTriggered(cooldown), OFF, epsilon);
  }

  @Test
  public void cycleSwitchingTest() {
    dds.onPose(null, 1000, fist);
    assertEquals(pms.actionTriggered(cooldown), ON, epsilon);
    dds.onPose(null, 1000, rest);
    assertEquals(pms.actionTriggered(cooldown), OFF, epsilon);
    dds.onPose(null, 1000, fist);
    assertEquals(pms.actionTriggered(cooldown), ON, epsilon);
    dds.onPose(null, 1000, rest);
    assertEquals(pms.actionTriggered(cooldown), OFF, epsilon);
    dds.onPose(null, 1000, fist);
    assertEquals(pms.actionTriggered(cooldown), ON, epsilon);
    dds.onPose(null, 1000, rest);
    assertEquals(pms.actionTriggered(cooldown), OFF, epsilon);
  }

  @Test
  /**
   * Keep changing active state, but don't let the cooldown run out and let it switch output.
   */
  public void wrongCycleSwitchingTest() {
    dds.onPose(null, 1000, fist);
    assertEquals(pms.actionTriggered(cooldown), ON, epsilon);
    dds.onPose(null, 1000, rest);
    assertEquals(pms.actionTriggered(cooldown / 2), ON, epsilon);
    dds.onPose(null, 1000, fist);
    assertEquals(pms.actionTriggered(cooldown), ON, epsilon);
    dds.onPose(null, 1000, rest);
    assertEquals(pms.actionTriggered(cooldown / 2), ON, epsilon);
    dds.onPose(null, 1000, fist);
    assertEquals(pms.actionTriggered(cooldown), ON, epsilon);
    dds.onPose(null, 1000, rest);
    assertEquals(pms.actionTriggered(cooldown - 1), ON, epsilon);
  }

  @Test
  /**
   * Make sure that the cooldown prevents switching output
   */
  public void cooldownBlockingTest() {
    dds.onPose(null, 1000, fist);
    assertEquals(pms.actionTriggered(cooldown), ON, epsilon);
    assertEquals(pms.actionTriggered(cooldown), ON, epsilon);

    dds.onPose(null, 1000, rest);
    assertEquals(pms.actionTriggered(cooldown / 4), ON, epsilon);
    assertEquals(pms.actionTriggered(cooldown / 4), ON, epsilon);
    assertEquals(pms.actionTriggered(cooldown / 4), ON, epsilon);
    assertEquals(pms.actionTriggered(cooldown / 4), OFF, epsilon);

    assertEquals(pms.actionTriggered(cooldown), OFF, epsilon);

    dds.onPose(null, 1000, fist);
    assertEquals(pms.actionTriggered(cooldown / 4), OFF, epsilon);
    assertEquals(pms.actionTriggered(cooldown / 4), OFF, epsilon);
    assertEquals(pms.actionTriggered(cooldown / 4), OFF, epsilon);
    assertEquals(pms.actionTriggered(cooldown / 4), ON, epsilon);
  }

  @Test
  /**
   * Make sure that having 0 cooldown allows for instantaneous output switching.
   */
  public void zeroCooldownSwitchingTest() {
    pms = new PoseMapSource(fist, 0);
    dds.onPose(null, 1000, fist);
    assertEquals(pms.actionTriggered(cooldown), ON, epsilon);
    assertEquals(pms.actionTriggered(cooldown), ON, epsilon);
    dds.onPose(null, 1000, rest);
    assertEquals(pms.actionTriggered(cooldown / 4), OFF, epsilon);
    dds.onPose(null, 1000, fist);
    assertEquals(pms.actionTriggered(cooldown), ON, epsilon);
    dds.onPose(null, 1000, rest);
    assertEquals(pms.actionTriggered(cooldown / 4), OFF, epsilon);
    dds.onPose(null, 1000, fist);
    assertEquals(pms.actionTriggered(cooldown), ON, epsilon);
    dds.onPose(null, 1000, rest);
    assertEquals(pms.actionTriggered(cooldown / 4), OFF, epsilon);
  }

  /**
   *
   */
}
