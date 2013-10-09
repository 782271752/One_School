package li.dream;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import li.adapter.AutoTextViewAdapter;
import li.entity.User;
import li.utils.AESPlus;
import li.utils.ConnWeb;
import li.utils.Salt;


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
import org.apache.http.HttpResponse;
import org.json.JSONObject;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sun.org.apache.bcel.internal.generic.NEW;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class loginActivity extends FinalActivity implements TextWatcher{
	
	private EditText pwd;  
	private AutoCompleteTextView username;
	private AutoTextViewAdapter adapter;
	private Button login;
	private Context context;
	private TextView register;
	private SharedPreferences sp;
	/*SharedPreferences也是一种轻型的数据存储方式，
	 * 它的本质是基于XML文件存储key-value键值对数据，通常用来存储一些简单的配置信息。
	 */
//	private CheckBox auto;
	private String name;  //登陆账号
	private String passwd; //登陆密码
	private String login_url=new ConnWeb().loginUrl;
	private String login_feedback;
	private boolean isExit;
	private String user_id,user_name,user_school,user_school_id,user_pic;  //用户id，用户名字，用户学校，用户学校id，用户头像
	boolean isauto= new User().atuo;
	private String aes_passwd,store_passwd,salt;
	
	public static final String[] AUTO_EMAILS = {"@163.com","@139.com", "@sina.com","@sina.cn", "@qq.com", "@126.com", "@sohu.com","@gmail.com", "@apple.com","@yeah.net"};
	
	protected ImageLoader imageLoader = ImageLoader.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		sp=this.getSharedPreferences("userinfo", MODE_PRIVATE);
		context=loginActivity.this;
		////获取SharedPreferences对象
		init();
		Listener();
		
	}
	private void init()
	{
		username=(AutoCompleteTextView)findViewById(R.id.username);
		pwd=(EditText)findViewById(R.id.pwd);
		login=(Button)findViewById(R.id.login);
//		auto=(CheckBox)findViewById(R.id.auto);
		register=(TextView)findViewById(R.id.regist);	  
		
		adapter = new AutoTextViewAdapter(this);
		username.setAdapter(adapter);
		username.setThreshold(1);//输入1个字符时就开始检测，默认为2个
		username.addTextChangedListener(this);//监听autoview的变化
		
/*		if(!sp.getBoolean("auto", false))  //如果不是自动登陆的话，获取用户名和密码
		{

			username.setText(sp.getString("uname", null));
			pwd.setText(sp.getString("password", null));
			auto.setChecked(true);
			
			
		} */
		
		
	}
	private void Listener()
	{
		login.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				
				name = username.getText().toString().trim();
				passwd=pwd.getText().toString().trim();
				
				
				
				if (name.equals("")) {
					Animation animation=AnimationUtils.loadAnimation(loginActivity.this, R.anim.shake);
					username.startAnimation(animation);
				}
				else if (passwd.equals("")) {
					Animation animation=AnimationUtils.loadAnimation(loginActivity.this, R.anim.shake);
					pwd.startAnimation(animation);
				}
				else 
				{
					LoginTask loginTask=new LoginTask();
					loginTask.execute(login_url);
						
				}
			}			
			
		});
		
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(loginActivity.this,registerActivity.class);
				startActivity(intent);
				loginActivity.this.finish();
			}
		});
		
		
	}
		
	/**
	 * 自动填充邮箱列表
	 * @param input
	 */
	private void autoAddEmails(String input) {
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
	public class LoginTask extends AsyncTask<String, Integer, String>
	{


		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			publishProgress(30);
			String rs="";
			List<NameValuePair> param=new ArrayList<NameValuePair>();
				//使用NameValuePair类来保存键值对，使用NameValuePair类是因为下面需要的那个类的参数要求
			param.add(new BasicNameValuePair("email", name));
			param.add(new BasicNameValuePair("pswd", passwd));
			rs=ConnectWeb(param, params);
			return rs;
			
		}


		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}


		@Override
		protected void onPostExecute(String result) {
			publishProgress(100);
			if(result=="0")
			{
				cancel_dialog();
				Error_Dialog(context,"无法连接到网络，请检查网络配置...");
			}
			try {
				
				JSONObject jObject=new JSONObject(result);
				isExit=jObject.getBoolean("state");
				salt=new Salt().salts();
				aes_passwd=AESPlus.encrypt(BaseApplication.key, passwd);
				store_passwd=salt+aes_passwd;
				if(isExit)
				{
					user_id=jObject.getString("uid");
					user_name=jObject.getString("user_name");
					user_pic=jObject.getString("user_pic");
					user_school=jObject.getString("user_school");
					user_school_id=jObject.getString("user_school_id");
					
			/*		if(auto.isChecked())  //自动登陆
					{
						Editor editor=sp.edit();
						editor.putInt("uid", user_id);
						editor.putString("uname",name);
						editor.putString("password", passwd);
						editor.putBoolean("auto",true);
						editor.putBoolean("first", true);  
						editor.commit();
							
						Intent intent=new Intent(loginActivity.this,DreamActivity.class);
						startActivity(intent);
						finish();
						
					}
					else//不自动登陆
					{*/
						Editor editor=sp.edit();
						editor.putString("uid", user_id);
						editor.putString("uname", name);
						editor.putString("password", store_passwd);
						editor.putString("user_name", user_name);
						editor.putString("user_pic",user_pic);
						editor.putString("user_school", user_school);
						editor.putString("user_school_id", user_school_id);
						editor.putBoolean("first", true);
						editor.commit();
						
						Intent intent=new Intent(loginActivity.this,DreamActivity.class);
						startActivity(intent);
						finish();
					}
				else{
					Toast.makeText(loginActivity.this, "用户名或密码错误，请重试...", 
							Toast.LENGTH_LONG).show();
							 
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


		@Override
		protected void onProgressUpdate(Integer... values) {
			if(values[0]==30)
			{
				show_dialog("登陆中，请稍等...");
			}
			else
			{
				cancel_dialog();
			}
		}
		
		
	}


	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void afterTextChanged(Editable s) {
		String input = s.toString();  
        adapter.mList.clear();  
        autoAddEmails(input);
        adapter.notifyDataSetChanged();  
        username.showDropDown();  
		
	}

	
}
