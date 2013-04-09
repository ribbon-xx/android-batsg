package com.chauhai.android.batsg.db.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.chauhai.android.batsg.util.ErrorUtil;

/**
 * JSONArray wrapper.
 *
 * @author umbalaconmeogia
 *
 */
public class JSONArrayUtil {

  /**
   * Wrapper for {@link JSONArray#getDouble(int)}
   * <p>
   * A RuntimeException wraps the raised exception.
   * @param json
   * @param index
   * @return
   */
  public static double getDouble(JSONArray json, int index) {
    try {
      return json.getDouble(index);
    } catch (JSONException e) {
      throw ErrorUtil.runtimeException(e);
    }
  }

  /**
   * Convert all elements in JSONArray (of JSONObject) to a list of JSONObject.
   * <p>
   * A RuntimeException wraps the raised exception.
   * @param json
   * @return
   */
  public static List<JSONObject> jsonArrayToJSONObjectList(JSONArray json) {
    ArrayList<JSONObject> list = new ArrayList<JSONObject>(); // Result

    if (json != null) {
      int nElement = json.length();
      for (int i = 0; i < nElement; i++) {
        try {
          JSONObject element = json.getJSONObject(i);
          list.add(element);
        } catch (JSONException e) {
          throw ErrorUtil.runtimeException(e);
        }
      }
    }

    return list;
  }

  /**
   * Map JSONArray of JSONObject by object's key.
   * <p>
   * A RuntimeException wraps the raised exception.
   * @param json
   * @param key
   * @return
   */
  public static Map<Object, JSONObject> map(JSONArray json, String key) {
    Map<Object, JSONObject> map = new HashMap<Object, JSONObject>(); // Result

    List<JSONObject> list = jsonArrayToJSONObjectList(json);

    for (JSONObject jsonObject: list) {
      Object value = JSONObjectUtil.get(jsonObject, key);
      map.put(value, jsonObject);
    }
    return map;
  }

  /**
   * Map JSONArray of JSONObject by object's key.
   * <p>
   * A RuntimeException wraps the raised exception.
   * @param json
   * @param key
   * @return
   */
  public static Map<Long, JSONObject> mapByLong(JSONArray json, String key) {
    Map<Long, JSONObject> map = new HashMap<Long, JSONObject>(); // Result

    List<JSONObject> list = jsonArrayToJSONObjectList(json);

    for (JSONObject jsonObject: list) {
      Long value = JSONObjectUtil.getLong(jsonObject, key);
      map.put(value, jsonObject);
    }
    return map;
  }

  /**
   * Convert all elements in JSONArray (of String) to a list of String.
   * <p>
   * A RuntimeException wraps the raised exception.
   * @param json
   * @return
   */
  public static List<String> jsonArrayToStringList(JSONArray json) {
    ArrayList<String> list = new ArrayList<String>(); // Result

    if (json != null) {
      int nElement = json.length();
      for (int i = 0; i < nElement; i++) {
        try {
          String element = json.getString(i);
          list.add(element);
        } catch (JSONException e) {
          throw ErrorUtil.runtimeException(e);
        }
      }
    }

    return list;
  }

  /**
   * Search the first element of the array that has specified
   * <code>value</code> of field <code>name</code>
   * @param json
   * @param fieldName
   * @param searchValue
   * @return
   */
  public static JSONObject search(JSONArray json, String fieldName, String searchValue) {
    JSONObject result = null;

    if (json != null) {
      int nElement = json.length();
      for (int i = 0; i < nElement; i++) {
        try {
          JSONObject element = json.getJSONObject(i);
          String value = element.getString(fieldName);
          if (value == searchValue
              || (searchValue != null && searchValue.equals(value))) {
            result = element;
            break;
          }
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    }

    return result;

  }
}
