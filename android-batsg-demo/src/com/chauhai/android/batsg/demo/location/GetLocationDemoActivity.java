package com.chauhai.android.batsg.demo.location;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

import com.chauhai.android.batsg.location.GetLocation;
import com.chauhai.android.batsg.location.GetLocation.OnGetLocationListener;

/**
 * Demonstrate the usage of GetLocation to detect the current GPS location.
 * <p>
 * This demo app detects the location, then displays it on the screen.
 * <p>
 * If the GPS function is disabled, then a message dialog is shown.
 * The user can enable it by the Android option setting screen,
 * then return back to the application.
 * <p>
 * If the user refuses to enable GPS location, a notifictaion message
 * about it is shown, too.
 * @author umbalaconmeogia
 */
public class GetLocationDemoActivity extends Activity {

  /**
   * To get the geo-location.
   */
  private GetLocation getLocation;

  /**
   * To display message on the screen.
   */
  private TextView message;

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      message = new TextView(this);
      setContentView(message);

      getLocation = new GetLocation(this,
          new GetLocationListener(),
          new AlertDialogCancelListener());
  }

  /**
   * Start detect GPS location.
   */
  @Override
  protected void onResume() {
    super.onResume();

    message.setText("Getting the GPS location");
    getLocation.startPositioning();
  }

  /**
   * Try to stop GetLocation.
   */
  @Override
  protected void onPause() {
    getLocation.stopPositioning();
    super.onPause();
  }

  /**
   * Processed when get the GPS location successfully.
   */
  class GetLocationListener implements OnGetLocationListener {
    /**
     * Alert the location value.
     */
    public void onGetLocation(Location location) {
      message.setText(
          String.format("Location: longitude = %f, latitude = %f",
              location.getLongitude(), location.getLatitude()));
    }
  }

  /**
   * Show message when user does not enable GPS.
   */
  class AlertDialogCancelListener implements OnCancelListener {
    public void onCancel(DialogInterface arg0) {
      message.setText("GPS is disabled");
    }
  }
}
