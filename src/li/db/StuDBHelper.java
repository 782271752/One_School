package li.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StuDBHelper extends SQLiteOpenHelper {

	private static final String TAG = "DreamSQLite";
	public static final int VERSION = 1;
		
	//必须要有构造函数
	public StuDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	// 当第一次创建数据库的时候，调用该方法 
	public void onCreate(SQLiteDatabase db) {
		
	}

	//当更新数据库的时候执行该方法
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}
