package com.hua.goddess.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.hua.goddess.vo.BusLineDetailVo;
import com.hua.goddess.vo.BusSiteVo;

public class BusCollectDao {
	private DBHelper dbHelper;

	public BusCollectDao(DBHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	// 添加站点信息
	public void addBusSite(BusSiteVo vo) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("siteName", vo.getName());
		cv.put("noteGuid", vo.getNoteGuid());
		try {
			db.insert("mySite", null, cv);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
				db = null;
			}
		}
	}

	// 查询所以的站点信息
	public ArrayList<BusSiteVo> getAllSiteData() {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String sql = "SELECT siteName,noteGuid FROM mySite order by siteId desc";
		ArrayList<BusSiteVo> list = new ArrayList<BusSiteVo>();
		Cursor cur = null;
		try {
			cur = db.rawQuery(sql, null);
			if (cur != null && cur.getCount() > 0) {
				for (int i = 0; i < cur.getCount(); i++) {
					cur.moveToPosition(i);
					BusSiteVo vo = new BusSiteVo();
					String siteName = cur.getString(0);
					String noteGuid = cur.getString(1);
					vo.setName(siteName);
					vo.setNoteGuid(noteGuid);
					list.add(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
				cur = null;
			}
			if (db != null) {
				db.close();
				db = null;
			}
		}
		return list;
	}

	// 查询所以的站点信息
	public boolean isExistSite(String noteGuid) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String sql = "select noteGuid from mySite where noteGuid=" + "'"
				+ noteGuid + "'";
		Cursor cur = null;
		try {
			cur = db.rawQuery(sql, null);
			if (cur != null && cur.getCount() > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
				cur = null;
			}

			if (db != null) {
				db.close();
				db = null;
			}
		}
		return false;
	}

	// 删除站点信息
	public void deleteBusSite(String noteGuid) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String sql = "DELETE FROM mySite WHERE noteGuid =" + "'" + noteGuid
				+ "'";
		try {
			db.execSQL(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
				db = null;
			}
		}
	}

	public boolean isExistLine(String guid) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String sql = "select lineId from myLine where guid=" + "'" + guid + "'";
		Cursor cur = null;
		try {
			cur = db.rawQuery(sql, null);
			if (cur != null && cur.getCount() > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
				cur = null;
			}

			if (db != null) {
				db.close();
				db = null;
			}
		}
		return false;
	}

	// 添加线路信息
	public void addBusLine(BusLineDetailVo vo) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("guid", vo.getLGUID());
		cv.put("LName", vo.getLName());
		cv.put("LDirection", vo.getLDirection());
		try {
			db.insert("myLine", null, cv);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
				db = null;
			}
		}
	}

	// 删除线路信息
	public void deleteBusLine(String guid) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String sql = "DELETE FROM myLine WHERE guid =" + "'" + guid + "'";
		try {
			db.execSQL(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
				db = null;
			}
		}
	}

	// 查询所以的站点信息
	public ArrayList<BusLineDetailVo> getAllLineData() {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String sql = "SELECT guid,LName,LDirection FROM myLine order by lineId desc";
		ArrayList<BusLineDetailVo> list = new ArrayList<BusLineDetailVo>();
		Cursor cur = null;
		try {
			cur = db.rawQuery(sql, null);
			if (cur != null && cur.getCount() > 0) {
				for (int i = 0; i < cur.getCount(); i++) {
					cur.moveToPosition(i);
					BusLineDetailVo vo = new BusLineDetailVo();
					String guid = cur.getString(0);
					String LName = cur.getString(1);
					String LDirection = cur.getString(2);
					vo.setLGUID(guid);
					vo.setLName(LName);
					vo.setLDirection(LDirection);
					list.add(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
				cur = null;
			}
			if (db != null) {
				db.close();
				db = null;
			}
		}
		return list;
	}

}
