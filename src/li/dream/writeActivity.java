package li.dream;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import li.bitmap.FinalBitmap;
import li.utils.BitmapUtil;
import li.utils.FileNameUtils;
import li.utils.ImageZoomUtils;
import li.utils.PhotoRequestUtil;
import li.utils.SDCardFileUtils;
import li.utils.StringUtils;

import org.omg.CORBA.PRIVATE_MEMBER;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class writeActivity extends loginActivity{

	private TextView title,count;
	private ImageView send,back;
	private ImageButton camera;
	private ImageView img;
	private EditText content;
	private int NONE = 0;
	 String filePath = "";
	 Bitmap bitmap;
	private String picPath;	//获取到的图片路径
	private Uri photoUri;
	/***
	 * 使用照相机拍照获取图片
	 */
	public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
	/***
	 * 使用相册中的图片
	 */
	public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
	/**
	 * 选择文件
	 */
	public static final int TO_SELECT_PHOTO = 3;

	private String key;
	String fileDir;
	private SetheadImageActivity cache=new SetheadImageActivity();
	private Bitmap bm;
	
/*	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0:
				Log.d("7777777777777777777", "-------");
				//img.setVisibility(View.VISIBLE );
				img.setImageURI(Uri.parse(picPath));
				Log.d("88888888888888888888888", img.getVisibility()+"");
				img.setVisibility(View.VISIBLE);
				Log.d("9999999999999999999", img.getVisibility()+"");
				break;
			}
		}
	};*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.write);
		
		fileDir = SDCardFileUtils.getSDCardPath() + "DCIM/Camera/";
		
		init();
		setListener();		
		title();
		
		
	}
	private void title()  //设置标题
	{
		key=this.getIntent().getExtras().getString("key");
		if(key.equals("wei_shuo")){
			title.setText("吐槽天地");
		}else if(key.equals("help")){
			title.setText("找人帮帮忙");
			content.setHint("遇到什么问题,让同校的同学朋友帮你解决");
		}else if(key.equals("wish")){
			title.setText("说出愿望");
			content.setHint("写下你的愿望,短号,对方的性别.给自己增加开心幸福的回忆");
		}
	}
	private void init(){
		back=(ImageView)findViewById(R.id.write_back);
		title=(TextView)findViewById(R.id.write_title_text);
		count=(TextView)findViewById(R.id.write_count);
		send=(ImageView)findViewById(R.id.write_send);
		camera=(ImageButton)findViewById(R.id.write_camera);
		content=(EditText)findViewById(R.id.write_edit);
		img=(ImageView)findViewById(R.id.write_upload_image);
	}
	private void setListener(){
		back.setOnClickListener(new OnClickListener() {   //点击返回按钮事件
			
			@Override
			public void onClick(View v) {
				 if(content.getText().toString().trim().length()>0){
					 backDialog();
				 }else {
					 finish();
					 overridePendingTransition(0, R.anim.roll_down);
					  
					
				}
				
			}
		});	
		send.setOnClickListener(new OnClickListener() {  //点击发送按钮
			
			@Override
			public void onClick(View v) {
				 if(content.getText().toString().trim().length()==0){
					 img.setVisibility(View.VISIBLE);
					 Toast.makeText(writeActivity.this, "您还未输入内容,请输入后重试",
								Toast.LENGTH_SHORT).show();
				 }else {
					
				}
				
			}
		});
		
		content.addTextChangedListener(new TextWatcher() {
			private CharSequence temp;
			private int selectionStart;
			private int selectionEnd;
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				temp = s;
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				int number = s.length();
				count.setText(String.valueOf(number));
				selectionStart = content.getSelectionStart();
				selectionEnd = count.getSelectionEnd();
				if (temp.length() > 140) {
					s.delete(selectionStart - 1, selectionEnd);
					int tempSelection = selectionEnd;
					content.setText(s);
					content.setSelection(tempSelection);
				}
				
			}
		});
		camera.setOnClickListener(new OnClickListener() { //点击拍照
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(writeActivity.this,selectActivity.class);
				startActivityForResult(intent, TO_SELECT_PHOTO);
				
			}
		});
		 
		img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	private void backDialog() {
		AlertDialog.Builder builder = new Builder(writeActivity.this);
		builder.setTitle("同校网");
		builder.setMessage("是否取消发布?");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				finish();
				overridePendingTransition(0, R.anim.roll_down);
				
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.create().show();
	}
	 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (content.getText().toString().trim().length() > 0) {
				backDialog();
			} else {
				finish();
				overridePendingTransition(0, R.anim.roll_down);
				 
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
//	private void PhotoDialog() {
//		AlertDialog.Builder builder = new Builder(writeActivity.this);
//		builder.setTitle("分享照片");
//		builder.setItems(new String[] { "拍照", "本地照片" },
//				new DialogInterface.OnClickListener() {
//
//					public void onClick(DialogInterface dialog, int which) {
//						Intent intent;
//						switch (which) {
//						case 0:
//							intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 添加相机的外部启用
//							SDCardFileUtils.creatDir2SDCard(fileDir);
//							intent.putExtra(MediaStore.EXTRA_OUTPUT,
//							Uri.fromFile(new File(fileDir, DateFormat.format("yyyy-MM-dd-hh-mm-ss", new Date()) + ".jpg")));
//							startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
//							break;
//
//						case 1:
//							intent = new Intent(Intent.ACTION_GET_CONTENT, null);
//							intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//									"image/*");//从image获取图片
//							startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
//							break;
//						}
//					}
//				});
//		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.cancel();
//			}
//		});
//		builder.create().show();
//	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO)
		{
			picPath = data.getStringExtra(selectActivity.KEY_PHOTO_PATH);
			Log.v("ZZZZZZZZZZZZZZZz", picPath);
//			imageView.setImageBitmap(BitmapFactory.decodeFile(picPath));
//			imageView.setVisibility(View.VISIBLE);
			Bitmap bitmap = BitmapUtil.compressBitmap(picPath, 200, 200);
			Drawable drawable = img.getDrawable();
			if (bitmap != null) {
			    Log.d("zj", "新的bitmap : "+bitmap+",大小： "+ BitmapUtil.getBitmapSize(bitmap));
 			    img.setImageBitmap(bitmap);
 			    img.setVisibility(View.VISIBLE);
 			    camera.setEnabled(false);
 			    
			} else {
			    Toast.makeText(this , "图片太大，内存空间不足，请重试", Toast.LENGTH_SHORT).show();
			}
			
			if (drawable != null) {
			    Bitmap bitmap1 = BitmapUtil.drawableToBitmap(this, drawable);
			    if (bitmap1 != null) {
			        Log.e("zj", "释放的bitmap : "+bitmap1+",大小： "+ BitmapUtil.getBitmapSize(bitmap1));
	                bitmap1.recycle();
	               // System.gc();
			    }
			   
			}
		}
		super.onActivityResult(requestCode, resultCode, data);

	}
	/**
	 * 获取系统默认存储真实文件路径
	 * 两个路径 一个是最后一张所拍图片路径  (由于设置了存储路径所以DCIM中会存储两张一模一样的图片，所以在此获取两张图片路径以便于缩放处理后全部删除)
	 * @param filePath
	 * @return
	 */
	protected String getRealFilePath() {
		String filePath = "";
		//MediaStore.Images.Media.EXTERNAL_CONTENT_URI content://media/external/images/media
		Cursor cursor = this.getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null,
				null, Media.DATE_MODIFIED + " desc");
		if (cursor.moveToNext()) {
				/**
				 *  _data：文件的绝对路径  Media.DATA='_data'
				 */
				filePath = cursor.getString(cursor.getColumnIndex(Media.DATA));
			}
		return filePath;
	}
	
	/**
	 * 缩小文件尺寸  (压缩文件大小)
	 * @param filePath
	 * @param hight
	 * @param width
	 */
	protected Bitmap zoomBitmap(String fromPath, int hight, int width,String topath) {
		int new_hight = 1024;
		int new_width = new_hight * width / hight;
		Bitmap bmp = null;
		try {
			bmp = ImageZoomUtils.getBitmapFromFile(fromPath, new_width,
					new_hight, topath);
		}catch(OutOfMemoryError e){
			Toast.makeText(this, "警告:手机内存不足", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return bmp;
	}
}


