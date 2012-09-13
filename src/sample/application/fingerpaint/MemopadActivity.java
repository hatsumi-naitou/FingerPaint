package sample.application.fingerpaint;

import java.text.DateFormat;
import java.util.Date;

import sample.application.memopad.R;

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
        super.onCreate(savedInstanceState); //super �e�N���X�̃C���X�^���X
        this.setContentView(R.layout.main); //this �����̃N���X�̃C���X�^���X   //����ɍ������́B
                
        EditText et = (EditText) this.findViewById(R.id.editText1);
        SharedPreferences pref = this.getSharedPreferences("MemoPrefs", MODE_PRIVATE);     //����\���Ƃ��āA�O��ۑ��������̂�����Γǂݍ���ŁA�������Ă�����B�@�@�Ƃ��������B
        et.setText(pref.getString("memo", ""));
        et.setSelection(pref.getInt("cursor", 0));
        
    }
    
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
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
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		MenuInflater mi = this.getMenuInflater();
		mi.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		EditText et = (EditText) findViewById(R.id.editText1);
		switch(item.getItemId()){
		case R.id.menu_save:
			this.saveMemo();
			break;
		case R.id.menu_open:                //              
			Intent i = new Intent(this, MemoList.class);       //MemoList�Ƃ����N���X�ɒu���������
			this.startActivityForResult(i,0);
			break;
		case R.id.menu_new:
			et.setText("");    //�����̃R�[�h�͂��������B�V�K�ł��當�������Ȃ�A���̑O�ɍ��܂ł̕��̓Z�[�u���Ă����R�[�h����Ă����Ȃ��ƁB
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
    protected void onStop(){
		super.onStop();
    	EditText et = (EditText) this.findViewById(R.id.editText1);   //onStop���Ă΂ꂽ�獡��͂���Ă���f�[�^���擾���Ă���B
    	SharedPreferences pref = this.getSharedPreferences("Memoprefs", MODE_PRIVATE);
    	SharedPreferences.Editor editor = pref.edit();
    	editor.putString("memo", et.getText().toString());    //�v���t�@�����X�̒��ŁAmemo�Ƃ������O�ŕۑ�����B
    	editor.putInt("cursor", Selection.getSelectionStart(et.getText()));
    	editor.commit();
    	    	
    }
    
	public void saveMemo(){         //����͌p���������ɒǉ��������\�b�h�̂��߁A�A���h���C�h�͎����I�ɂ͎��s���Ă���Ȃ��B�A���h���C�h�I�ɂ͒m�������Ƃł͂Ȃ��B
		EditText et = (EditText)this.findViewById(R.id.editText1);     //onOptionsItemSelected���ŁA�{�^�����������Ƃ��ɌĂԂ悤�ɂȂ��Ă���B
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