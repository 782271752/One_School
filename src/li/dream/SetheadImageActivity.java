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
	private static final int PHOTOHRAPH = 1;// ����,��sdk
	private static final int PHOTOZOOM = 2; // ����
	private static final int PHOTORESOULT = 3;// ���

	private static final String IMAGE_UNSPECIFIED = "image/*";

	private static File file;
	private String SDK_file=getCache();
	 
	String photo_base ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.head_select);
		sp=this.getSharedPreferences("userinfo",MODE_PRIVATE);////��ȡSharedPreferences����
		photo_name=getFilename();
		init();
		
		
		local_head.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						"image/*");//��image��ȡͼƬ
				startActivityForResult(intent, PHOTOZOOM);
			}
		});
		
		take_head.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// ���������ⲿ����

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
					Error_Dialog(SetheadImageActivity.this,"�����ϴ�ͷ��");
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
		// ����
		if (requestCode == PHOTOHRAPH) {
			// �����ļ�����·��������ڸ�Ŀ¼��
			File picture = new File(SDK_file
					+ "head.jpg");
			System.out.println("------------------------" + picture.getPath());
			startPhotoZoom(Uri.fromFile(picture));
		}

		if (data == null)
			return;

		// ��ȡ�������ͼƬ
		if (requestCode == PHOTOZOOM) {
			startPhotoZoom(data.getData());
		}
		// ������
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
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
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
				Toast.makeText(SetheadImageActivity.this, "�ɹ�", Toast.LENGTH_LONG).show();
				fos.flush();
				fos.close();
			} catch ( Exception e) {
				e.printStackTrace();
				Toast.makeText(SetheadImageActivity.this, "��ʾͼƬ����", Toast.LENGTH_LONG).show();
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
				//ʹ��NameValuePair���������ֵ�ԣ�ʹ��NameValuePair������Ϊ������Ҫ���Ǹ���Ĳ���Ҫ��
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
					Error_Dialog(SetheadImageActivity.this,"�޷����ӵ����磬������������...");
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
						Error_Dialog(SetheadImageActivity.this,"����ʧ�ܣ��������ϴ�");
					}
				} catch (Exception e) {
					e.printStackTrace();
					
				}
			}

			@Override
			protected void onProgressUpdate(Integer... values) {
				if(values[0]==30)
				{
					show_dialog("�ϴ�ͷ���У����Ե�...");
				}
				else
				{
					cancel_dialog();
				}
			}

			
		}
}
