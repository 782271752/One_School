package li.dream;

import cn.sharesdk.framework.AbstractWeibo;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Window;


public class welcome extends Activity{
	
	
	private SharedPreferences sp;//SharedPreferencesҲ��һ�����͵����ݴ洢��ʽ
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		AbstractWeibo.initSDK(this);
		
		sp=this.getSharedPreferences("userinfo", MODE_PRIVATE);////��ȡSharedPreferences����
		
		
		if (sp.getBoolean("first", false)){
/*			if(sp.getBoolean("auto", false))  //������Զ���½�Ļ���ֱ������������
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
