package li.dream;


import li.entity.youji;
import li.entity.youji_content;
import li.utils.ConnWeb;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebSettings.TextSize;
import android.widget.TextView;

public class youjidetailActivity extends loginActivity{

	public static final TextSize[] FONT_SIZES = new TextSize[]
			{TextSize.SMALLER,TextSize.NORMAL,TextSize.LARGER,TextSize.LARGEST}; 
	private WebView webView;
	private String content; 
	private String replace_content;
	private youji_content yj_content;
	 
	
	private TextView start,		//出发地点
			time,name,destination,groups,costs,start_time,days;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.youji_detail);
		init();
		getYouji();
		new youjidetailTask().execute();
	}
	public void init(){
		webView=(WebView)findViewById(R.id.youji_webview);
		webView.getSettings().setJavaScriptEnabled(true); 
		webView.setInitialScale(78);
		webView.getSettings().setTextSize(FONT_SIZES[3]);
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);	 
		
		start=(TextView)findViewById(R.id.youji_detail_start); //出发地点
		destination=(TextView)findViewById(R.id.youji_detail_destination); //终点站
		name=(TextView)findViewById(R.id.youji_detail_name);  //文章标题
		time=(TextView)findViewById(R.id.youji_detail_name);  //发表时间
		costs=(TextView)findViewById(R.id.youji_detail_costs); //花费
		groups=(TextView)findViewById(R.id.youji_detail_groups); //结伴方式
		days=(TextView)findViewById(R.id.youji_detail_days); //行程
		start_time=(TextView)findViewById(R.id.youji_detail_starttime);//出发时间
		 
	}
	
	public void getYouji(){
		Bundle bundle=this.getIntent().getExtras();
		youji yj=(youji)bundle.getSerializable("youji");
		name.setText(yj.getTitle());
		time.setText(yj.getTime());
		start.setText(yj.getStart());
		destination.setText(yj.getDestination());
		days.setText(yj.getDays());
		start_time.setText(yj.getStart_time());
		costs.setText(yj.getCosts());
		groups.setText(yj.getGroups());
	}
	
	public class youjidetailTask extends AsyncTask<Void, Integer, Void>{

		 
		@Override
		protected Void doInBackground(Void... params) {
			publishProgress(30);
			try {
				yj_content=new ConnWeb().getContent();
				content=yj_content.getContent();
				
				
				Log.v("youjiActivity", content);
				replace_content=content.replace("/images/shop/t", "http://121.199.40.201/images/shop/t");
				Log.v("youjiActivity", "replace");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		 
		@Override
		protected void onPostExecute(Void result) {
			webView.loadDataWithBaseURL(null, replace_content.toString(), "text/html", "utf-8", null);
			 
			publishProgress(100);
		}

		 
		@Override
		protected void onProgressUpdate(Integer... values) {
			if(values[0]==30){
				show_dialog("正在加载");
			}else{
				cancel_dialog();
			}
		}
		
	}
}
