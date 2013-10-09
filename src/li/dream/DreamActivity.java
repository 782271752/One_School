package li.dream;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.tencent.mm.sdk.platformtools.Log;

import cn.sharesdk.framework.AbstractWeibo;
import li.utils.AESPlus;
import li.utils.ExitManager;
import li.utils.Salt;
import android.R.drawable;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.Toast;


public class DreamActivity extends TabActivity {
	private TabHost tabHost;
	private RadioGroup tab_group;
    private RadioButton home,sort,more,search,message;
    private RelativeLayout Bottom;
//    private Button arrow;
    private boolean isnetwork,isExit;
    private Context context;
    private SharedPreferences sp;
    private String username;
    private String aes_password;
    private String pwd;
    /**
	 * 退出时间
	 */
	private long mExitTime;
	/**
	 * 退出间隔
	 */
	private static final int INTERVAL = 2000;
	/**
	 * 
	 */
    
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context=this;
        init();
        initTab();
        ExitManager.getInstance().addActivity(this);
       
        isnetwork=new FinalActivity().checkNetwork(context);
	     if(!isnetwork){
	       	AlertDialog.Builder builder = new AlertDialog.Builder(context);
	       	builder.setTitle("同校网");  
	       	builder.setMessage("网络错误，请检查网络设置");
	       	builder.setCancelable(false);
	       	builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
	                
	       		@Override  
	            public void onClick(DialogInterface dialog, int which) {  

	            Intent intent=new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
	            ComponentName cName = new ComponentName("com.android.phone","com.android.phone.Settings");
	            intent.setComponent(cName);
	            startActivity(intent);
	       		}  
	         });  
	        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
						
					}
				});
	            builder.show(); 
	            
	     }
	     
/*	    sp=this.getSharedPreferences("userinfo", MODE_PRIVATE);
		username=sp.getString("uname", null);
		Log.v("dream_username", username);
		aes_password=sp.getString("password", null);
		Log.v("dream_aespwd", aes_password);
		
		if((!username.equals(""))&&(!aes_password.equals(""))){  //判断 账号和密码是否存在
			pwd=aes_password.substring(6, aes_password.length());
			
			
		}else{		
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
	       	builder.setTitle("同校网");  
	       	builder.setMessage("账号或者密码错误");
	       	builder.setCancelable(false);
	       	builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
	                
	       		@Override  
	            public void onClick(DialogInterface dialog, int which) {  
	       			Intent intent=new Intent(DreamActivity.this,loginActivity.class);
	    			startActivity(intent);
	    			DreamActivity.this.finish();            
	       		}  
	         });  	        
	         builder.show(); 	
		}*/
	     
        
    } 
    
    public void init()
    {
    	//final Drawable arrow_up=this.getResources().getDrawable(android.R.drawable.arrow_up_float);
    	//final Drawable arrow_down=this.getResources().getDrawable(android.R.drawable.arrow_down_float);
    	Bottom=(RelativeLayout)findViewById(R.id.console_line_bottom);
    	//Bottom.setVisibility(View.VISIBLE);
    	//arrow=(Button)findViewById(R.id.arrow);
    	
    	tab_group=(RadioGroup)findViewById(R.id.main_tab_group);
    	home=(RadioButton)findViewById(R.id.main_home);
        sort=(RadioButton)findViewById(R.id.main_sort);
        more=(RadioButton)findViewById(R.id.main_more);
        search=(RadioButton)findViewById(R.id.main_search);
        message=(RadioButton)findViewById(R.id.main_message);
        
   /*     arrow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(tab_group.getVisibility()==View.GONE)
				{
					tab_group.setVisibility(View.VISIBLE);
					arrow.setBackgroundDrawable(getResources().getDrawable(R.drawable.arrow_down));
				}else{
					tab_group.setVisibility(View.GONE);
					arrow.setBackgroundDrawable(getResources().getDrawable(R.drawable.arrow_up));
				}
				
			}
		});   */
        
        home.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
			
				tabHost.setCurrentTabByTag("home");
			}
		});
        sort.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				tabHost.setCurrentTabByTag("sort");
			}
		});
		more.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tabHost.setCurrentTabByTag("more");
				
			}
		});
		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tabHost.setCurrentTabByTag("search");
				
			}
		});
		message.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tabHost.setCurrentTabByTag("message");
				
			}
		});
		
    }
    public void initTab()
    {
    	tabHost=getTabHost();
    	
    	tabHost.addTab(tabHost.newTabSpec("home").setIndicator("home")
    			.setContent(new Intent(this,homeActivity.class)));
    	tabHost.addTab(tabHost.newTabSpec("sort").setIndicator("sort")
    			.setContent(new Intent(this,sortActivity.class)));
    	tabHost.addTab(tabHost.newTabSpec("more").setIndicator("more")
    			.setContent(new Intent(this,moreActivity.class)));
    	tabHost.addTab(tabHost.newTabSpec("search").setIndicator("search")
    			.setContent(new Intent(this,findActivity.class)));
    	tabHost.addTab(tabHost.newTabSpec("message").setIndicator("message")
    			.setContent(new Intent(this,messageActivity.class)));		
    	
    }
	 
	 
	 
 
    

	private void exit() {
		if (System.currentTimeMillis() - mExitTime > INTERVAL) {
			Toast.makeText(this, "再按一次返回键,可直接退出程序", Toast.LENGTH_SHORT).show();
			mExitTime = System.currentTimeMillis();
		} else {
			finish();
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		}
	}
	 
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
              // exit();
            	AlertDialog.Builder builder = new AlertDialog.Builder(context);
    	       	builder.setTitle("同校网");  
    	       	builder.setMessage("确定离开同校网吗？");
    	       	builder.setCancelable(false);
    	       	builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
    	                
    	       		@Override  
    	            public void onClick(DialogInterface dialog, int which) {  
    	       			finish();
    	    			android.os.Process.killProcess(android.os.Process.myPid());
    	    			System.exit(0);
    	       		}  
    	         });  
    	        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
    					
    					@Override
    					public void onClick(DialogInterface dialog, int which) {
    						// TODO Auto-generated method stub
    						dialog.cancel();
    						
    					}
    				});
    	         builder.show();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
		 
	}

	@Override
	protected void onDestroy() {
		AbstractWeibo.stopSDK(this);
		super.onDestroy();
	}
	
	public class dreamTask extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			String rs="";
			List<NameValuePair> param=new ArrayList<NameValuePair>();
				//使用NameValuePair类来保存键值对，使用NameValuePair类是因为下面需要的那个类的参数要求
			param.add(new BasicNameValuePair("username", username));
			param.add(new BasicNameValuePair("password", pwd));
			rs=new FinalActivity().ConnectWeb(param, params);
			return rs;
		}

	 
		@Override
		protected void onPostExecute(String result) {
			if(result=="0")
			{
				Toast.makeText(context, "无法连接到网络，请检查网络配置", 
						Toast.LENGTH_LONG).show();
			}
			try {
				
				JSONObject jObject=new JSONObject(result);
				isExit=jObject.getBoolean("is_Exit");
				if(isExit)
				{
					return;
				}
				else{
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
			       	builder.setTitle("同校网");  
			       	builder.setMessage("账号或者密码错误");
			       	builder.setCancelable(false);
			       	builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
			                
			       		@Override  
			            public void onClick(DialogInterface dialog, int which) {  
			       			Intent intent=new Intent(DreamActivity.this,loginActivity.class);
			    			startActivity(intent);
			    			DreamActivity.this.finish();            
			       		}  
			         });  	        
			         builder.show(); 	
							 
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}