package myo2key;

import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.enums.VibrationType;
import com.thalmic.myo.enums.UnlockType;
public class Myo2Key {
  public static void main(String[] args) {
    asdf2();
    try {
      //RobotController rc = RobotController.getInstance();
      //rc.mouseDeltaMove(50, 100);
      //Hub hub = new Hub();
      //System.out.println("Attempting to find a Myo.");
      //Myo myo = hub.waitForMyo(10000);

      //if(myo == null)
          //throw new RuntimeException("Unable to find a Myo.");
      //System.out.println("Connected to a Myo");
      //myo.vibrate(VibrationType.VIBRATION_SHORT);
      //myo.unlock(UnlockType.UNLOCK_HOLD);

      //DeviceDataStorage dataStorage = DeviceDataStorage.getInstance();
      //hub.addListener(dataStorage);
      //MyoMappingController mapController = new MyoMappingController();

      //MyoMapModifier mod = new OnDemandMapModifier(2000L);
      //MyoMapAction act = new KeyMapAction(java.awt.event.KeyEvent.VK_T);
      //MyoMapSource src = new PoseMapSource(new com.thalmic.myo.Pose(com.thalmic.myo.enums.PoseType.FIST));
      //Myo2KeyMapping mapping = new Myo2KeyMapping(mod, act, src);


      //mapController.addMapping(mapping);

      //while(true) {
        //hub.run(1000 / 20);
        //mapController.runMappings();
        ////System.out.println(dataStorage);
      //}

    } catch(Exception e) {
      System.err.println("Error: ");
      e.printStackTrace();
      System.exit(1);
    }
  }

  public static void asdf() {
    try{
      String str =  "{\"Action\":{\"Type\":\"KeyMapAction\",\"Keycode\":84,\"Key_Modifier\":[]},\"Priority\":{\"Priority_Value\":0,\"Priority_Id\":0},\"Triggers\":[{\"Type\":\"PoseMapSource\",\"Buffer_Size\":100,\"Pose_Type\":\"FIST\"}],\"Modifier\":{\"Type\":\"OnDemandMapModifier\",\"Cooldown\":2000},\"Map_Id\":-1}";
      org.json.JSONObject jobj = new org.json.JSONObject(str);
      System.out.println(jobj.toString(2));
      org.json.JSONObject j2 = jobj.getJSONObject("Action");
      j2.put("Type", "----_____OOOOOO______-----");
      System.out.println(jobj.toString(2));

    } catch(Exception e) {
      System.err.println("Error: ");
      e.printStackTrace();
      System.exit(1);
    }
  }

  public static void testFactory() {
    try{
      System.out.println("--- To JSON --- ");
      MyoMapModifier mod = new OnDemandMapModifier(2000L);
      MyoMapAction act = new KeyMapAction(java.awt.event.KeyEvent.VK_T);
      MyoMapSource src = new PoseMapSource(new com.thalmic.myo.Pose(com.thalmic.myo.enums.PoseType.FIST));
      Myo2KeyMapping mapping = new Myo2KeyMapping(mod, act, src);

      org.json.JSONObject jobj = MyoMapFactory.convertMapping(mapping);
      System.out.println(jobj.toString(2));

      System.out.println("--- FROM JSON --- ");
      String str =  "{\"Action\":{\"Type\":\"KeyMapAction\",\"Keycode\":84,\"Key_Modifier\":[]},\"Priority\":{\"Priority_Value\":0,\"Priority_Id\":0},\"Triggers\":[{\"Type\":\"PoseMapSource\",\"Buffer_Size\":100,\"Pose_Type\":\"FIST\"}],\"Modifier\":{\"Type\":\"OnDemandMapModifier\",\"Cooldown\":2000},\"Map_Id\":-1}";

       mapping = MyoMapFactory.createMapping(str);

      jobj = MyoMapFactory.convertMapping(mapping);
      System.out.println(jobj.toString(2));

    } catch(Exception e) {
      System.err.println("Error: ");
      e.printStackTrace();
      System.exit(1);
    }
  }

  public static void asdf2() {
    try{
      String str =  "{\"Action\":{\"Type\":\"KeyMapAction\",\"Keycode\":84,\"Key_Modifier\":[]},\"Priority\":{\"Priority_Value\":0,\"Priority_Id\":0},\"Triggers\":[{\"Type\":\"PoseMapSource\",\"Buffer_Size\":100,\"Pose_Type\":\"FIST\"}],\"Modifier\":{\"Type\":\"OnDemandMapModifier\",\"Cooldown\":2000},\"Map_Id\":-1}";
      org.json.JSONObject jobj = new org.json.JSONObject(str);
      Settings set = Settings.getInstance();
      System.out.println("Loaded");
      System.out.println(set.root.toString(2));
      set.root.put("Mapping2", jobj);
      set.save();
      System.out.println("Saved");

    } catch(Exception e) {
      System.err.println("Error: ");
      e.printStackTrace();
      System.exit(1);
    }
  }
}
