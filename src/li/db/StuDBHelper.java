package li.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StuDBHelper extends SQLiteOpenHelper {

	private static final String TAG = "DreamSQLite";
	public static final int VERSION = 1;
		
	//����Ҫ�й��캯��
	public StuDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	// ����һ�δ������ݿ��ʱ�򣬵��ø÷��� 
	public void onCreate(SQLiteDatabase db) {
		
	}

	//���������ݿ��ʱ��ִ�и÷���
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}
