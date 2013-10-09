package li.dream;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebSettings.TextSize;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import li.dream.R;
import li.entity.Tour;

public class TourDetailActivity extends loginActivity implements OnClickListener{

	
	private TextView name,start,destin,days,price,username,longtel,shortel;
	private WebView detail;
	private Button shortelButton,longtelButton;
	private Gallery gallery;
	private LinearLayout short_layout;
	private String tour_id,tour_uid,tour_des,describ;
	private ProgressBar progressBar;
	private ImageView tourback;
	
	private String phone;
	private Uri uri;
	private Intent call_intent;
	
	
	private Tour tour_detail; //详情数据
	
	/* (non-Javadoc)
	 * @see li.dream.loginActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tour_detail);
		init();
		getTour();
		
		Message msg=new Message();
		msg.what=0;
		myHandler.sendMessage(msg);
	}
	
	public void init()
	{
		gallery=(Gallery)findViewById(R.id.tour_detail_gallery);//详情图片
		
		name=(TextView)findViewById(R.id.tour_detail_name);
		start=(TextView)findViewById(R.id.tour_detail_start);
		destin=(TextView)findViewById(R.id.tour_detail_destination);
		days=(TextView)findViewById(R.id.tour_detail_days);
		price=(TextView)findViewById(R.id.tour_detail_money);
		username=(TextView)findViewById(R.id.tour_detail_boss);
		longtel=(TextView)findViewById(R.id.tour_detail_longtel);
		shortel=(TextView)findViewById(R.id.tour_detail_shortel);
		
		shortelButton=(Button)findViewById(R.id.tour_detail_shorttel_button); //短号拨打
		longtelButton=(Button)findViewById(R.id.tour_detail_longtel_button);  //长号拨打
		
		detail=(WebView)findViewById(R.id.tour_detail_webview);//描述
		detail.getSettings().setJavaScriptEnabled(true); 
		detail.setInitialScale(78);
		detail.getSettings().setTextSize(BaseApplication.FONT_SIZES[3]);
		detail.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);	 
		
		short_layout=(LinearLayout)findViewById(R.id.tour_detail_short_layout);
		progressBar=(ProgressBar)findViewById(R.id.tour_detail_progressbar);
		tourback=(ImageView)findViewById(R.id.tour_detail_back);
	}
	
	public void getTour()
	{
		Bundle bundle = this.getIntent().getExtras();//获取Bundle
		tour_detail=(Tour)bundle.getSerializable("tour_details");
		tour_id=tour_detail.getId();
		tour_uid=tour_detail.getUid();
		
		name.setText(tour_detail.getName()); //旅游 名称
		start.setText(tour_detail.getStart());  //出发
		destin.setText(tour_detail.getDestin()); //终点
		days.setText(tour_detail.getDays()); //天数
		price.setText(tour_detail.getPrice()); //价格
		username.setText(tour_detail.getUsername()); //商家名称
		longtel.setText(tour_detail.getLong_tel()); //长号
		//---------------------------------------------------------------------------------//
		//---------------------------------短号需加判断---------------------------------------//
		
		if(!tour_detail.getShort_tel().equals(""))
		{
			shortel.setText(tour_detail.getShort_tel());
		}else {
			short_layout.setVisibility(View.GONE); //短号布局不可见
		}
		//shortel.setText(tour_detail.getShort_tel()); //短号
		
		tour_des=tour_detail.getDes();
		
	}
	
	private Handler myHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				 describ=tour_des.replace("/images/shop/t", "http://121.199.40.201/images/shop/t");
				 detail.loadDataWithBaseURL(null, describ.toString(), "text/html", "utf-8", null);
				 progressBar.setVisibility(View.GONE);
				 detail.setVisibility(View.VISIBLE);
				break;

			default:
				break;
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tour_detail_back:
				finish();
				break;
			//--------------------------拨打长号------------------------------------//
			case R.id.tour_detail_longtel_button:
				phone=longtel.getText().toString().trim(); // 得到 长号的号码
				uri=Uri.parse("tel:"+phone);
				call_intent.setAction(Intent.ACTION_CALL);
				call_intent.setData(uri);
				startActivity(call_intent);
				break;
				
			//--------------------------拨打短号------------------------------------//
			case R.id.tour_detail_shorttel_button:
				phone=shortel.getText().toString().trim(); // 得到 长号的号码
				uri=Uri.parse("tel:"+phone);
				call_intent.setAction(Intent.ACTION_CALL);
				call_intent.setData(uri);
				startActivity(call_intent);
				break;
			default:
				break;
		}
		
	}
	
}
