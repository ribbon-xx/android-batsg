package com.chauhai.android.batsg.activity;

import android.app.Activity;
import android.os.Handler;

import com.chauhai.android.batsg.util.ActivityUtil;

/**
 * Base class to create splash activity, which displays a splash
 * screen then move to the next activity.
 * <p>
 * In the simplest case, the subclass implement the following methods
 * to implement a splash activity.
 * <ul>
 *   <li><code>onCreate</code>: to set the activity layout.</li>
 *   <li>{@link BaseSplashActivity#gotoNextActivity() <code>gotoNextActivity</code>}: to open the next activity,
 *     for example, by calling <code>gotoNextActivityFading(NextActivity.class)</code>.</li>
 * </ul>
 *
 * An example of a splash activity.
 * <pre>
 * public class SplashActivity extends BaseSplashActivity {
 *   public void onCreate(Bundle savedInstanceState) {
 *     super.onCreate(savedInstanceState);
 *     setContentView(R.layout.splash);
 *   }
 *
 *   protected void gotoNextActivity() {
 *     gotoNextActivityFading(MainMenuActivity.class);
 *   }
 * }
 * </pre>
 * @author umbalaconmeogia
 *
 */
public abstract class BaseSplashActivity extends BaseActivity {

  private boolean displayed = false;

  /**
   * Display splash screen for the first time called,
   * and finish the activity for the second time.
   */
  @Override
  protected void onResume()
  {
    super.onResume();

    // Close this activity if the splash has been displayed before.
    if (displayed) {
      this.finish();
    } else {
      // Mark that the splash is displayed.
      displayed = true;

      // Wait and go to the next activity.
      displaySplashAndGotoNextActivity();
    }
  }

  /**
   * Empty the layout to release the resource.
   * This activity will never be rendered again.
   */
  @Override
  protected void onPause() {
    ActivityUtil.emptyView(this); // Empty view to free memory.
    super.onPause();
  }

  /**
   * Display the splash picture.
   */
  private void displaySplashAndGotoNextActivity() {
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        gotoNextActivity();
      }
    }, displayTimeInMillisecond());
  }

  /**
   * The time to display the splash.
   * <p>
   * This is 2 seconds by default. Subclass can override this to
   * adjust the time.
   * @return
   */
  protected int displayTimeInMillisecond() {
    return 2000;
  }

  /**
   * Goto the next activity after displaying splash.
   * <p>
   * Subclass should override this method to implement
   * the call to the next activity.
   */
  protected abstract void gotoNextActivity();

  /**
   * Default method to open an activity, by fading out the splash
   * activity and fading in the next activity.
   *
   * @param activityClass
   */
  protected void gotoNextActivityFading(Class<? extends Activity> activityClass) {
    openActivity(activityClass);
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
  }
}
