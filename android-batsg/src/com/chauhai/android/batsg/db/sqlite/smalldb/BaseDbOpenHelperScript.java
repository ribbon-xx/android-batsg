package com.chauhai.android.batsg.db.sqlite.smalldb;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chauhai.android.batsg.util.ErrorUtil;
import com.chauhai.android.batsg.util.FileUtil;

public class BaseDbOpenHelperScript extends SQLiteOpenHelper {

  private static final String TAG = "BaseDbOpenHelperScript";

	private Context context;

  public BaseDbOpenHelperScript(Context context, String name,
      CursorFactory factory, int version) {
    super(context, name, factory, version);
    this.context = context;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    try {
      String[] sqls = getCreateDbSql().split(";");
      for (String sql: sqls) {
        String s = sql.trim();
        if (!"".equals(s)) {
          Log.v(TAG, "exec SQL: " + s);
          db.execSQL(sql);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw ErrorUtil.runtimeException(e);
    }
  }

  /**
   * Get the sql commands to create tables of the database.
   * This get the content of assets/db/createDb.sql by default.
   * @return All create table commands.
   * @throws IOException
   */
  protected String getCreateDbSql() throws IOException {
    InputStream input = context.getAssets().open(getCreateDbPathInAssets());
    String sql = FileUtil.getContents(input);
    return sql;
  }

  /**
   * Get the path of script file to create tables of the database in assets.
   * @return Return db/createDb.sql (for the file assets/db/createDb.sql) by default.
   */
  protected String getCreateDbPathInAssets() {
    return "db/createDb.sql";
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.v(TAG,
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data.");
    try {
      String[] sqls = getDropDbSql().split(";");
      for (String sql: sqls) {
        String s = sql.trim();
        if (!"".equals(s)) {
          Log.v(TAG, "exec SQL: " + s);
          db.execSQL(sql);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw ErrorUtil.runtimeException(e);
    }
    onCreate(db);
  }

  /**
   * Get the sql commands to drop tables of the database.
   * This get the content of assets/db/dropDb.sql by default.
   * @return All drop table commands.
   * @throws IOException
   */
  protected String getDropDbSql() throws IOException {
    InputStream input = context.getAssets().open(getDropDbPathInAssets());
    String sql = FileUtil.getContents(input);
    return sql;
  }

  /**
   * Get the path of script file to create tables of the database in assets.
   * @return Return db/dropDb.sql (for the file assets/db/dropDb.sql) by default.
   */
  protected String getDropDbPathInAssets() {
    return "db/dropDb.sql";
  }
}