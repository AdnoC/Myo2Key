package myo2key;

import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.enums.VibrationType;
import com.thalmic.myo.enums.UnlockType;
public class Myo2Key {
  public static void main(String[] args) {
    try {
      RobotController rc = RobotController.getRobotController();
      rc.mouseDeltaMove(50, 100);
      Hub hub = new Hub();
      System.out.println("Attempting to find a Myo.");
      Myo myo = hub.waitForMyo(10000);

      if(myo == null)
          throw new RuntimeException("Unable to find a Myo.");
      System.out.println("Connected to a Myo");
      myo.vibrate(VibrationType.VIBRATION_SHORT);
      myo.unlock(UnlockType.UNLOCK_HOLD);

      DeviceDataStorage dataStorage = DeviceDataStorage.getDDS();
      hub.addListener(dataStorage);
      MyoMappingController mapController = new MyoMappingController();

      MyoMapModifier mod = new OnDemandMapModifier(2000L);
      MyoMapAction act = new KeyMapAction(java.awt.event.KeyEvent.VK_T);
      MyoMapSource src = new PoseMapSource(new com.thalmic.myo.Pose(com.thalmic.myo.enums.PoseType.FIST));
      Myo2KeyMapping mapping = new Myo2KeyMapping(mod, act, src);

      mapController.mappings.add(mapping);
      while(true) {
        hub.run(1000 / 20);
        mapController.runMappings();
        //System.out.println(dataStorage);
      }

    } catch(Exception e) {
      System.err.println("Error: ");
      e.printStackTrace();
      System.exit(1);
    }
  }
}
