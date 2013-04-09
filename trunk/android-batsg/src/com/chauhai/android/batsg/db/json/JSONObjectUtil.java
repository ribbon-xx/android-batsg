package com.chauhai.android.batsg.db.json;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

import com.chauhai.android.batsg.util.ErrorUtil;

/**
 * JSONObject wrapper.
 *
 * @author umbalaconmeogia
 *
 */
public class JSONObjectUtil {

  /**
   * Wrapper for {@link JSONObject#JSONObject(String)}
   * <p>
   * A RuntimeException wraps the raised exception.
   * @param json
   * @return
   */
  public static JSONObject createJSONObject(String json) {
    try {
      return new JSONObject(json);
    } catch (JSONException e) {
      throw ErrorUtil.runtimeException(e);
    }
  }

  /**
   * Wrapper for {@link JSONObject#getJSONObject(String)}
   * <p>
   * A RuntimeException wraps the raised exception.
   * @param json
   * @param name
   * @return
   */
  public static JSONObject getJSONObject(JSONObject json, String name) {
    try {
      return json.getJSONObject(name);
    } catch (JSONException e) {
      throw ErrorUtil.runtimeException(e);
    }
  }

  /**
   * Wrapper for {@link JSONObject#getJSONArray(String)}
   * <p>
   * A RuntimeException wraps the raised exception.
   * @param json
   * @param name
   * @return
   */
  public static JSONArray getJSONArray(JSONObject json, String name) {
    try {
      return json.getJSONArray(name);
    } catch (JSONException e) {
      throw ErrorUtil.runtimeException(e);
    }
  }


  /**
   * Wrapper for {@link JSONObject#put(JSONArray)}
   * <p>
   * A RuntimeException wraps the raised exception.
   * @param json
   * @param name
   * @return
   */
  public static void put(JSONObject json, String name, JSONArray value) {
    try {
      json.put(name, value);
    } catch (JSONException e) {
      throw ErrorUtil.runtimeException(e);
    }
  }

  /**
   * Wrapper for {@link JSONObject#getString(String)}
   * <p>
   * A RuntimeException wraps the raised exception.
   * @param json
   * @param name
   * @return
   */
  public static String getString(JSONObject json, String name) {
    try {
      return json.getString(name);
    } catch (JSONException e) {
      throw ErrorUtil.runtimeException(e);
    }
  }

  /**
   * Wrapper for {@link JSONObject#getString(String)}
   * @param json
   * @param name
   * @param defValue Value to be returned if does not exist or error occurs.
   * @return
   */
  public static String getString(JSONObject json, String name, String defValue) {
    String result = defValue;
    try {
      result = json.getString(name);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * Wrapper for {@link JSONObject#put(String, String)}
   * <p>
   * A RuntimeException wraps the raised exception.
   * @param json
   * @param name
   * @param value
   */
  public static void put(JSONObject json, String name, String value) {
    try {
      json.put(name, value);
    } catch (JSONException e) {
      throw ErrorUtil.runtimeException(e);
    }
  }

  /**
   * Wrapper for {@link JSONObject#getDouble(String)}
   * <p>
   * A RuntimeException wraps the raised exception.
   * @param json
   * @param name
   * @return
   */
  public static double getDouble(JSONObject json, String name) {
    try {
      return json.getDouble(name);
    } catch (JSONException e) {
      throw ErrorUtil.runtimeException(e);
    }
  }

  /**
   * Wrapper for {@link JSONObject#put(String, int)}
   * <p>
   * A RuntimeException wraps the raised exception.
   * @param json
   * @param name
   * @param value
   */
  public static void put(JSONObject json, String name, int value) {
    try {
      json.put(name, value);
    } catch (JSONException e) {
      throw ErrorUtil.runtimeException(e);
    }
  }

  /**
   * Wrapper for {@link JSONObject#getInt(String)}
   * <p>
   * A RuntimeException wraps the raised exception.
   * @param json
   * @param name
   * @return
   */
  public static int getInt(JSONObject json, String name) {
    try {
      return json.getInt(name);
    } catch (JSONException e) {
      throw ErrorUtil.runtimeException(e);
    }
  }

  /**
   * Wrapper for {@link JSONObject#getLong(String)}
   * <p>
   * A RuntimeException wraps the raised exception.
   * @param json
   * @param name
   * @return
   */
  public static long getLong(JSONObject json, String name) {
    try {
      return json.getLong(name);
    } catch (JSONException e) {
      throw ErrorUtil.runtimeException(e);
    }
  }

  /**
   * Wrapper for {@link JSONObject#put(String, long)}
   * <p>
   * A RuntimeException wraps the raised exception.
   * @param json
   * @param name
   * @param value
   */
  public static void put(JSONObject json, String name, long value) {
    try {
      json.put(name, value);
    } catch (JSONException e) {
      throw ErrorUtil.runtimeException(e);
    }
  }

  /**
   * Wrapper for {@link JSONObject#getBoolean(String)}
   * <p>
   * A RuntimeException wraps the raised exception.
   * @param json
   * @param name
   * @return
   */
  public static boolean getBoolean(JSONObject json, String name) {
    try {
      return json.getBoolean(name);
    } catch (JSONException e) {
      throw ErrorUtil.runtimeException(e);
    }
  }

  /**
   * Wrapper for {@link JSONObject#put(String, boolean)}
   * <p>
   * A RuntimeException wraps the raised exception.
   * @param json
   * @param name
   * @param value
   */
  public static void put(JSONObject json, String name, boolean value) {
    try {
      json.put(name, value);
    } catch (JSONException e) {
      throw ErrorUtil.runtimeException(e);
    }
  }

  /**
   * Convert json to bundle.
   * @param json All fields of json object should have value of string.
   * @return
   */
  public static Bundle jsonObjectToBundle(JSONObject json) {
    Bundle bundle = new Bundle();
    for (Iterator<?> it = json.keys(); it.hasNext(); ) {
      String key = it.next().toString();
      bundle.putString(key, JSONObjectUtil.getString(json, key));
    }
    return bundle;
  }

  /**
   * Wrapper for {@link JSONObject#get(String)}
   * <p>
   * A RuntimeException wraps the raised exception.
   * @param json
   * @param name
   * @return
   */
  public static Object get(JSONObject json, String name) {
    try {
      return json.get(name);
    } catch (JSONException e) {
      throw ErrorUtil.runtimeException(e);
    }
  }
}
