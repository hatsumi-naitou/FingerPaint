package sample.application.memopad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class MemoDBHelper extends SQLiteOpenHelper {

	public static final String name = "memos.db";
	public static final CursorFactory factory = null;
	public static final Integer version = 1;
	
	//public String name2 = "memos.db"
	
	public MemoDBHelper(Context context, String name,CursorFactory factory, Integer version){
		super(context, name, factory, version);
	}
	
	public MemoDBHelper(Context context) {
		super(context, name, factory, version);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table memoDB ("
				+ android.provider.BaseColumns._ID
				+ " integer primary key autoincrement, title text, memo text);";
		db.execSQL(sql);
		

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO 自動生成されたメソッド・スタブ

	}
	

	
	

}
