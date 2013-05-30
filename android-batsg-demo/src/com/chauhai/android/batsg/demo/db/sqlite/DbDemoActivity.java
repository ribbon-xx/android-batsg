package com.chauhai.android.batsg.demo.db.sqlite;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chauhai.android.batsg.demo.R;
import com.chauhai.android.batsg.demo.db.sqlite.bean.BCategory;
import com.chauhai.android.batsg.demo.db.sqlite.db.DCategory;

public class DbDemoActivity extends Activity {
  private static final String TAG = "DbActivity";

  private TextView log;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_db_demo);
    log = (TextView) findViewById(R.id.log);
  }

  public void clickInsertCategory(View v) {
    // Get the last category.
    BCategory bCategory = DCategory.findWhere(this, DCategory.class, "1 = 1 ORDER BY _id DESC");
    // Create new category, with name is next to the last category.
    if (bCategory == null) {
      bCategory = new BCategory();
      bCategory._id = 0;
    }
    bCategory.name = "Category " + (bCategory._id + 1);
    bCategory = DCategory.insert(this, DCategory.class, bCategory);
    Log.d(TAG, log("Insert " + bCategory));
  }

  public void clickRemoveLastCategory(View v) {
    // Get the last category.
    BCategory bCategory = DCategory.findWhere(this, DCategory.class, "1 = 1 ORDER BY _id DESC");
    // Remove the last category and its items.
    if (bCategory != null) {
      DCategory dCategory = new DCategory(this).open();
      dCategory.remove(bCategory._id);
      dCategory.close();
      Log.d(TAG, log("Remove " + bCategory));
    } else {
      Log.d(TAG, log("There is no category."));
    }
  }

  public void clickInsertItemToLastCategory(View v) {

  }

  public void clickDeleteFirstOfLastCategory(View v) {

  }

  private String log(String message) {
    log.setText(message);
    return message;
  }
}
