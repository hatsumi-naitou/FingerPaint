package sample.application.fingerpaint;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class FingerPaintActivity extends Activity implements OnTouchListener{	//implementsの場合は、メソッドの中の実装がないため、実装させてあげなくてはいけない。

	public Canvas canvas;
	public Paint paint;
	public Path path;
	public Bitmap bitmap;
	public Float x1,y1;				//同じ方の変数を２つまとめて宣言。
	public Integer w,h;
	
	
	
	public boolean onTouch(View v, MotionEvent event) {
		return Boolean.valueOf(true);		//参照型のBoolean型のインスタンスを返している。　
		//return Boolean2.TRUE;
		
		/*　本来のbooleanの定義はこれでよかったはず・・・。
		 * public enum Boolean {
		 * 	TRUE(true), False(false);
		 * 		boolean value = true;
		 * 		Boolean(boolean bool){
		 * 			this.value = bool;
		 * 		}
		 * }  //使うときは、Boolean.TRUE /　Boolean.False　を使えばいい。
		 */
		
		
	}
	
	
}

//オートボクシング