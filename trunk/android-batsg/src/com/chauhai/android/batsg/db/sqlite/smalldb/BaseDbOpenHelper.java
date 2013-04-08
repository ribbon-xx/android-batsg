package com.chauhai.android.batsg.db.sqlite.smalldb;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Class to manipulate the database file.
 * The database is initiated by copying from a file in the assets folder.
 *
 * @author umbalaconmeogia
 */
public abstract class BaseDbOpenHelper extends SQLiteOpenHelper {

  private static final String TAG = "BaseDbOpenHelper";

  private static int ACCESS_TYPE_READ = 0;

  private static int ACCESS_TYPE_WRITE = 1;

  /**
   * Set if DB needs to be initialized (copy from asset file).
   */
  private boolean shouldBeInitialized = false;

  private Context context;

  public BaseDbOpenHelper(Context context, String name,
      CursorFactory factory, int version) {
    super(context, name, factory, version);
    this.context = context;
  }

  /**
   * Get the directory that contains the source database file in the assets folder.
   * <p>
   * For example, if the source database file is <em>assets/db/company.sqlite</em>,
   * then <em>getAssetsDbDir()</em> should return <em>db</em>.
   * Notice: Method name has been changed from getDatabaseDir to getAssetsDbDir.
   * @return
   */
  public abstract String getAssetsDbDir();

  /**
   * Get the source database file name.
   * <p>
   * For example, if the source database file is <em>assets/db/company.sqlite</em>,
   * then <em>getAssetsDbName()</em> should return <em>company.sqlite</em>.
   * Notice: Method name has been changed from getDatabaseName to getAssetsDbName
   * to avoid conflicting with same method name in API 14.
   * @return
   */
  public abstract String getAssetsDbName();

  @Override
  public void onCreate(SQLiteDatabase db) {
    Log.i(TAG, "onCreate");
    // Database should be initialized with data from the assets folder.
    shouldBeInitialized = true;
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.i(TAG, "onUpgrade from version " + oldVersion + " to version " + newVersion);
    // Database should be initialized with data from the assets folder.
    shouldBeInitialized = true;
  }

  /**
   * Copy data base from assets folder if necessary.
   */
  @Override
  public synchronized SQLiteDatabase getWritableDatabase() {
    Log.d(TAG, "getWritableDatabase");
    // This will call onCreate or onUpdate if necessary.
    SQLiteDatabase database = super.getWritableDatabase();
    // If onCreate or onUpdate find that the database should be update.
    if (shouldBeInitialized) {
      database.close(); // Close the opened database.
      database = copyDatabase(ACCESS_TYPE_WRITE);
    }
    return database;
  }

  /**
   * Copy data base from assets folder if necessary.
   */
  @Override
  public synchronized SQLiteDatabase getReadableDatabase() {
    Log.d(TAG, "getReadableDatabase");
    // This will call onCreate or onUpdate if necessary.
    SQLiteDatabase database = super.getReadableDatabase();
    // If onCreate or onUpdate find that the database should be update.
    if (shouldBeInitialized) {
      database.close(); // Close the opened database.
      database = copyDatabase(ACCESS_TYPE_READ);
    }
    return database;
  }

  /**
   * Copy the database file from asset and open it.
   * @param accessType 0 for readable, 1 for writable.
   * @return
   */
  private SQLiteDatabase copyDatabase(int accessType) {
    try {
      copyDatabase(); // Copy the database from asset
      // Open it again. onCreate is called.
      Log.d(TAG, "onCreate is called again");
      SQLiteDatabase database = accessType == ACCESS_TYPE_READ ?
          super.getReadableDatabase() : super.getWritableDatabase();
      Log.d(TAG, "Set shouldBeInitialized to false");
      shouldBeInitialized = false; // Reset shouldBeInitialized.
      return database;
    } catch (IOException e) {
      Log.e(TAG, e.getMessage(), e);
      throw new SQLiteException(e.getMessage());
    }
  }

  /**
   * Copy the database file from asset to the SQLite database folder.
   * @throws IOException
   */
  private void copyDatabase() throws IOException {
    String outFileName = dbFilePath();
    Log.i(TAG, "Copy database to: " + outFileName);

    // Open the local db as the input stream
    InputStream input = context.getAssets().open(getAssetsDbDir() + "/" + getAssetsDbName());

    // Open the current db as the output stream
    OutputStream output = new FileOutputStream(outFileName);

    // Transfer bytes from the input file to the output file
    byte[] buffer = new byte[1024];
    int length;
    while ((length = input.read(buffer)) > 0){
      output.write(buffer, 0, length);
    }

    // Close the streams
    output.flush();
    output.close();
    input.close();
  }

  /**
   * Get the path to the database file.
   * @return
   */
  private String dbFilePath() {
    return context.getFilesDir().getPath() + "/databases/" + getAssetsDbName();
  }
}