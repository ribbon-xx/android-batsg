package com.chauhai.android.batsg.demo.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.chauhai.android.batsg.activity.BaseSplashActivity;
import com.chauhai.android.batsg.demo.AndroidBatsgDemoActivity;
import com.chauhai.android.batsg.demo.R;

public class SplashActivity extends BaseSplashActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ImageView imageView = new ImageView(this);
    imageView.setImageResource(R.drawable.ic_launcher);
    setContentView(imageView);
  }

  @Override
  protected void gotoNextActivity() {
    gotoNextActivityFading(AndroidBatsgDemoActivity.class);
  }
}
