package com.chauhai.android.batsg.location;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Get current location.
 * <p>
 * This class help to get the current GPS location, and involve some
 * processing with it. Basically, the GPS location processing flow is
 * as following:
 * <ul>
 *   <li>
 *     The application check if GPS function is enable.
 *     If it is disabled, then a message dialog is displayed, notice
 *     the user about it. The user can goto the setting screen to enabled
 *     GPS function, then return to the application by the BACK button.
 *     If the user refuses to enable GPS, then it is friendly to report
 *     again about it.
 *   </li>
 *   <li>
 *     If GPS is enabled, then the application detect the location and
 *     doing something with it.
 *   </li>
 * </ul>
 * This <code>GetLocation<code> helps you to implement this flow.
 * The basic implementation is as following:
 * <ul>
 *   <li>
 *     Create a <code>GetLocation.OnGetLocationListener</code> object,
 *     to process the get location information when it is get.
 *   </li>
 *   <li>
 *     Create a <code>DialogInterface.OnCancelListener</code> if you want
 *     to do something (display message, for example) when user refuses
 *     to enable GPS function.
 *   </li>
 *   <li>
 *     Create a <code>GetLocation</code> object (in the activity constructor
 *     or <code>onCreate()</code>).
 *   </li>
 *   <li>
 *     Call {@link GetLocation#startPositioning()} in the activity
 *     <code>onResume()</code> (this is for continuing detect the location
 *     when user return back from the GPS setting screen).
 *   </li>
 *   <li>
 *     Call {@link GetLocation#stopPositioning()} in the activity
 *     <code>onPause()</code> for the case the user click BACK button
 *     while GPS location is detecting.
 *   </li>
 * </ul>
 * <p>
 * @author umbalaconmeogia
 */
public class GetLocation {

  private Context context;

  private LocationManager locationManager;

  private MyLocationListener gpsLocationListener;

  private MyLocationListener networkLocationListener;

  private OnCancelListener alertDialogOnCancelListener;

  private OnGetLocationListener onGetLocationListener;

  private boolean positioning = false;

  private String alertMessage = "GPS service is disabled! Would you line to enable it?";

  private String alertSettingButtonLabel = "Enable GPS";

  private String alertCancelButtonLabel = "Cancel";

  /**
   * Set the alert message when GPS function is disabled.
   * @param alertMessage The message string.
   */
  public void setAlertMessage(String alertMessage) {
    this.alertMessage = alertMessage;
  }

  /**
   * Set the alert message when GPS function is disabled.
   * @param alertMessageResId The message string resource id.
   */
  public void setAlertMessage(int alertMessageResId) {
    setAlertMessage(context.getString(alertMessageResId));
  }

  /**
   * Set the label of the button to open GPS setting screen.
   * @param label The label string.
   */
  public void setAlertSettingButtonLabel(String label) {
    this.alertSettingButtonLabel = label;
  }

  /**
   * Set the label of the button to open GPS setting screen.
   * @param labelResId The label string resource id.
   */
  public void setAlertSettingButtonLabel(int labelResId) {
    setAlertSettingButtonLabel(context.getString(labelResId));
  }

  /**
   * Set the label of the button to not open GPS setting screen.
   * @param label The label string.
   */
  public void setAlertCancelButtonLabel(String label) {
    this.alertCancelButtonLabel = label;
  }

  /**
   * Set the label of the button to not open GPS setting screen.
   * @param labelResId The label string resource id.
   */
  public void setAlertCancelButtonLabel(int labelResId) {
    setAlertCancelButtonLabel(context.getString(labelResId));
  }

  /**
   * Create GetLocation object.
   * @param context
   * @param onGetLocationListener Processing when the location is get.
   * @param alertDialogOnCancelListener Processing when the user does not
   *        want to enable GPS function. May be <code>null</code>.
   */
  public GetLocation(Context context,
      OnGetLocationListener onGetLocationListener,
      OnCancelListener alertDialogOnCancelListener) {
    this.context = context;
    this.onGetLocationListener = onGetLocationListener;
    this.alertDialogOnCancelListener = alertDialogOnCancelListener;
  }

  /**
   * Start positioning.
   */
  public void startPositioning() {
    locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
        !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
      alertLocationServiceDisabled();
    } else {
      gpsLocationListener = new MyLocationListener();
      networkLocationListener = new MyLocationListener();
      positioning = true;
      locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsLocationListener);
      locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, networkLocationListener);
    }
  }

  /**
   * Stop positioning.
   * <p>
   * This method should be called in Activity#onPause(), too.
   */
  synchronized public void stopPositioning() {
    if (positioning) {
      positioning = false;
      locationManager.removeUpdates(gpsLocationListener);
      locationManager.removeUpdates(networkLocationListener);
      locationManager = null;
      gpsLocationListener = null;
      networkLocationListener = null;
    }
  }

  /**
   * Alert the user that the Location service is disabled,
   * allow the user a chance to enable it.
   */
  private void alertLocationServiceDisabled() {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setMessage(alertMessage)
        .setCancelable(false)
        .setPositiveButton(alertSettingButtonLabel, new OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            showLocationSourceSetting();
          }
        })
        .setNegativeButton(alertCancelButtonLabel, new OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
          }
        });
    AlertDialog dialog = builder.create();
    dialog.setOnCancelListener(alertDialogOnCancelListener);
    dialog.show();
  }

  /**
   * Display location source setting to enable GPS.
   */
  private void showLocationSourceSetting() {
    Intent intent = new Intent(
        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    context.startActivity(intent);
  }

  /**
   * Process when get the location.
   * @param location
   */
  synchronized private void makeUseOfNewLocation(Location location) {
    if (positioning) {
      stopPositioning();

      onGetLocationListener.onGetLocation(location);
    }
  }

  public static interface OnGetLocationListener {
    /**
     * Called when get the location.
     * @param location
     */
    public void onGetLocation(Location location);
  }

  class MyLocationListener implements LocationListener {

    @Override
    public void onLocationChanged(Location location) {
      // Called when a new location is found by the location provider.
      makeUseOfNewLocation(location);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
  }
}
