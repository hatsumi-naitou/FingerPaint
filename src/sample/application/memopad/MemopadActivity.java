package sample.application.memopad;

import java.text.DateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.text.Selection;
import android.widget.EditText;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MemopadActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //super 親クラスのインスタンス
        this.setContentView(R.layout.main); //this 自分のクラスのインスタンス   //勝手に作られるもの。
                
        EditText et = (EditText) this.findViewById(R.id.editText1);
        SharedPreferences pref = this.getSharedPreferences("MemoPrefs", MODE_PRIVATE);     //初期表示として、前回保存したものがあれば読み込んで、処理してあげる。　　という処理。
        et.setText(pref.getString("memo", ""));
        et.setSelection(pref.getInt("cursor", 0));
        
    }
    
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自動生成されたメソッド・スタブ
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			EditText et = (EditText) findViewById(R.id.editText1);
					
					switch(requestCode){
					case 0:
						et.setText(data.getStringExtra("text"));
						break;
					}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO 自動生成されたメソッド・スタブ
		MenuInflater mi = this.getMenuInflater();
		mi.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO 自動生成されたメソッド・スタブ
		EditText et = (EditText) findViewById(R.id.editText1);
		switch(item.getItemId()){
		case R.id.menu_save:
			this.saveMemo();
			break;
		case R.id.menu_open:                //              
			Intent i = new Intent(this, MemoList.class);       //MemoListというクラスに置き換わった
			this.startActivityForResult(i,0);
			break;
		case R.id.menu_new:
			et.setText("");    //ここのコードはおかしい。新規でから文字を入れるなら、その前に今までの分はセーブしておくコードを入れておかないと。
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
    protected void onStop(){
		super.onStop();
    	EditText et = (EditText) this.findViewById(R.id.editText1);   //onStopが呼ばれたら今入力されているデータを取得してくる。
    	SharedPreferences pref = this.getSharedPreferences("Memoprefs", MODE_PRIVATE);
    	SharedPreferences.Editor editor = pref.edit();
    	editor.putString("memo", et.getText().toString());    //プリファレンスの中で、memoという名前で保存する。
    	editor.putInt("cursor", Selection.getSelectionStart(et.getText()));
    	editor.commit();
    	    	
    }
    
	public void saveMemo(){         //これは継承した分に追加したメソッドのため、アンドロイドは自動的には実行してくれない。アンドロイド的には知ったことではない。
		EditText et = (EditText)this.findViewById(R.id.editText1);     //onOptionsItemSelected内で、ボタンを押したときに呼ぶようになっている。
		String title;
		String memo = et.getText().toString();
		
		if(memo.trim().length() > 0){
			if(memo.indexOf("\n") == -1) {
				title = memo.substring(0, Math.min(memo.length(), 20));
			}
			else{
				title = memo.substring(0, Math.min(memo.indexOf("\n"), 20));
			}
			String ts = DateFormat.getDateTimeInstance().format(new Date());
			MemoDBHelper memos = new MemoDBHelper(this);
			SQLiteDatabase db = memos.getWritableDatabase();
			ContentValues values = new ContentValues();
			
			values.put("title", title + "\n" + ts);
			values.put("memo", memo);
			
			db.insertOrThrow("memoDB", null, values);
			memos.close();

		}		
	}    
}