package li.dream;



import java.util.HashMap;

import sun.security.krb5.internal.LoginOptions;

import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.framework.WeiboActionListener;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import li.service.UpdateService;

import li.entity.checkVersion;
import li.utils.ConnWeb;
 
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler.Callback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class moreActivity extends loginActivity implements Callback, OnClickListener, WeiboActionListener{

	
	
	private Button more_card_button,more_info_button,more_update_button,
			weixin,sinaweibo,tencentweibo;
	private checkVersion checkVersion;
	//private String serviceversion,apk_url;
	
	/** 官方微信 */
	public static final String WECHAT_ADDR = "http://weixin.qq.com/r/_nXa1QHEaD8XhxC2nyDe";
	/** 官方新浪微博 */
	public static final String SDK_SINAWEIBO_UID = "3483311000";
	/** 官方腾讯微博 */
	public static final String SDK_TENCENTWEIBO_UID = "xuan13421191980";
	private Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_more);
		handler = new Handler(this);
		init();
		 
		Listen();
	}
	public void init()
	{
		more_card_button=(Button)findViewById(R.id.more_card);
		more_info_button=(Button)findViewById(R.id.more_info);
		more_update_button=(Button)findViewById(R.id.more_update);
		weixin=(Button)findViewById(R.id.more_weixin);
		sinaweibo=(Button)findViewById(R.id.more_sinaweibo);
//		tencentweibo=(Button)findViewById(R.id.more_tencentweibo);  
	}
	public void Listen()
	{
 
		more_card_button.setOnClickListener(this);
		
		more_info_button.setOnClickListener(this);
		more_update_button.setOnClickListener(this);
		weixin.setOnClickListener(this);
		sinaweibo.setOnClickListener(this);
		//tencentweibo.setOnClickListener(this);
	}
	
	public void CheckVersion(){
		BaseApplication application=new BaseApplication();
		try{
			checkVersion=new ConnWeb().getAPK();
			Log.v("moreActivity", checkVersion.getServiceVersion());
			Log.v("moreActivity", checkVersion.getApk_url());
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		if (application.localVersion < Integer.parseInt(checkVersion.getServiceVersion())) {
			// 发现新版本，提示用户更新
						AlertDialog.Builder alert = new AlertDialog.Builder(this);
						alert.setTitle("软件升级")
								.setMessage("发现新版本,建议立即更新使用.")
								.setPositiveButton("更新",
										new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog,
													int which) {
												// 开启更新服务UpdateService
												// 这里为了把update更好模块化，可以传一些updateService依赖的值
												// 如布局ID，资源ID，动态获取的标题,这里以app_name为例
												Intent updateIntent = new Intent(
														moreActivity.this,
														UpdateService.class);
												updateIntent.putExtra("app_name",getResources().getString(R.string.app_name));
												updateIntent.putExtra("apk_url", checkVersion.getApk_url());
												startService(updateIntent);
											}
										})
								.setNegativeButton("取消",
										new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog,
													int which) {
												dialog.dismiss();
											}
										});
						alert.create().show();

		}else {
			Error_Dialog(moreActivity.this,"目前版本已经是最新版本" );
		}
		
		
	}
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.more_card:
			intent=new Intent(moreActivity.this,userRoomActivity.class);
			startActivity(intent);
			break;
		case R.id.more_info:
			intent=new Intent(moreActivity.this,modifiActivity.class);
			startActivity(intent);
			break;
		case R.id.more_update:
			CheckVersion();
			break;
		case R.id.more_weixin:
			try {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(WECHAT_ADDR));
				i.setPackage("com.tencent.mm");
				startActivity(i);
			} catch(Throwable t) {
				System.err.println("wechat client is not installed correctly or its version is too old.");				
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(WECHAT_ADDR));
				String title = "请选择微信客户端完成操作";
				startActivity(Intent.createChooser(i, title));
			}
			break;
		case R.id.more_sinaweibo:
			AbstractWeibo weibo = AbstractWeibo.getWeibo(moreActivity.this, SinaWeibo.NAME);
			weibo.setWeiboActionListener(this);				
			weibo.followFriend(SDK_SINAWEIBO_UID);
			break;
/*		case R.id.more_tencentweibo:
			AbstractWeibo tencentweibo = AbstractWeibo.getWeibo(
					moreActivity.this, TencentWeibo.NAME);
			tencentweibo.setWeiboActionListener(this);
			tencentweibo.followFriend(SDK_TENCENTWEIBO_UID);
			break; */
		default:
			break;
		}
		
	}
	
 
	
	@Override
	public void onCancel(AbstractWeibo arg0, int arg1) {
		Message msg = new Message();
		msg.arg1 = 3;
		msg.arg2 = arg1;
		msg.obj = arg0;
		handler.sendMessage(msg);
		Log.d("33333333333333333", msg+"");
		
	}
	@Override
	public void onComplete(AbstractWeibo arg0, int arg1,
			HashMap<String, Object> arg2) {
		Message msg = new Message();
		msg.arg1 = 1;
		msg.arg2 = arg1;
		msg.obj = arg0;
		
		handler.sendMessage(msg);
		Log.d("11111111111111111", msg+"");
		
	}
	@Override
	public void onError(AbstractWeibo arg0, int arg1, Throwable arg2) {
		Message msg = new Message();
		msg.arg1 = 2;
		msg.arg2 = arg1;
		msg.obj = arg0;
		
		handler.sendMessage(msg);
		Log.d("2222222222222", msg+"");
	}
	@Override
	public boolean handleMessage(Message msg) {
		String text = "";
		switch (msg.arg1) {
			case 1: { // 成功
				text = "关注新浪官方微博成功";
			}
			break;
			case 2: { // 失败
				text = "你已经关注了新浪官方微博";
			}
			break;
			case 3: { // 取消
			//	text = weibo.getName() + " canceled at " + text;
			}
			break;
		}
		
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
		return false;
	}
	
}
