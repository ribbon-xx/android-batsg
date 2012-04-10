package com.chauhai.android.batsg.demo.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.chauhai.android.batsg.demo.R;
import com.chauhai.android.batsg.util.DebugUtil;
import com.chauhai.android.batsg.widget.SegmentedControlView;

public class SegmentedControlDemoActivity extends Activity
    implements OnCheckedChangeListener {

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.segmented_control_demo);

      // Set segmented control listener.
      ((SegmentedControlView) findViewById(R.id.segmented_control_1))
          .setOnCheckedChangeListener(this);
      ((SegmentedControlView) findViewById(R.id.segmented_control_2))
          .setOnCheckedChangeListener(this);
      ((SegmentedControlView) findViewById(R.id.segmented_control_3))
          .setOnCheckedChangeListener(this);
  }

  public void onCheckedChanged(RadioGroup group, int checkedId) {
    DebugUtil.toast(this, "Click on " + ((RadioButton) findViewById(checkedId)).getText());
  }

}
