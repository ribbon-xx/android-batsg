package com.chauhai.android.batsg.db.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.chauhai.android.batsg.util.ErrorUtil;

/**
 * Base class of data based on JSONArray.
 *
 * @author umbalaconmeogia
 *
 */
public class BaseJSONArray extends BasePrefData {

  protected JSONArray jsonData;

  protected BaseJSONArray(Context context) {
    super(context);
    parseJSON(getPrefData());
  }

  /**
   * Parse a string to jsonArray.
   * @param json
   */
  private void parseJSON(String json) {
    if (json != null) {
      try {
        jsonData = new JSONArray(json);
      } catch (JSONException e) {
        throw ErrorUtil.runtimeException(e);
      }
    }
  }

  /**
   * @return JSONArray object if data exists, null otherwise.
   */
  public synchronized JSONArray getJSONData() {
    return jsonData;
  }

  /**
   * Set jsonData.
   * @param json
   */
  public synchronized void setJSONData(JSONArray json) {
    String jsonString = json == null ? null : json.toString();
    setPrefData(jsonString);
    jsonData = json;
  }

  /**
   * Convert all elements in JSONArray to a list of JSONObject.
   * @return
   */
  public List<JSONObject> listAll() {
    ArrayList<JSONObject> jsonList = new ArrayList<JSONObject>();

    JSONArray jsonArray = getJSONData();
    if (jsonArray != null) {
      int nElement = jsonArray.length();
      for (int i = 0; i < nElement; i++) {
        try {
          JSONObject element = jsonArray.getJSONObject(i);
          jsonList.add(element);
        } catch (JSONException e) {
          throw ErrorUtil.runtimeException(e);
        }
      }
    }

    return jsonList;
  }

  /**
   * Gather available elements of jsonData (JSONArray) to list of JSONObject.
   * <p>
   * It decide whether an element is available by calling {@link #jsonElementAvaiable(JSONObject)}
   * @return
   */
  public List<JSONObject> listAvaiable() {
    ArrayList<JSONObject> jsonList = new ArrayList<JSONObject>();

    JSONArray jsonArray = getJSONData();
    if (jsonArray != null) {
      int nElement = jsonArray.length();
      for (int i = 0; i < nElement; i++) {
        try {
          JSONObject element = jsonArray.getJSONObject(i);
          if (jsonElementAvaiable(element)) {
            jsonList.add(element);
          }
        } catch (JSONException e) {
          throw ErrorUtil.runtimeException(e);
        }
      }
    }

    return jsonList;
  }

  /**
   * Check if an element of a jsonArray is available (for example, isDeleted
   * is false).
   * <p>
   * This method is called in {@link #listAvaiable()}
   * @param json the element to be checked.
   * @return true if it is available, false otherwise.
   */
  protected boolean jsonElementAvaiable(JSONObject json) {
    return true;
  }

  @Override
  public void clearData() {
    super.clearData();
    jsonData = null;
  }

  /**
   * Search the first element that has specified <code>value</code> of field <code>name</code>
   * @param fieldName
   * @param searchValue
   * @return
   */
  public JSONObject search(String fieldName, String searchValue) {
    return JSONArrayUtil.search(jsonData, fieldName, searchValue);
  }
}
