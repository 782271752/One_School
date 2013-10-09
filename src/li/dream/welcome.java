package li.dream;

import cn.sharesdk.framework.AbstractWeibo;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Window;


public class welcome extends Activity{
	
	
	private SharedPreferences sp;//SharedPreferences也是一种轻型的数据存储方式
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		AbstractWeibo.initSDK(this);
		
		sp=this.getSharedPreferences("userinfo", MODE_PRIVATE);////获取SharedPreferences对象
		
		
		if (sp.getBoolean("first", false)){
/*			if(sp.getBoolean("auto", false))  //如果是自动登陆的话，直接跳到主界面
			{
				new Handler().postDelayed(new Runnable() {
					
					public void run() {
						Intent intent=new Intent(welcome.this,moreActivity.class);
						startActivity(intent);
						welcome.this.finish();
					}
				}, 1000);
				
			} 
			else 
			{	*/
				new Handler().postDelayed(new Runnable() {
					
					public void run() {
						// TODO Auto-generated method stub
						Intent intent=new Intent(welcome.this,DreamActivity.class);
						startActivity(intent);
						welcome.this.finish();	
					}
				}, 1000);
			}
		else {
			new Handler().postDelayed(new Runnable() {
				
				public void run() {
					Intent intent=new Intent(welcome.this,DreamActivity.class);
					startActivity(intent);
					welcome.this.finish();
				}
			}, 1000);
		}
		
		
		
		
	}
	

}
