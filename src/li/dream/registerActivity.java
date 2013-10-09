package li.dream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import li.adapter.AutoTextViewAdapter;
import li.adapter.BrayAdapter;
import li.db.StuDBHelper;
import li.utils.ConnWeb;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Entity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.inputmethodservice.Keyboard.Key;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class registerActivity extends loginActivity implements TextWatcher{

	
	private EditText password1,password2;
	private AutoCompleteTextView E_mail,school;
	private Spinner province;
	private RadioGroup radioGroup;
	private RadioButton male,female;
	private AutoTextViewAdapter adapter;
	private Button regist;
	private String name,passwd,passwd2,sex;
	private String url=new ConnWeb().loginUrl;
	private SharedPreferences sp;
	boolean is_finish;
	private ImageView back;
	private TextView age;
	public static final int  DATE_PICKER_ID=1;   
	private Context context=registerActivity.this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		sp=this.getSharedPreferences("userinfo",MODE_PRIVATE);////获取SharedPreferences对象
		//sp.edit().clear().commit();
		DBCtrl(context);
		init();
		Listener();
	}
	private void init()
	{
		E_mail=(AutoCompleteTextView)findViewById(R.id.regist_email);
		password1=(EditText)findViewById(R.id.regist_first_password);
		password2=(EditText)findViewById(R.id.regist_second_password);
		regist=(Button)findViewById(R.id.regist_regist_button);
		back=(ImageView)findViewById(R.id.regist_back);
		age=(TextView)findViewById(R.id.regist_age);
		province=(Spinner)findViewById(R.id.regist_province);
		school=(AutoCompleteTextView)findViewById(R.id.regist_school);
		school.setThreshold(1);
		male=(RadioButton)findViewById(R.id.regist_male_cb);
		female=(RadioButton)findViewById(R.id.regist_female_cb);
		radioGroup=(RadioGroup)findViewById(R.id.regist_group);
		
		E_mail.setError("请输入邮箱");
		adapter = new AutoTextViewAdapter(this);
		E_mail.setAdapter(adapter);
		E_mail.setThreshold(1);//输入1个字符时就开始检测，默认为2个
		E_mail.addTextChangedListener(this);//监听autoview的变化
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
	    
	  private void copyDataBase() throws IOException {      //复制数据库文件到指定目录
			// Open your local db as the input stream
			InputStream myInput = registerActivity.this.getResources()
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
	    
	    public void DBCtrl(Context context) {   //创建数据库文件
			
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
	    	StuDBHelper dbHelper = new StuDBHelper(registerActivity.this,"province.db",null,1);
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
	private void Listener()
	{
		regist.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				
				name= E_mail.getText().toString().trim();
				passwd= password1.getText().toString().trim();
				passwd2= password2.getText().toString().trim();
				
				if(name.equals(""))
				{
					Animation animation=AnimationUtils.loadAnimation(registerActivity.this, R.anim.shake);
					E_mail.startAnimation(animation);
				}
				else if(passwd.equals("")){
					Animation animation=AnimationUtils.loadAnimation(registerActivity.this, R.anim.shake);
					password1.startAnimation(animation);
				}
				else if(passwd2.equals("")){
					Animation animation=AnimationUtils.loadAnimation(registerActivity.this, R.anim.shake);
					password2.startAnimation(animation);
				}
				else if(!passwd.equals(passwd2)){
					Error_Dialog(registerActivity.this,"两次输入的密码不一样...");
					password1.setText("");
					password2.setText("");
				}
				else{
					RegistTask registTask=new RegistTask();
					registTask.execute(url);
				}
				
			}
		});
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(registerActivity.this,loginActivity.class);
				startActivity(intent);
				registerActivity.this.finish();
				
				
			}
		});
		age.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(1); //弹出时间窗口
				
			}
		});
		radioGroup.setOnCheckedChangeListener(groupChangeListener);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.privince_item, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        province.setAdapter(adapter);
        province.setOnItemSelectedListener(new OnItemSelectedListener() {

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
				
				BrayAdapter<String> adapter=new BrayAdapter<String>(registerActivity.this, android.R.layout.simple_dropdown_item_1line,getName(province_item));
				school.setAdapter(adapter);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	//--------------------------创建时间窗口----------------------------//
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
	     
	     age.setText(year+"-"+moth+"-"+day);//获得选择的日期(注意月份是从0开始的)  
	    }  
	  
	 };  
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			Intent intent=new Intent(registerActivity.this,loginActivity.class);
			startActivity(intent);
			registerActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void show_dialog(String title) {
		// TODO Auto-generated method stub
		super.show_dialog(title);
	}
	
	@Override
	public void cancel_dialog() {
		// TODO Auto-generated method stub
		super.cancel_dialog();
	}

	@Override
	public String ConnectWeb(List<NameValuePair> param, String... params) {
		return super.ConnectWeb(param, params);
	}

	public class RegistTask extends AsyncTask<String, Integer, String>
	{

		
		@Override
		protected String doInBackground(String... params) {
			publishProgress(30);
			String reg="";
			List<NameValuePair> user_info=new ArrayList<NameValuePair>();
			user_info.add(new BasicNameValuePair("username", name));
			user_info.add(new BasicNameValuePair("password", passwd));
			reg=ConnectWeb(user_info, params);
				
			
			return reg;
		}

	
		@Override
		protected void onPreExecute() {
			
			super.onPreExecute();
		}

	
		@Override
		protected void onPostExecute(String result) {
			
			publishProgress(100);
			if(result=="0")
			{
				Error_Dialog(registerActivity.this,"无法连接到网络，请检查网络配置...");
			}
			try {
				JSONObject jObject=new JSONObject(result);
				is_finish=jObject.getBoolean("is_Exit");
				if(is_finish)
				{	
					
					Editor editor=sp.edit();
					editor.putString("uname", name);
					editor.putString("password", passwd);
					editor.commit();
					
					Intent intent=new Intent(registerActivity.this,SetheadImageActivity.class);
					startActivity(intent);
					registerActivity.this.finish();
				}
				else{
					Error_Dialog(registerActivity.this,"注册失败，你可能已经注册过该账号");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		@Override
		protected void onProgressUpdate(Integer... values) {
			if(values[0]==30)
			{
				show_dialog("正在注册,请稍等...");
			}
			else {
				cancel_dialog();
			}
		}
		
	}

	
	@Override
	public void afterTextChanged(Editable s) {
		String input = s.toString();  
        adapter.mList.clear();  
        autoEmails(input);
        adapter.notifyDataSetChanged();  
		E_mail.showDropDown();
	}
	private void autoEmails(String input) {
		System.out.println("input-->" + input);
		String autoEmail = "";
        if (input.length() > 0) {  
            for (int i = 0; i < AUTO_EMAILS.length; ++i) {  
            	if(input.contains("@")) {//包含“@”则开始过滤
            		String filter = input.substring(input.indexOf("@") + 1 , input.length());//获取过滤器，即根据输入“@”之后的内容过滤出符合条件的邮箱
            		System.out.println("filter-->" + filter);
            		if(AUTO_EMAILS[i].contains(filter)) {//符合过滤条件
            			autoEmail = input.substring(0, input.indexOf("@")) + AUTO_EMAILS[i];//用户输入“@”之前的内容加上自动填充的内容即为最后的结果
            			adapter.mList.add(autoEmail);
            		}
            	}else {
            		autoEmail = input + AUTO_EMAILS[i];
            		adapter.mList.add(autoEmail);
            	}
            }  
        } 
	}
}
