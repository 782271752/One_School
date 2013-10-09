package li.dream;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

 

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import li.adapter.albumAdapter;
import li.adapter.hotelAdapter;
import li.adapter.hotelgalleryAdapter;
import li.dream.albumActivity.AlbumTask;
import li.entity.Album;
import li.entity.hotel;
import li.utils.ConnWeb;
import li.widget.CommentListView;
import li.widget.FlowIndicator;

public class hotelActivity extends loginActivity implements CommentListView.OnRefreshLoadingMoreListener{

//	static final int SCROLL_ACTION = 0;---------------------------广告位---------------------------------
//	private Gallery hotelGallery; ---------------------------广告位---------------------------------
//	private hotelgalleryAdapter gallery_adapter;  ---------------------------广告位---------------------------------
//	private hotelAdapter hotel_adapter;
	
//	private albumAdapter gallery_adapter;
	private hotelAdapter hotel_adapter;
	
//	private FlowIndicator hotel_View; ---------------------------广告位---------------------------------
	private CommentListView hotel_listview;
	
//	private List<Album> gallery_list;---------------------------广告位---------------------------------
	private List<hotel> hotel_list;
	private List<hotel> hotel_con;
	

	private Context context;
	private Timer timer;
	private boolean firstload=true;
	private final int Drag_index=1;
	private final int Loadmore_index=2;
	private final int Gallery_index=3;
	
	private int visibleCount;
	private int visibleLast;
	private Integer Start_Index=0;
	private FrameLayout hotel_gallery_layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel);
		hotel_list=new ArrayList<hotel>();
		hotel_con=new ArrayList<hotel>();
		//gallery_list=new ArrayList<Album>();---------------------------广告位---------------------------------
		context=this;
		init();
		timer=new Timer();
		hotel_listview.setOnRefreshListener(this); 
		onRefresh();
	//	new AlbumTask(this, Gallery_index).execute(Start_Index);---------------------------广告位---------------------------------
	
	/*	---------------------------广告位---------------------------------*/
/*		hotelGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				hotel_View.setSeletion(position);
				 
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		hotelGallery.setOnItemClickListener(new  OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				 
				
			}
		}); */
	//	timer.scheduleAtFixedRate(new MyTask(), 5000, 10000);---------------------------广告位---------------------------------
		
		
		hotel_listview.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				 
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				visibleCount = visibleItemCount;
				visibleLast = firstVisibleItem + visibleItemCount - 1;
				
			}
		});
		 hotel_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					hotel hotel_detail=hotel_list.get(position);
					Log.d("hotel", "---------------"+position+"----------"+ hotel_detail.toString()+"");
					Intent it = new Intent();//创建Intent对象
					Bundle bundle = new Bundle();//创建Bundle对象
					it.setClass(hotelActivity.this, hoteldetailActivity.class);
					bundle.putSerializable("hotel_details",(Serializable) hotel_detail);
					Log.d("hotel_detail", hotel_detail.toString());
					it.putExtras(bundle);
					startActivity(it);
				} catch (Exception e) {
					e.printStackTrace();
				}
				 
				 
				
			}
		});
	}

	public void init(){
//		gallery_adapter=new hotelgalleryAdapter(context, gallery_list);---------------------------广告位---------------------------------
//		hotel_adapter=new hotelAdapter(context, hotel_list);
		 
		hotel_adapter=new hotelAdapter(context, hotel_list);
		
	//	hotelGallery=(Gallery)findViewById(R.id.hotel_gallery);       广告栏
	//	hotel_View=(FlowIndicator)findViewById(R.id.hotel_view);  广告栏
		hotel_listview=(CommentListView)findViewById(R.id.hotel_list);
	//	hotel_gallery_layout=(FrameLayout)findViewById(R.id.hotel_gallery_layout);  广告栏
		
		
//		hotelGallery.setAdapter(gallery_adapter);---------------------------广告位---------------------------------
		hotel_listview.setAdapter(hotel_adapter);
		
		
	}
	
//	---------------------------广告位---------------------------------
/*	public class MyTask extends TimerTask{

		@Override
		public void run() {
			 
			 myhandler.sendEmptyMessage(SCROLL_ACTION);
			
		}
		Handler myhandler=new Handler(){

			 
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case SCROLL_ACTION:
					 int curPos = hotelGallery.getSelectedItemPosition();
					 if (curPos < gallery_adapter.getCount() - 1) {
					 curPos++;
					 } else {
					 curPos = 0;
					 }
					 hotelGallery.setLayoutAnimation(new LayoutAnimationController(
					 AnimationUtils.loadAnimation(hotelActivity.this,
					 R.anim.gallery_in)));
					 hotelGallery.setSelection(curPos, true);
 
					 
					break;

				default:
					break;
				}
			}
			
		};
	}*/
	
	public class HotelTask extends AsyncTask<Integer, Integer, Void>
	{
		private Context context;
		private int index;	// 用于判断是下拉刷新还是点击加载更多

		public HotelTask(Context context,int index){
			this.context = context;
			this.index = index;
		}

		 
		@Override
		protected Void doInBackground(Integer... params) {
			
			if(firstload&&index!=Gallery_index){
				publishProgress(30);
				
			}
			 
			if(index==Gallery_index){
				try {
				//	gallery_list.addAll(new ConnWeb().getAlbum(uid, params[0]));---------------------------广告位---------------------------------
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				try{				 	
				
					hotel_con=new ConnWeb().gethotel("url", "560", params[0]); 
					hotel_list.addAll(hotel_con);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			return null;
		}

	 
		 
		@Override
		protected void onProgressUpdate(Integer... values) {
			if(values[0]==30)
			{
				show_dialog("加载数据中,请稍等...");
			}
			else
			{
				cancel_dialog();
			}
		}


		@Override
		protected void onPreExecute() {
			 
			super.onPreExecute();
		}
		



		@Override
		protected void onPostExecute(Void result) {
			if(firstload&&index!=Gallery_index){ 
				publishProgress(100);
				firstload=false;
			}
			 if(index==Drag_index){
//				 adapter=new albumAdapter(albumActivity.this,getAlbum_Data());
				 if(hotel_list.size()==0){
					 Toast.makeText(context, "已加载全部数据",Toast.LENGTH_LONG).show();
					 Log.v("hotel_list", String.valueOf(hotel_list.size()));
				//	 hotel_listview.onRefreshComplete();
					
					 return;
				 }else{		
					 
					 try {
						 
						 
						 hotel_adapter.notifyDataSetChanged();
						 //album_listview.setAdapter(adapter);
						 hotel_listview.setSelection(visibleLast - visibleCount+1);
						// hotel_listview.onRefreshComplete();
					} catch (Exception e) {
						e.printStackTrace();
					}
					 
					  
				 }
			 }else if(index==Loadmore_index){
				  
				  
				 if (hotel_con.size()==0) {
					 Toast.makeText(context, "已加载全部数据",Toast.LENGTH_LONG).show();
					 hotel_listview.onLoadMoreComplete(false);
					  
					 return;
				}else{		
					hotel_adapter.notifyDataSetChanged();
					//album_listview.setAdapter(adapter);
					hotel_listview.setSelection(visibleLast - visibleCount+1);
					hotel_listview.onLoadMoreComplete(false);
				}
			 }else if(index==Gallery_index){
				 //---------------------------广告位---------------------------------
			/*		gallery_adapter.notifyDataSetChanged();
					hotel_View.setCount(gallery_adapter.getCount());
					hotel_gallery_layout.setVisibility(View.VISIBLE);*/
			 
			 }
		}		 
	}

	@Override
	public void onRefresh() {
		Start_Index=0;
		hotel_list.clear();
		new HotelTask(this, Drag_index).execute(Start_Index);	
		
	}

	@Override
	public void onLoadMore() {
		Start_Index=Start_Index+10;
		new HotelTask(this, Loadmore_index).execute(Start_Index);
		
	}
}
