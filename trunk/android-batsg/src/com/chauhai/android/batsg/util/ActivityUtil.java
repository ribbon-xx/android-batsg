package com.chauhai.android.batsg.util;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Utility functions on Activity.
 */
public class ActivityUtil {

  /**
   * Display a Toast message and close the activity.
   * @param activity
   * @param errorMessage
   */
  public static void closeOnError(Activity activity, String errorMessage) {
    Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show();
    activity.finish();
  }

  /**
   * TODO: Check if this is true?<br/>
   * Set the view of activity to empty, to free memory.
   * {@link System#gc()} is called, too.
   * <p>
   * This method is intended to be used in the heavy graphics activity.
   * It will call <code>emptyView()</code> in <code>onPause()</code>,
   * and <code>setContentView()</code> in <code>onResume()</code>.
   * @param activity
   */
  public static void emptyView(Activity activity) {
    ((ViewGroup) activity.findViewById(android.R.id.content)).removeAllViews();
    System.gc();
  }
}
