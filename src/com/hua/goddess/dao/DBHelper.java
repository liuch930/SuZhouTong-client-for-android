package com.hua.goddess.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hua.goddess.global.Globe;

public class DBHelper extends SQLiteOpenHelper {

	private static final String SQL_BUS_SITE = "create table mySite (siteId integer primary key,siteName varchar,noteGuid varchar)";
	private static final String SQL_BUS_LINE = "create table myLine (lineId integer primary key,guid varchar,LName varchar,LDirection varchar)";

	public DBHelper(Context context) {
		super(context, Globe.DATABASE_NAME, null, Globe.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_BUS_SITE);
		db.execSQL(SQL_BUS_LINE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
