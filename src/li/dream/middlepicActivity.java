package li.dream;


import li.utils.Zoompic;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class middlepicActivity extends loginActivity{
	
	public ImageView zoom_pic;
	public ProgressBar progress;
	public DisplayImageOptions options;
	public String m_pic_url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_m_photo);
		init();
		Zoompic zoompic=new Zoompic();
		zoompic.setZoompic(zoom_pic);
		
		m_pic_url=this.getIntent().getExtras().getString("m_pic_url");
		
		options = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.drawable.logo)
		.showImageOnFail(R.drawable.logo)
		.resetViewBeforeLoading()
		.cacheOnDisc()
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new FadeInBitmapDisplayer(300))
		.build();
		
		imageLoader.displayImage(m_pic_url,zoom_pic,options,new SimpleImageLoadingListener(){

			 
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				progress.setVisibility(View.VISIBLE);
			}

			 
			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason) {
				String message = null;
				switch (failReason.getType()) {
					case IO_ERROR:
						message = "Input/Output error";
						break;
					case DECODING_ERROR:
						message = "Image can't be decoded";
						break;
					case NETWORK_DENIED:
						message = "Downloads are denied";
						break;
					case OUT_OF_MEMORY:
						message = "Out Of Memory error";
						break;
					case UNKNOWN:
						message = "Unknown error";
						break;
				}
				Toast.makeText(middlepicActivity.this, message, Toast.LENGTH_SHORT).show();
				progress.setVisibility(View.GONE);
			}

			 
			@Override
			public void onLoadingComplete(String imageUri, View view,
					Bitmap loadedImage) {
				progress.setVisibility(View.GONE);
				
				
				
				
			}
			
		});
		
	}
	
/*	@Override
	protected void onDestroy() {
		imageLoader.clearMemoryCache();
		imageLoader.clearDiscCache();
	}*/
	public void init()
	{
		zoom_pic=(ImageView)findViewById(R.id.home_m_photo_image);
		progress=(ProgressBar)findViewById(R.id.home_m_photo_loading);
	}
}
