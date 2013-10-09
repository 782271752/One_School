package li.dream;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.sun.org.apache.bcel.internal.generic.NEW;

import li.entity.Used;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class UsedDetailActivity extends loginActivity{

	private TextView name,describe,price,into_new,ori_price,
		buytime,shop_name,phone,duan;
	private Button phoneButton,duanButton;
	private ImageView duan_view;
	private LinearLayout duan_layout;
	private String pic_urls;
	private String[] pic_path;
	private Used used_detail;
	private Gallery usegly;
	DisplayImageOptions options;
	private String url;
	String[] dividepid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.used_detail);
		init();
		pic_path=new String[2];
		
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory()
		.cacheOnDisc()
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		
		getUseds();
		usegly.setAdapter(new ImageGalleryAdapter());
		usegly.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startImagePagerActivity(position);
			}
		});
		
	}
	public void init()
	{
		name=(TextView)findViewById(R.id.used_detail_name);
		describe=(TextView)findViewById(R.id.used_detail_describe);
		price=(TextView)findViewById(R.id.used_detail_money);
		into_new=(TextView)findViewById(R.id.used_detail_into_new);
		ori_price=(TextView)findViewById(R.id.used_detail_costprice);
		buytime=(TextView)findViewById(R.id.used_detail_time);
		shop_name=(TextView)findViewById(R.id.used_detail_boss);
		phone=(TextView)findViewById(R.id.used_detail_longtel);
		duan=(TextView)findViewById(R.id.used_detail_shorttel);
		
		phoneButton=(Button)findViewById(R.id.uesd_detail_longphone);
		duanButton=(Button)findViewById(R.id.uesd_detail_shortphone);
		
		duan_view=(ImageView)findViewById(R.id.used_detail_duan_view);
		duan_layout=(LinearLayout)findViewById(R.id.used_detail_duan_layout);
		usegly=(Gallery)findViewById(R.id.used_detail_gallery);
		
	}
	
	public void getUseds()
	{
		Bundle bundle = this.getIntent().getExtras();//获取Bundle
		used_detail=(Used)bundle.getSerializable("used_details");
		name.setText(used_detail.getLabel());
		describe.setText(used_detail.getDes());
		price.setText(used_detail.getPrice());
		into_new.setText(used_detail.getInto_new()+"成新");
		ori_price.setText(used_detail.getOri_price());
		buytime.setText(used_detail.getButtime());
		shop_name.setText(used_detail.getShop_name());
		phone.setText(used_detail.getLongtel());
		if(!used_detail.getShorttel().equals(""))
		{
			duan.setText(used_detail.getShorttel());
		}else {
			duan_view.setVisibility(View.GONE);
			duan_layout.setVisibility(View.GONE);
		}
		
		pic_urls=used_detail.getPicUrls();
		if(pic_urls.equals(""))
		{
			usegly.setVisibility(View.GONE);
		}else {
			String[] urlStr=pic_urls.split("\\|");
//			for (int i = 0; i < urlStr.length; i++) {
//				dividepid=urlStr[i].split("\\.");
//				urlStr[i]=urlStr[i]+"_s."+dividepid[dividepid.length-1];
//				Log.i("11111111111111111", urlStr[i]);
//			}
			pic_path=urlStr;
		}
		
		
		
	}
	

	private void startImagePagerActivity(int position) {
		Intent intent = new Intent(this, ImagePagerActivity.class);
		intent.putExtra("pic", pic_path);
		intent.putExtra("pic_position", position);
		startActivity(intent);
	}

	private class ImageGalleryAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return pic_path.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = (ImageView) convertView;
			if (imageView == null) {
				imageView = (ImageView) getLayoutInflater().inflate(R.layout.item_gallery_image, parent, false);
			}
			imageLoader.displayImage(pic_path[position], imageView, options);
			return imageView;
		}
	}
	
}
