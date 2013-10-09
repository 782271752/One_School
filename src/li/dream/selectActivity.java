package li.dream;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import li.utils.FileNameUtils;
import li.utils.ImageZoomUtils;
import li.utils.SDCardFileUtils;
import li.utils.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class selectActivity extends Activity implements OnClickListener{

	private String fileDir;
	private LinearLayout dialogLayout;
	private Button takePhotoBtn,pickPhotoBtn,cancelBtn;
	private Intent lastIntent ;
	public String filePath = "";
	private Uri photoUri;
	public static final String KEY_PHOTO_PATH = "photo_path";
	Bitmap bitmap;
	/***
	 * 使用照相机拍照获取图�?
	 */
	public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
	/***
	 * 使用相册中的图片
	 */
	public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
	public String picPath;	//获取到的图片路径
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fileDir = SDCardFileUtils.getSDCardPath() + "DCIM/Camera/";
		setContentView(R.layout.select_pic_layout);
		initView();
	}
	private void initView() {
		dialogLayout = (LinearLayout) findViewById(R.id.dialog_layout);
		dialogLayout.setOnClickListener(this);
		takePhotoBtn = (Button) findViewById(R.id.btn_take_photo);
		takePhotoBtn.setOnClickListener(this);
		pickPhotoBtn = (Button) findViewById(R.id.btn_pick_photo);
		pickPhotoBtn.setOnClickListener(this);
		cancelBtn = (Button) findViewById(R.id.btn_cancel);
		cancelBtn.setOnClickListener(this);		
		lastIntent = getIntent();
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_layout:
			finish();
			break;
		case R.id.btn_take_photo:
			takePhoto();
			break;
		case R.id.btn_pick_photo:
			pickPhoto();
			break;
		default:
			finish();
			break;
		}
		
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return super.onTouchEvent(event);
	}
	public void takePhoto()
	{
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 添加相机的外部启用
		SDCardFileUtils.creatDir2SDCard(fileDir);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
		Uri.fromFile(new File(fileDir, DateFormat.format("yyyy-MM-dd-hh-mm-ss", new Date()) + ".jpg")));
		startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
	}
	private void pickPhoto() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
	}
	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_OK)
		{
			doPhoto(requestCode,data);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void doPhoto(int requestCode,Intent data)
	{
		if (requestCode == SELECT_PIC_BY_TACK_PHOTO) {
			Object object = null; 
			if(data!=null){
				
				if (data.getData() != null) {
					Cursor cursor = this.getContentResolver().query(data.getData(), null,
							null, null, null);
					if (cursor.moveToFirst()) {
						filePath = cursor.getString(cursor.getColumnIndex("_data"));// 获取绝对路径
					}
					cursor.close();
				}else{
					object = (Bitmap) (data.getExtras() == null ? null : data.getExtras().get("data"));
				}
			}
			//直接强转报错  这个主要是为了去高宽比例
			bitmap = object==null?null:(Bitmap)object;
			filePath = StringUtils.isNotBlank(filePath)?filePath:getRealFilePath();
			if (bitmap == null) {
				bitmap = BitmapFactory.decodeFile(filePath);
			}			
			picPath="/mnt/sdcard/Tongxiao/"+FileNameUtils.getName(filePath);
			zoomBitmap(filePath, bitmap.getHeight(), bitmap.getWidth(),picPath);
			lastIntent.putExtra(KEY_PHOTO_PATH, picPath);
			Log.d("XXXXXXXXXXXXXXXX", picPath);
			setResult(Activity.RESULT_OK, lastIntent);
			finish();
		}
		if(requestCode == SELECT_PIC_BY_PICK_PHOTO )  //从相册取图片，有些手机有异常情况，请注意
		{
			String photo_file="";
			if(data == null)
			{
				Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
				return;
			}
			photoUri = data.getData();
			if(photoUri == null )
			{
				Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
				return;
			}
			String[] pojo = {MediaStore.Images.Media.DATA};
			Cursor cursor = managedQuery(photoUri, pojo, null, null,null);   
			if(cursor != null )
			{
				int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
				cursor.moveToFirst();
				photo_file = cursor.getString(columnIndex);
				cursor.close();
			}
			
			if(photo_file != null && ( photo_file.endsWith(".png") || photo_file.endsWith(".PNG") ||photo_file.endsWith(".jpg") ||photo_file.endsWith(".JPG")  ))
			{	
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				Bitmap bitmap = BitmapFactory.decodeFile(photo_file,options);
				
				picPath="/mnt/sdcard/Tongxiao/"+FileNameUtils.getName(photo_file);
				zoomBitmap(photo_file,options.outHeight, options.outWidth,picPath);
			
			
				lastIntent.putExtra(KEY_PHOTO_PATH, photo_file);
				setResult(Activity.RESULT_OK, lastIntent);
			//	bitmap = BitmapFactory.decodeFile(photo_file);
				
				finish();
			}else{
				
			}
		} 
		
	}
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
