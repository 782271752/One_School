package li.dream;

import java.net.URL;

import li.bitmap.FinalBitmap;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;

public class album_m_photo extends loginActivity{

	private ImageView m_photo;
	private Button comment;
	 
	private Context context;
	private String m_photo_url;
	private Bundle bundle;
	private Bitmap m_photoBp;
	
	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;
	
	private int mode = NONE;
	private float oldDist;
	private Matrix matrix = new Matrix();
	private Matrix savedMatrix = new Matrix();
	private PointF start = new PointF();
	private PointF mid = new PointF();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gv_m_photo);
		init();
		context=this;
		
		
		bundle=this.getIntent().getExtras();
		m_photo_url=bundle.getString("m_photo");
		Log.v("album_m_photo",m_photo_url);
		new m_photoTask().execute();
		
		m_photo.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageView view = (ImageView) v;
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					savedMatrix.set(matrix);
					start.set(event.getX(), event.getY());
					mode = DRAG;
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
					mode = NONE;
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					oldDist = spacing(event);
					if (oldDist > 10f) {
						savedMatrix.set(matrix);
						midPoint(mid, event);
						mode = ZOOM;
					}
					break;
				case MotionEvent.ACTION_MOVE:
					if (mode == DRAG) {
						matrix.set(savedMatrix);
						matrix.postTranslate(event.getX() - start.x, event.getY()
								- start.y);
					} else if (mode == ZOOM) {
						float newDist = spacing(event);
						if (newDist > 10f) {
							matrix.set(savedMatrix);
							float scale = newDist / oldDist;
							matrix.postScale(scale, scale, mid.x, mid.y);
						}
					}
					break;
				}

				view.setImageMatrix(matrix);
				return true;
			}
			
			private float spacing(MotionEvent event) {
				float x = event.getX(0) - event.getX(1);
				float y = event.getY(0) - event.getY(1);
				return FloatMath.sqrt(x * x + y * y);
			}

			private void midPoint(PointF point, MotionEvent event) {
				float x = event.getX(0) + event.getX(1);
				float y = event.getY(0) + event.getY(1);
				point.set(x / 2, y / 2);
			}
		});
	}
	
	public void init(){
		comment=(Button)findViewById(R.id.gv_m_photo_comment);
		m_photo=(ImageView)findViewById(R.id.gv_m_photo_image);
		
	}
	
	public class m_photoTask extends AsyncTask<Void, Integer, Void>{

		 
		@Override
		protected Void doInBackground(Void... params) {
			 publishProgress(30);
			 try{
				 URL picUrl = new URL(m_photo_url);
				  m_photoBp= BitmapFactory.decodeStream(picUrl.openStream());
			 }catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		 
		@Override
		protected void onPostExecute(Void result) {
			  
			 m_photo.setImageBitmap(m_photoBp);
			 publishProgress(100);
		}

		 
		@Override
		protected void onProgressUpdate(Integer... values) {
			if(values[0]==30)
			{
				show_dialog("º”‘ÿ¥ÛÕº£¨«Î…‘µ»...");
			}
			else
			{
				cancel_dialog();
			}
		}
		
	}
}
