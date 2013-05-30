package com.chauhai.android.batsg.demo.db.sqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.chauhai.android.batsg.db.sqlite.smalldb.BaseDbAdapter;
import com.chauhai.android.batsg.db.sqlite.smalldb.BaseDbBean;
import com.chauhai.android.batsg.db.sqlite.smalldb.BaseDbBeanClassFactory;
import com.chauhai.android.batsg.demo.db.sqlite.bean.DbBeanClassFactory;

public class DbAdapter<A extends BaseDbAdapter<A, B>, B extends BaseDbBean>
		extends BaseDbAdapter<A, B> {

  private static DbOpenHelper commonDbOpenHelper;

	public DbAdapter(Context context) {
		super(context);
	}

	@Override
	public SQLiteOpenHelper createDbOpenHelper() {
	  if (commonDbOpenHelper == null) {
	    commonDbOpenHelper = new DbOpenHelper(context);
	  }
		return commonDbOpenHelper;
	}

	/**
	 * The DbBeanClassFactory.
	 */
	public static DbBeanClassFactory dbBeanClassFactory = new DbBeanClassFactory();

	@Override
	protected BaseDbBeanClassFactory dbBeanClassFactory() {
		return dbBeanClassFactory;
	}
}
