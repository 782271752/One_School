package li.dream;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.sun.org.apache.bcel.internal.generic.NEW;

import li.db.StuDBHelper;
import li.utils.ConnWeb;
import li.adapter.BrayAdapter;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class modifiActivity extends loginActivity{

	private TextView title,modifi_age;
	private EditText modifi_name,modifi_signature;
	private AutoCompleteTextView select_school;
	private Spinner province;
	private Context context= modifiActivity.this;
	private Button send_info;
	private RadioGroup modifi_radioGroup;
	private RadioButton male,female;
	private String nickname,sex,signature,school,age;
	private String url=new ConnWeb().send_infoUrl;
	private static final int  DATE_PICKER_ID=1;   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modifi);
		DBCtrl(context);
		init();
		title.setText("设置个人资料");
		send_info.setText("提交");
		
		modifi_radioGroup.setOnCheckedChangeListener(groupChangeListener);
		
		send_info.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				nickname=modifi_name.getText().toString().trim();
				age=modifi_age.getText().toString().trim();
				signature=modifi_signature.getText().toString().trim();
				school=select_school.getText().toString().trim();
				if(nickname.equals("")||school.equals(""))
				{
					Error_Dialog(modifiActivity.this,"完善资料，让大家认识你");
				}
				else{
					modifi_task modifiTask=new modifi_task();
					modifiTask.execute(url);
				}
			}
		});
		
		modifi_age.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(DATE_PICKER_ID); 
				
			}
		});
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.privince_item, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        province.setAdapter(adapter);
		province.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String province_item="";
				switch(position)
				{
				case 0:
					province_item="guangdong";
					break;
				case 1:
					province_item="beijing";
					break;
				case 2:
					province_item="shanghai";
					break;
				case 3:
					province_item="tianjing";
					break;
				case 4:
					province_item="chongqing";
					break;
				case 5:
					province_item="heilongjiang";
					break;
				case 6:
					province_item="jilin";
					break;
				case 7:
					province_item="liaoning";
					break;
				case 8:
					province_item="shandong";
					break;
				case 9:
					province_item="shanxi";
					break;
				case 10:
					province_item="shangxi";
					break;
				case 11:
					province_item="hebei";
					break;
				case 12:
					province_item="henan";
					break;
				case 13:
					province_item="hubei";
					break;
				case 14:
					province_item="hunan";
					break;
				case 15:
					province_item="hainan";
					break;
				case 16:
					province_item="jiangsu";
					break;
				case 17:
					province_item="jiangxi";
					break;
				case 18:
					province_item="guangxi";
					break;
				case 19:
					province_item="yunnan";
					break;
				case 20:
					province_item="guizhou";
					break;
				case 21:
					province_item="sichuan";
					break;
				case 22:
					province_item="neimenggu";
					break;
				case 23:
					province_item="ningxia";
					break;
				case 24:
					province_item="gansu";
					break;
				case 25:
					province_item="qinghai";
					break;
				case 26:
					province_item="xizang";
					break;
				case 27:
					province_item="xinjiang";
					break;
				case 28:
					province_item="anhui";
					break;
				case 29:
					province_item="zhejiang";
					break;
				case 30:
					province_item="fujian";
					break;
				case 31:
					province_item="xianggang";
					break;
				case 32:
					province_item="aomeng";
					break;
				case 33:
					province_item="taiwang";
					break;
				
				}
				
				BrayAdapter<String> adapter=new BrayAdapter<String>(modifiActivity.this, android.R.layout.simple_dropdown_item_1line,getName(province_item));
				select_school.setAdapter(adapter);
			}
		});
		
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id)  
		 {  
		   case DATE_PICKER_ID:  
		   return new DatePickerDialog(this,onDateSetListener,1991,11,24);  //显示的默认时间  
		  }  
		return null;  
	}
	DatePickerDialog.OnDateSetListener onDateSetListener=new DatePickerDialog.OnDateSetListener()  
	{  
	  
	  @Override  
	   public void onDateSet(DatePicker view, int year, int monthOfYear,  
	   int dayOfMonth) {  
		 monthOfYear=monthOfYear+1;
	     String moth;
	     String day;
	     if(0<monthOfYear&&monthOfYear<10){
	    	 moth="0"+String.valueOf(monthOfYear);
	     }else{
	    	 moth=String.valueOf(monthOfYear);
	     }
	     if(0<dayOfMonth&&dayOfMonth<10)
	     {
	    	 day="0"+String.valueOf(dayOfMonth);
	     }else{
	    	 day=String.valueOf(dayOfMonth);
	     }
	     
	     modifi_age.setText(year+"-"+moth+"-"+day);//获得选择的日期(注意月份是从0开始的)  
	    }  
	  
	 };  
	public void init()
	{
		modifi_name=(EditText)findViewById(R.id.modifi_name);
		modifi_age=(TextView)findViewById(R.id.modifi_age);
		modifi_signature=(EditText)findViewById(R.id.modifi_signature);
		select_school=(AutoCompleteTextView)findViewById(R.id.modifi_school);
		select_school.setThreshold(1);
		province=(Spinner)findViewById(R.id.modifi_province);
		
		title=(TextView)findViewById(R.id.user_title);
		send_info=(Button)findViewById(R.id.modifi_save);	
		male=(RadioButton)findViewById(R.id.modi_male_cb);
		female=(RadioButton)findViewById(R.id.modi_female_cb);
		modifi_radioGroup=(RadioGroup)findViewById(R.id.modifi_group);
	}
	
	private RadioGroup.OnCheckedChangeListener groupChangeListener=new 
			RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			String sexx=null;
			if(checkedId==male.getId()){
				sexx="男";
			}
			else if(checkedId==female.getId()){
				sexx="女";
			}
			sex=sexx;
		}
	};
	 private boolean checkDataBase() {
			
			File file = new File("/data/data/li.dream/databases/", "province.db");
			return file.exists();

		}
	    
	  private void copyDataBase() throws IOException {
			// Open your local db as the input stream
			InputStream myInput = modifiActivity.this.getResources()
					.openRawResource(R.raw.province);
			// Path to the just created empty db
			String outFileName = "/data/data/li.dream/databases/province.db";
			// Open the empty db as the output stream
			OutputStream myOutput = new FileOutputStream(outFileName);
			// transfer bytes from the inputfile to the outputfile
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}
			// Close the streams
			myOutput.flush();
			myOutput.close();
			myInput.close();

			// }

		}
	    
	    public void DBCtrl(Context context) {
			
				this.context = context; 
				boolean dbExist = checkDataBase();

				if (dbExist) {
					try {
						File dbf = new File("data/data/li.dream/databases/province.db");
						dbf.delete();
						copyDataBase();
					} catch (Exception e) {
						throw new Error("创建数据库失败");
					}
					
				} else {
					// 创建数据库
					try {
						File dir = new File("data/data/li.dream/databases/");
						if (!dir.exists()) {
							dir.mkdirs();
						}
						File dbf = new File("data/data/li.dream/databases/province.db");
						if (dbf.exists()) {
							dbf.delete();
						}
						// SQLiteDatabase.openOrCreateDatabase(dbf, null);
						// 复制asseets中的db文件到DB_PATH下
						copyDataBase();
					} catch (IOException e) {
						throw new Error("数据库创建失败");
					}
				}
			}
	    public List<String> getName(String provi)
	    {
	    	
	    	List<String> name= new ArrayList<String>();
	    	StuDBHelper dbHelper = new StuDBHelper(modifiActivity.this,"province.db",null,1);
			//得到一个可写的数据库
			SQLiteDatabase db =dbHelper.getReadableDatabase();
			
			Cursor cursor = db.query(provi, null,null,null, null, null, null);
			while(cursor.moveToNext()){
				String item = cursor.getString(cursor.getColumnIndex("name"));
				name.add(item);
			}
			//关闭数据库
			db.close(); 	
	    	
	    	return name;
	    }
	    
	   public class modifi_task extends AsyncTask<String, Integer, String>
	   {
 
		@Override
		protected String doInBackground(String... params) {
			
			publishProgress(30);
			String reg="";
			List<NameValuePair> user_info=new ArrayList<NameValuePair>();
			user_info.add(new BasicNameValuePair("nickname",  nickname));
			user_info.add(new BasicNameValuePair("sex",sex));
			user_info.add(new BasicNameValuePair("age", age));
			user_info.add(new BasicNameValuePair("signature", signature));
			user_info.add(new BasicNameValuePair("school", school));
			reg=ConnectWeb(user_info, params);
				
			
			return reg;
		}

		 
		@Override
		protected void onPreExecute() { 
			super.onPreExecute();
		}

	 
		@Override
		protected void onPostExecute(String result) {
			if(result=="0")
			{
				cancel_dialog();
				Error_Dialog(modifiActivity.this,"无法连接到网络，请检查网络配置...");
			}
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
 
		@Override
		protected void onProgressUpdate(Integer... values) {
			if(values[0]==30)
			{
				show_dialog("正在提交数据,请稍等...");
			}
			else {
				cancel_dialog();
			}
		}
		   
	   }

}
