package li.dream;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.lang.model.type.NullType;

import li.utils.ConnWeb;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class SetheadImageActivity extends loginActivity{

	private Button local_head,take_head,send_head;
	private ImageView head;
 
	private boolean is_finish;
	String photo_name;
	private SharedPreferences sp;
	
	
	private static final int NONE = 0;
	private static final int PHOTOHRAPH = 1;// 拍照,有sdk
	private static final int PHOTOZOOM = 2; // 缩放
	private static final int PHOTORESOULT = 3;// 结果

	private static final String IMAGE_UNSPECIFIED = "image/*";

	private static File file;
	private String SDK_file=getCache();
	 
	String photo_base ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.head_select);
		sp=this.getSharedPreferences("userinfo",MODE_PRIVATE);////获取SharedPreferences对象
		photo_name=getFilename();
		init();
		
		
		local_head.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						"image/*");//从image获取图片
				startActivityForResult(intent, PHOTOZOOM);
			}
		});
		
		take_head.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 添加相机的外部启用

				createPath(SDK_file);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
						SDK_file+ "head.jpg")));
				System.out.println("=============" + Environment.getExternalStorageDirectory());
				startActivityForResult(intent, PHOTOHRAPH);
			}
		});
		
		send_head.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(head.getVisibility()==View.INVISIBLE){
					Error_Dialog(SetheadImageActivity.this,"请先上传头像");
				}else{
					headImageTask headTask=new headImageTask();
					headTask.execute(new ConnWeb().loginUrl);
				}
			}
		});
	}
	public void createPath(String path) {
	     file = new File(path);
	    if (!file.exists()) {
	    	file.mkdir();
	    }
       
   }
	private void init()
	{
		local_head=(Button)findViewById(R.id.headselect_select_head);
		take_head=(Button)findViewById(R.id.headselect_take_photo);
		send_head=(Button)findViewById(R.id.headsend_head);
		head=(ImageView)findViewById(R.id.headselect_head);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == NONE)
			return;
		// 拍照
		if (requestCode == PHOTOHRAPH) {
			// 设置文件保存路径这里放在跟目录下
			File picture = new File(SDK_file
					+ "head.jpg");
			System.out.println("------------------------" + picture.getPath());
			startPhotoZoom(Uri.fromFile(picture));
		}

		if (data == null)
			return;

		// 读取相册缩放图片
		if (requestCode == PHOTOZOOM) {
			startPhotoZoom(data.getData());
		}
		// 处理结果
		if (requestCode == PHOTORESOULT) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				
				ByteArrayOutputStream baops=new ByteArrayOutputStream();
				StoreInSDK(photo, SDK_file);
				head.setImageBitmap(photo);
				head.setVisibility(View.VISIBLE);
				
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 50, stream);
				byte[] bt= stream.toByteArray();
				photo_base=Base64.encodeToString(bt, Base64.DEFAULT);
				
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150); 
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTORESOULT);
	}
	static String  status = Environment.getExternalStorageState();
	
	public boolean hasSdcard() {
		     
		      if (status.equals(Environment.MEDIA_MOUNTED)) {
		          return true;
		      } else {
		          return false;
		     }
	}
	 public String getCache()
	 {
		 String file_url="";
		 if(hasSdcard())
		 {
			 file_url="/mnt/sdcard/Tongxiao/";
		 }else {
			 file_url="/data/data/Tongxiao/";
		}
		 return file_url;
	 }
	 public void StoreInSDK(Bitmap bp,String path)
		{
			File file=new File(path);
			if(!file.exists())
			{
				file.mkdirs();
			}
			File imageFile=new File(file,photo_name+".png");
			try {
				imageFile.createNewFile();
				FileOutputStream fos=new FileOutputStream(imageFile);
				bp.compress(CompressFormat.JPEG, 90,fos );
				Toast.makeText(SetheadImageActivity.this, "成功", Toast.LENGTH_LONG).show();
				fos.flush();
				fos.close();
			} catch ( Exception e) {
				e.printStackTrace();
				Toast.makeText(SetheadImageActivity.this, "显示图片出错", Toast.LENGTH_LONG).show();
				}
				
		}
		
		public static String getFilename()
		{
			String filenameRandom=getName();
			return filenameRandom;
		}
		
		
		public static String getName()
		{
			String name="";
			UUID uid=UUID.randomUUID();
			name=uid.toString();
			return name;
		}
	
		public class headImageTask extends AsyncTask<String, Integer, String>
		{

			@Override
			protected String doInBackground(String... params) {
				
				publishProgress(30);
				
				
				String rs="";
				List<NameValuePair> param=new ArrayList<NameValuePair>();
				//使用NameValuePair类来保存键值对，使用NameValuePair类是因为下面需要的那个类的参数要求
				param.add(new BasicNameValuePair("head", photo_base));				
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
				if(result=="0")
				{
					cancel_dialog();
					Error_Dialog(SetheadImageActivity.this,"无法连接到网络，请检查网络配置...");
				}
				try {
					JSONObject jObject=new JSONObject(result);
					is_finish=jObject.getBoolean("is_Exit");
					if(is_finish)
					{
						publishProgress(100);
						Editor editor=sp.edit();
						editor.putString("photo", SDK_file+photo_name+".png");
						editor.commit();
						
						Intent intent=new Intent(SetheadImageActivity.this,modifiActivity.class);
						startActivity(intent);
						SetheadImageActivity.this.finish();
						
					}else{
						Error_Dialog(SetheadImageActivity.this,"设置失败，请重新上传");
					}
				} catch (Exception e) {
					e.printStackTrace();
					
				}
			}

			@Override
			protected void onProgressUpdate(Integer... values) {
				if(values[0]==30)
				{
					show_dialog("上传头像中，请稍等...");
				}
				else
				{
					cancel_dialog();
				}
			}

			
		}
}
