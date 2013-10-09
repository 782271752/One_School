 
package li.dream;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


import li.annotation.EventListener;
import li.annotation.Select;
import li.annotation.ViewInject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;

public class FinalActivity extends Activity {
	private ProgressDialog loginPD;
	public static String TEST_IMAGE;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initImagePath();
	}
	
	public boolean checkNetwork(Context context){
		boolean flag=false;
		ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conMan == null) {
			flag= false;
		} else {
			NetworkInfo[] info = conMan.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						flag= true;
						break;
					}
				}
			}
		}
		return flag;
	}
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		initView();
	}
	public void show_dialog(String title)
    {
    	loginPD=new ProgressDialog(this);
        loginPD.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置进度条风格，风格为圆形，旋转的
        loginPD.setTitle("同校网");
         
        loginPD.setMessage(title);
        loginPD.setIndeterminate(false);//设置ProgressDialog 的进度条是否不明确
        loginPD.setCancelable(false);//设置ProgressDialog 是否可以按退回按键取消
        loginPD.show();
        
    }
	public void cancel_dialog()
	 {
	    loginPD.dismiss();
	 }
	
	public void Error_Dialog(Context context,String message)
	{
		new AlertDialog.Builder(context).setTitle("提示").setMessage(message)
		.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		}).show();
	}
	
	 public String ConnectWeb(List<NameValuePair> param, String...params)
	 {
		 String rs="";
		 try {
			 	HttpClient httpClient= new DefaultHttpClient();////生成一个http客户端对象
				HttpParams httpparams=httpClient.getParams();
				
				HttpConnectionParams.setConnectionTimeout(httpparams, 20000);//设置连接超时时间
				HttpConnectionParams.setSoTimeout(httpparams, 600000);//设置请求超时
				HttpPost post=new HttpPost(params[0]);//客户端向服务器发送请求,返回一个响应对象
				post.setEntity(new UrlEncodedFormEntity(param,HTTP.UTF_8));
				
				HttpResponse response =new DefaultHttpClient().execute(post);
				
				if(response.getStatusLine().getStatusCode()==200)
				{
					rs=EntityUtils.toString(response.getEntity());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				rs="0";
			}
			return rs;
	
	 }

	
	 
	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
		initView();
	}



	public void setContentView(View view) {
		super.setContentView(view);
		initView();
	}



	private void initView(){
		Field[] fields = getClass().getDeclaredFields();
		if(fields!=null && fields.length>0){
			for(Field field : fields){
				ViewInject viewInject = field.getAnnotation(ViewInject.class);
				if(viewInject!=null){
					int viewId = viewInject.id();
					try {
						field.setAccessible(true);
						field.set(this,findViewById(viewId));
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					String clickMethod = viewInject.click();
					if(!TextUtils.isEmpty(clickMethod))
						setViewClickListener(field,clickMethod);
					
					String longClickMethod = viewInject.longClick();
					if(!TextUtils.isEmpty(longClickMethod))
						setViewLongClickListener(field,longClickMethod);
					
					String itemClickMethod = viewInject.itemClick();
					if(!TextUtils.isEmpty(itemClickMethod))
						setItemClickListener(field,itemClickMethod);
					
					String itemLongClickMethod = viewInject.itemLongClick();
					if(!TextUtils.isEmpty(itemLongClickMethod))
						setItemLongClickListener(field,itemLongClickMethod);
					
					Select select = viewInject.select();
					if(!TextUtils.isEmpty(select.selected()))
						setViewSelectListener(field,select.selected(),select.noSelected());
					
				}
			}
		}
	}
	
	
	private void setViewClickListener(Field field,String clickMethod){
		try {
			Object obj = field.get(this);
			if(obj instanceof View){
				((View)obj).setOnClickListener(new EventListener(this).click(clickMethod));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setViewLongClickListener(Field field,String clickMethod){
		try {
			Object obj = field.get(this);
			if(obj instanceof View){
				((View)obj).setOnLongClickListener(new EventListener(this).longClick(clickMethod));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setItemClickListener(Field field,String itemClickMethod){
		try {
			Object obj = field.get(this);
			if(obj instanceof AbsListView){
				((AbsListView)obj).setOnItemClickListener(new EventListener(this).itemClick(itemClickMethod));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setItemLongClickListener(Field field,String itemClickMethod){
		try {
			Object obj = field.get(this);
			if(obj instanceof AbsListView){
				((AbsListView)obj).setOnItemLongClickListener(new EventListener(this).itemLongClick(itemClickMethod));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setViewSelectListener(Field field,String select,String noSelect){
		try {
			Object obj = field.get(this);
			if(obj instanceof View){
				((AbsListView)obj).setOnItemSelectedListener(new EventListener(this).select(select).noSelect(noSelect));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void initImagePath() {
		try {
			if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
					&& Environment.getExternalStorageDirectory().exists()) {
				TEST_IMAGE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/two.png";
			}
			else {
				TEST_IMAGE = getApplication().getFilesDir().getAbsolutePath() + "/two.png";
			}
			File file = new File(TEST_IMAGE);
			if (!file.exists()) {
				file.createNewFile();
				Bitmap pic = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
				FileOutputStream fos = new FileOutputStream(file);
				pic.compress(CompressFormat.JPEG, 75, fos);
				fos.flush();
				fos.close();
			}
		} catch(Throwable t) {
			t.printStackTrace();
			TEST_IMAGE = null;
		}
	}
	
}
