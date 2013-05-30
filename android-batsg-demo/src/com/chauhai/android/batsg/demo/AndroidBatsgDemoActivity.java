package com.chauhai.android.batsg.demo;

import android.os.Bundle;
import android.view.View;

import com.chauhai.android.batsg.activity.BaseActivity;
import com.chauhai.android.batsg.demo.activity.RoundBoxActivity;
import com.chauhai.android.batsg.demo.activity.SplashActivity;
import com.chauhai.android.batsg.demo.db.sqlite.DbDemoActivity;
import com.chauhai.android.batsg.demo.location.GetLocationDemoActivity;
import com.chauhai.android.batsg.demo.widget.ProportionalLayoutDemoActivity;
import com.chauhai.android.batsg.demo.widget.RoundedCornerImageViewDemoActivity;
import com.chauhai.android.batsg.demo.widget.SegmentedControlDemoActivity;

public class AndroidBatsgDemoActivity extends BaseActivity {

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
  }

  /**
   * Process when user clicks a button.
   * @param v
   */
  public void onClickButton(View v) {
    int id = v.getId();
    switch (id) {
    case R.id.SplashActivity:
      openActivity(SplashActivity.class);
      break;
    case R.id.GetLocation:
      openActivity(GetLocationDemoActivity.class);
      break;
    case R.id.SegmentedControl:
      openActivity(SegmentedControlDemoActivity.class);
      break;
    case R.id.ProportionalLayout:
      openActivity(ProportionalLayoutDemoActivity.class);
      break;
    case R.id.RoundedCornerImageView:
      openActivity(RoundedCornerImageViewDemoActivity.class);
      break;
    case R.id.RoundBox:
      openActivity(RoundBoxActivity.class);
      break;
    case R.id.Db:
      openActivity(DbDemoActivity.class);
      break;
    }
  }
}