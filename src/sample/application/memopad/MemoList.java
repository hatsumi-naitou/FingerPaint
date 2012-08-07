package sample.application.memopad;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;
import android.content.Intent;

public class MemoList extends ListActivity {

	public static final String[] cols = {"title", "memo", android.provider.BaseColumns._ID,};
	public MemoDBHelper memos;
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		this.memos = new MemoDBHelper(this);
		SQLiteDatabase db = memos.getWritableDatabase();
		Cursor cursor = db.query("memoDB", MemoList.cols, "_ID="+String.valueOf(id), null, null, null, null);		//ｉｄについてもともとlong型できていたのでString型に変換。
		this.startManagingCursor(cursor);
		Integer idx = cursor.getColumnIndex("memo");
		cursor.moveToFirst();
		Intent i = new Intent();

		i.putExtra("text", cursor.getString(idx));
		this.setResult(RESULT_OK, i);                //
		this.memos.close();
		this.finish();		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.memolist);			//this.を追加。
		this.showMemos(this.getMemos());				//this.を追加。
		
		ListView lv = (ListView) this.findViewById(android.R.id.list);
		this.registerForContextMenu(lv);
		
	}

	public void showMemos(Cursor cursor) {
		if(cursor != null){
			String [] from = {"title"};
			int[] to = {android.R.id.text1};
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(
					this, android.R.layout.simple_list_item_1, cursor, from, to);
			setListAdapter(adapter);
		}
		this.memos.close();								//ifに対する偽の際の実行文
	}

	public Cursor getMemos() {
		this.memos = new MemoDBHelper(this);
		SQLiteDatabase db = memos.getReadableDatabase();
		Cursor cursor = db.query("memoDB", cols, null, null, null, null, null);
		this.startManagingCursor(cursor);
		return cursor;
	}

}
