package li.dream;

import li.entity.Tour;
import li.entity.hotel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class hoteldetailActivity extends loginActivity{
	
	private TextView name,hotelname,time,money,where,longtel,shorttel;
	private Gallery hotel_gallery;
	private WebView describe;
	private Button longtel_button,shorttel_button; 
	private LinearLayout shortel_layout;
	private hotel hotel_detail;
	private ProgressBar des_progressBar; 
	private String hotel_id,hotel_uid,hotel_desc,des,phone;
	private ImageView hotel_detail_back;
	private Uri uri;
	private Intent call_intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_detail);
		init();
		getHotels();
		
		longtel_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				phone=longtel.getText().toString().trim(); // 得到 长号的号码
				Intent phoneIntent = new Intent("android.intent.action.CALL",Uri.parse("tel:" + phone));
				startActivity(phoneIntent);
				
			}
		});
		
		shorttel_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				phone=shorttel.getText().toString().trim(); // 得到 短号的号码
				Intent phoneIntent = new Intent("android.intent.action.CALL",Uri.parse("tel:" + phone));
				startActivity(phoneIntent);
				
			}
		});
		hotel_detail_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hoteldetailActivity.this.finish();
				
			}
		});
	}
	
	
	public void init(){
		name=(TextView)findViewById(R.id.hotel_detail_name);
		hotelname=(TextView)findViewById(R.id.hotel_detail_hotelname);
		time=(TextView)findViewById(R.id.hotel_detail_time);
		money=(TextView)findViewById(R.id.hotel_detail_money);
		where=(TextView)findViewById(R.id.hotel_detail_where);
		longtel=(TextView)findViewById(R.id.hotel_detail_longtel);
		shorttel=(TextView)findViewById(R.id.hotel_detail_shorttel);
		Log.d("---------------",longtel.getText().toString()+shorttel.getText().toString()+"");
		hotel_detail_back=(ImageView)findViewById(R.id.hotel_detail_back);
		
		longtel_button=(Button)findViewById(R.id.hotel_detail_longtel_button);
		shorttel_button=(Button)findViewById(R.id.hotel_detail_shorttel_button);
		
		hotel_gallery=(Gallery)findViewById(R.id.hotel_detail_gallery);
		shortel_layout=(LinearLayout)findViewById(R.id.hotel_detail_shorttel_layout);
		
		describe=(WebView)findViewById(R.id.hotel_detail_describe);
		describe.getSettings().setJavaScriptEnabled(true); 
		describe.setInitialScale(78);
		describe.getSettings().setTextSize(BaseApplication.FONT_SIZES[3]);
		describe.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);	 
		
		des_progressBar=(ProgressBar)findViewById(R.id.hotel_detail_progressBar);
		
		
	
	}
	public void getHotels()
	{
		Bundle bundle = this.getIntent().getExtras();//获取Bundle
		hotel_detail=(hotel)bundle.getSerializable("hotel_details");
		hotel_id=hotel_detail.getHid();
		hotel_uid=hotel_detail.getUid();
		
		name.setText(hotel_detail.getName()); //旅游 名称
		hotelname.setText(hotel_detail.getHotelname());
	//	time.setText(hotel_detail.getTime());
		money.setText(hotel_detail.getMoney());
		where.setText(hotel_detail.getWhere());
		longtel.setText(hotel_detail.getLongtel()); //长号
		//---------------------------------------------------------------------------------//
		//---------------------------------短号需加判断---------------------------------------//
		
		if(!hotel_detail.getShorttel().equals(""))
		{
			shorttel.setText(hotel_detail.getShorttel());
		}else {
			shortel_layout.setVisibility(View.GONE); //短号布局不可见
		}
		hotel_desc=hotel_detail.getDescribe();
		
		Message msg=new Message();
		msg.what=0;
		myHandler.sendMessage(msg);
	}
	
	private Handler myHandler =new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				 des=hotel_desc.replace("/images/shop/hs", "http://121.199.40.201/images/shop/hs");
				 describe.loadDataWithBaseURL(null, des.toString(), "text/html", "utf-8", null);
				 des_progressBar.setVisibility(View.GONE);
				 describe.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}
		}		
	};
	
}
