package com.chauhai.android.batsg.demo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.chauhai.android.batsg.demo.R;
import com.chauhai.android.batsg.util.DebugUtil;

public class RoundBoxActivity extends Activity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.round_box);

    String[] items = new String[] {"One", "Two", "Three"};
    CustomizeSpinner firstSpinner = new CustomizeSpinner(findViewById(R.id.first_button),
      (TextView) findViewById(R.id.first_button_spinner),
      "First spinner", items);
    firstSpinner.setSelection(1);

    CustomizeSpinner secondSpinner = new CustomizeSpinner(findViewById(R.id.middle_button),
        (TextView) findViewById(R.id.middle_button),
        "Second spinner", items);
    secondSpinner.setSelection(1);
  }

  public void onClickFirstItem(View v) {
    DebugUtil.toast(this, "First item");
  }

  public void onClickMiddleItem(View v) {
    DebugUtil.toast(this, "Middle item " + v.getId());
  }

  static class CustomizeSpinner {

    private AlertDialog dialog;

    public CustomizeSpinner(View clickView, final TextView textView, String title, final String[] items) {
      dialog = new AlertDialog.Builder(clickView.getContext())
      .setTitle(title)
      .setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
          dialog.dismiss();
          checkedItem = which;
          textView.setText(items[which]);
        }
      }).create();

      clickView.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
          dialog.show();
        }
      });
    }

    private int checkedItem = -1;

    public void setSelection(int position) {
      this.checkedItem = position;
    }
  }
}
