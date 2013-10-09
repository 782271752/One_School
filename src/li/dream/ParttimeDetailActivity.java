package li.dream;

import com.sun.org.apache.xml.internal.security.Init;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import li.entity.Parttime;
import li.widget.CommentListView;

public class ParttimeDetailActivity extends loginActivity implements CommentListView.OnRefreshLoadingMoreListener{

	private TextView name,time,username,area,price,num,days,work_hour,sex,
				long_tel,short_tel;
	private ProgressBar progressBar;
	private WebView detail;
	private ImageView back;
	private LinearLayout short_layout;
	private String parttime_des,describ;
	private String phone;
	private Uri uri;
	private Intent call_intent;
	private Parttime parttime_detail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.part_time_job_detail);
		
		init();
		getParttime();
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	public void init()
	{
		name=(TextView)findViewById(R.id.part_time_job_detail_name);
		time=(TextView)findViewById(R.id.part_time_job_detail_time);
		username=(TextView)findViewById(R.id.part_time_job_detail_boss);
		area=(TextView)findViewById(R.id.part_time_job_detail_place);
		price=(TextView)findViewById(R.id.part_time_job_detail_money);
		num=(TextView)findViewById(R.id.part_time_job_detail_num);
		days=(TextView)findViewById(R.id.part_time_job_detail_days);
		work_hour=(TextView)findViewById(R.id.part_time_job_detail_work_time);
		sex=(TextView)findViewById(R.id.part_time_job_detail_sex);
		long_tel=(TextView)findViewById(R.id.part_time_job_detail_longtel);
		short_tel=(TextView)findViewById(R.id.part_time_job_detail_shorttel);
		
		progressBar=(ProgressBar)findViewById(R.id.part_time_job_detail_progressBar);
		
		detail=(WebView)findViewById(R.id.part_time_job_detail_describe);
		detail.getSettings().setJavaScriptEnabled(true); 
		detail.setInitialScale(78);
		detail.getSettings().setTextSize(BaseApplication.FONT_SIZES[3]);
		detail.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);	 
		
		back=(ImageView)findViewById(R.id.part_time_job_detail_back);
		short_layout=(LinearLayout)findViewById(R.id.part_time_job_detail_shortlayout);
	}
	public void getParttime()
	{
		Bundle bundle = this.getIntent().getExtras();//获取Bundle
		parttime_detail=(Parttime)bundle.getSerializable("parttime_details");
		name.setText(parttime_detail.getName());
		time.setText(parttime_detail.getTime());
		username.setText(parttime_detail.getUsername());
		area.setText(parttime_detail.getArea());
		price.setText(parttime_detail.getReward());
		num.setText(parttime_detail.getNum());
		days.setText(parttime_detail.getDays());
		work_hour.setText(parttime_detail.getWork_hour());
		
		//--------------------------招聘性别----------------------------------//
		if(parttime_detail.getSex().equals("0"))
		{
			sex.setText("女");
		}else if(parttime_detail.getSex().equals("1")){
			sex.setText("男");
		}else {
			sex.setText("不限");
		}
		
		long_tel.setText(parttime_detail.getLongtel());
		if(!parttime_detail.getShort_tel().equals(""))
		{
			short_tel.setText(parttime_detail.getShort_tel());
		}else {
			short_layout.setVisibility(View.GONE); //短号布局不可见
		}
		parttime_des= parttime_detail.getDes();
		Message msg=new Message();
		msg.what=0;
		myHandler.sendMessage(msg);
	}
	private Handler myHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				 describ=parttime_des.replace("/images/shop/v", "http://121.199.40.201/images/shop/v");
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
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}
	
}
