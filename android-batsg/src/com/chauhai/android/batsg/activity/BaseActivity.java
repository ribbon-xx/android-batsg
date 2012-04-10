package com.chauhai.android.batsg.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;

public class BaseActivity extends Activity {

  /**
   * Open specified activity.
   * @param activityClass Activity to open.
   * @param flags flag set to Intent object.
   */
  public void openActivity(Class<? extends Activity> activityClass, int... flags) {
    openActivity(new Intent(this, activityClass), flags);
  }

  /**
   * Open specified activity.
   * @param intent Intent to open.
   * @param flags flag set to Intent object.
   */
  public void openActivity(Intent intent, int... flags) {
    for (int flag: flags) {
      intent.addFlags(flag);
    }
    startActivity(intent);
  }

  /**
   * Alternative the BACK menu button process.
   */
  public void emulatePressingBackButton() {
    emulatePressingBackButton(this);
  }


  /**
   * Alternative the BACK menu button process.
   * @param activity
   */
  public static void emulatePressingBackButton(Activity activity) {
    KeyEvent backEvtDown = new KeyEvent(KeyEvent.ACTION_DOWN,
        KeyEvent.KEYCODE_BACK);
    KeyEvent backEvtUp = new KeyEvent(KeyEvent.ACTION_UP,
        KeyEvent.KEYCODE_BACK);
    activity.dispatchKeyEvent(backEvtDown);
    activity.dispatchKeyEvent(backEvtUp);
  }

}