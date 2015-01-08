package myo2key;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import com.thalmic.myo.enums.PoseType;
import com.thalmic.myo.Pose;

/**
 * Creates Myo2KeyMappings from a savable format and turns the objects into a savable format.
 *
 * @author Adam Cutler
 * @version 
 */
public class MyoMapFactory {

  /**
   * These strings are to make sure that no stupid spelling mistakes break loading or saving.
   */

  private static final String MAP_ID_STRING = "Map_Id";
  private static final String TRIGGERS_STRING = "Triggers";
  private static final String MODIFIER_STRING = "Modifier";
  private static final String ACTION_STRING = "Action";
  private static final String PRIORITY_STRING = "Priority";

  private static final String TYPE_STRING = "Type";
  private static final String BUFFER_SIZE_STRING = "Buffer_Size";

  private static final String POSE_STRING = "Pose_Type";

  // Just realized that I use two different terms for basically the same thing in
  // the PoseMapSource and OnDemandMapModifier... Could probably make them the same.
  private static final String COOLDOWN_STRING = "Cooldown";

  private static final String KEYCODE_STRING = "Keycode";
  // Or should I just use MODIFIER_STRING?
  private static final String KEYMODIFIER_STRING = "Key_Modifier";

  private static final String PRIORITY_ID_STRING = "Priority_Id";
  private static final String PRIORITY_VALUE_STRING = "Priority_Value";

  //private static final String


  public static JSONObject convertMapping(Myo2KeyMapping map) {
    JSONObject root = new JSONObject();

    // Lets save the mapId
    root.put(MAP_ID_STRING, map.mapId);

    // Parse the triggers first.
    JSONArray triggers = new JSONArray();
    for(MyoMapSource src : map.triggers) {
      JSONObject trig = new JSONObject();

      // Check what type of MyoMapSource it is and act accordingly.
      if(src instanceof PoseMapSource) {
        PoseMapSource pms = (PoseMapSource) src;
        trig.put(TYPE_STRING, "PoseMapSource");
        trig.put(BUFFER_SIZE_STRING, pms.getBufferSize());
        trig.put(POSE_STRING, pms.getPose().getType());
      }

      // This should always be true unless something goes horribly disasterously wrong.
      if(trig.length() != 0) {
        triggers.put(trig);
      }
    }
    root.put(TRIGGERS_STRING, triggers);

    // Then parse the modifier.
    JSONObject modifier = new JSONObject();
    if(map.modifier instanceof OnDemandMapModifier) {
      OnDemandMapModifier odmm = (OnDemandMapModifier) map.modifier;
      modifier.put(TYPE_STRING, "OnDemandMapModifier");
      modifier.put(COOLDOWN_STRING, odmm.getCooldown());
    }
    root.put(MODIFIER_STRING, modifier);

    // After that we do the action.
    JSONObject action = new JSONObject();
    if(map.action instanceof KeyMapAction) {
      KeyMapAction kmp = (KeyMapAction) map.action;
      action.put(TYPE_STRING, "KeyMapAction");
      action.put(KEYCODE_STRING, kmp.getKeycode());
      JSONArray keyMods = new JSONArray();
      for(int mod : kmp.getModifiers()) {
        keyMods.put(mod);
      }
      action.put(KEYMODIFIER_STRING, keyMods);
    }
    root.put(ACTION_STRING, action);

    // Finally we do priority
    JSONObject priority = new JSONObject();
    priority.put(PRIORITY_ID_STRING, map.priority.getId());
    priority.put(PRIORITY_VALUE_STRING, map.priority.getValue());
    root.put(PRIORITY_STRING, priority);


    return root;
  }

  /**
   * Creates a Myo2KeyMapping from JSON. Conveneint method that converts to JSONObject.
   * @param source The JSON string to use as a source.
   * @return the created Myo2KeyMapping.
   * @throws JSONException If the json is not correct, throws an error.
   */
  public static Myo2KeyMapping createMapping(String source) throws JSONException {
    return createMapping(new JSONObject(source));
  }

  /**
   * Creates a Myo2KeyMapping from JSON. Conveneint method that converts to JSONObject.
   * @param source The JSON string to use as a source.
   * @return the created Myo2KeyMapping.
   * @throws JSONException If the json is not correct, throws an error.
   */
  public static Myo2KeyMapping createMapping(JSONObject source) throws JSONException {
    int mapId = source.getInt(MAP_ID_STRING);

    JSONObject jobj;

    MyoMapPriority priority = null;
    jobj = source.getJSONObject(PRIORITY_STRING);
    int pId = jobj.getInt(PRIORITY_ID_STRING);
    int pVal = jobj.getInt(PRIORITY_VALUE_STRING);
    priority = new MyoMapPriority(pId, pVal);


    MyoMapAction action = null;
    jobj = source.getJSONObject(ACTION_STRING);
    String type = jobj.getString(TYPE_STRING);
    if(type.equals("KeyMapAction")) {
      int keycode = jobj.getInt(KEYCODE_STRING);
      JSONArray keyMods = jobj.getJSONArray(KEYMODIFIER_STRING);
      int[] mods = new int[keyMods.length()];
      for(int i = 0; i < keyMods.length(); i++) {
        mods[i] = keyMods.getInt(i);
      }

      action = new KeyMapAction(keycode, mods);
    }

    MyoMapModifier modifier = null;
    jobj = source.getJSONObject(MODIFIER_STRING);
    type = jobj.getString(TYPE_STRING);
    if(type.equals("OnDemandMapModifier")) {
      long cooldown = jobj.getLong(COOLDOWN_STRING);
      modifier = new OnDemandMapModifier(cooldown);
    }

    JSONArray jarr = source.getJSONArray(TRIGGERS_STRING);
    MyoMapSource[] triggers = new MyoMapSource[jarr.length()];
    for(int i = 0; i < jarr.length(); i++) {
      jobj = jarr.getJSONObject(i);
      type = jobj.getString(TYPE_STRING);

      if(type.equals("PoseMapSource")) {
        long bufferSize = jobj.getLong(BUFFER_SIZE_STRING);
        String poseString = jobj.getString(POSE_STRING);
        PoseType poseType = PoseType.valueOf(poseString);
        triggers[i] = new PoseMapSource(new Pose(poseType), bufferSize);
      }
    }


    return new Myo2KeyMapping(modifier, action, priority, mapId, triggers);

  }
}
