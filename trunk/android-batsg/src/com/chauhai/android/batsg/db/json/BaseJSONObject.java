package com.chauhai.android.batsg.db.json;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.chauhai.android.batsg.util.ErrorUtil;

/**
 * Base class of data based on JSONObject.
 *
 * @author umbalaconmeogia
 *
 */
public class BaseJSONObject extends BasePrefData {

  protected JSONObject jsonData;

  protected BaseJSONObject(Context context) {
    super(context);
    parseJSON(getPrefData());
  }

  /**
   * Parse a string to jsonObject.
   * @param json
   */
  private void parseJSON(String json) {
    if (json != null) {
      try {
        jsonData = new JSONObject(json);
      } catch (JSONException e) {
        throw ErrorUtil.runtimeException(e);
      }
    }
  }

  /**
   * @return JSONObject object if data exists, null otherwise.
   */
  public synchronized JSONObject getJSONData() {
    return jsonData;
  }

  public synchronized void setJSONData(JSONObject json) {
    String jsonString = json == null ? null : json.toString();
    setPrefData(jsonString);
    jsonData = json;
  }

  /**
   * @param data name/value pairs.
   */
  public synchronized void put(String... data) {
    int nData = data.length;
    for (int i = 0; i < nData; i += 2) {
      JSONObjectUtil.put(jsonData, data[i], data[i + 1]);
    }
    setJSONData(jsonData);
  }

  public synchronized void put(String name, int value) {
    JSONObjectUtil.put(jsonData, name, value);
    setJSONData(jsonData);
  }

  public synchronized void put(String name, boolean value) {
    JSONObjectUtil.put(jsonData, name, value);
    setJSONData(jsonData);
  }

  @Override
  public void clearData() {
    super.clearData();
    jsonData = null;
  }

}
