package com.chauhai.android.batsg.db.json;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.chauhai.android.batsg.util.DebugUtil;

/**
 * Base class to store and retrieve StyleNavi data into SharedPreferences.
 *
 * @author umbalaconmeogia
 *
 */
public class BasePrefData {

  protected Context context;

  private SharedPreferences settings;

  private String dataField;

  protected BasePrefData(Context context) {
    this.context = context;
    this.settings = PreferenceManager.getDefaultSharedPreferences(context);
  }

  /**
   * Get the class name with the first character to be lower case.
   * Subclass may override this method for more efficient.
   * @return
   */
  protected String dataField() {
    // Initiate dataField variable if null.
    if (dataField == null) {
      dataField = getClass().getSimpleName();
      dataField = Character.toLowerCase(
          dataField.charAt(0)) + (dataField.length() > 1 ? dataField.substring(1) : "");
    }
    return dataField;
  }

  /**
   * Get data from SharePreferences.
   * @return
   */
  protected synchronized String getPrefData() {
    return settings.getString(dataField(), null);
  }

  /**
   * Put data into SharedPreferences.
   * @param data
   */
  protected synchronized void setPrefData(String data) {
    DebugUtil.d("setPrefData() " + dataField() + " => " + data);
    SharedPreferences.Editor editor = settings.edit();
    editor.putString(dataField(), data);
    // Commit the edits!
    editor.commit();
  }

  /**
   * Clear saved data.
   */
  public synchronized void clearData() {
    DebugUtil.d("clearData() " + dataField());
    SharedPreferences.Editor editor = settings.edit();
    editor.remove(dataField());
    // Commit the edits!
    editor.commit();
  }
}
