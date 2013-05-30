package com.chauhai.android.batsg.demo.db.sqlite.db;

import android.content.Context;

import com.chauhai.android.batsg.db.sqlite.smalldb.BaseDbOpenHelperScript;

public class DbOpenHelper extends BaseDbOpenHelperScript {

	/**
	 * Database file name.
	 */
	private static final String DATABASE_NAME = "demo.sqlite";

  /**
   * Increase DATABASE_VERSION will make Android to upgrade the database.
   */
	private static final int DATABASE_VERSION = 1;

  public DbOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
}