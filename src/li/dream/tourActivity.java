package li.dream;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xml.internal.security.Init;
import com.tencent.mm.sdk.platformtools.Log;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import li.adapter.TourAdapter;
import li.adapter.albumAdapter;
import li.dream.albumActivity.AlbumTask;
import li.entity.Album;
import li.entity.Tour;
import li.entity.note;
import li.utils.ConnWeb;
import li.widget.CommentListView;

public class tourActivity extends loginActivity implements CommentListView.OnRefreshLoadingMoreListener{
	
	private CommentListView tour_ListView;
	private List<Tour> tour_list;
	private List<Tour> tour_con;
	private final int Drag_index=1;
	private final int Loadmore_index=2;
	private Integer Start_Index=0;
	private Bundle bundle;
	private String uid="100000"; //--------------------------------------------
	private int visibleCount;
	private int visibleLast;
	private String pid;
	private TourAdapter tadapter;
	private boolean firstload=true;
	private Context context;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tour);
		tour_list=new ArrayList<Tour>();
		tour_con=new ArrayList<Tour>();
		context=this;
		init();
		tour_ListView.setOnRefreshListener(this);
		onRefresh();
		
		tour_ListView.setOnScrollListener(new OnScrollListener() {
			
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
		
		tour_ListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					Tour tour_detail=tour_list.get(position);
					Log.d("tour", "---------------"+position+"----------"+ tour_detail.toString()+"");
					Intent it = new Intent();//创建Intent对象
					Bundle bundle = new Bundle();//创建Bundle对象
					it.setClass(tourActivity.this, TourDetailActivity.class);
					bundle.putSerializable("tour_details",(Serializable) tour_detail);
					Log.d("tour_detail", tour_detail.toString());
					it.putExtras(bundle);
					startActivity(it);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	public void  init()
	{
		tadapter=new TourAdapter(context, tour_list);
		tour_ListView=(CommentListView)findViewById(R.id.tour_list);
		tour_ListView.setAdapter(tadapter);
	}
	@Override
	public void onRefresh() {
		Start_Index=0;
		tour_list.clear();
		new TourAsync(context, Drag_index).execute(Start_Index);
		
	}

	@Override
	public void onLoadMore() {
		Start_Index=Start_Index+10;
		new TourAsync(context, Loadmore_index).execute(Start_Index);
		
	}
	public class TourAsync extends AsyncTask<Integer, Integer, Void>
	{
		private Context context;
		private int index;	// 用于判断是下拉刷新还是点击加载更多

		public TourAsync(Context context,int index){
			this.context = context;
			this.index = index;
		}

		@Override
		protected Void doInBackground(Integer... params) {
			if(firstload){
				publishProgress(30);
				
			}
			 
			//----------------------------修改school id----------------------------------------------------
			try{				 	
				tour_con= new ConnWeb().getTour("url", "560", params[0]); //---------------------------------
				tour_list.addAll(tour_con);
			}catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		
		@Override
		protected void onPostExecute(Void result) {
			if(firstload){ 
				publishProgress(100);
				firstload=false;
			}
			 if(index==Drag_index){
//				 adapter=new albumAdapter(albumActivity.this,getAlbum_Data());
				 if(tour_list.size()==0){
				//	 tour_ListView.onRefreshComplete();
					 Toast.makeText(context, "已加载全部数据",Toast.LENGTH_LONG).show();
					 return;
				 }else{		
					 try {
						 
						 
						 tadapter.notifyDataSetChanged();
						 tour_ListView.setSelection(visibleLast - visibleCount+1);
					//	 tour_ListView.onRefreshComplete();
					} catch (Exception e) {
						e.printStackTrace();
					}
					 
					  
				 }
			 }else if(index==Loadmore_index){
				  
				  
				 if (tour_con.size()==0) {
					 tour_ListView.onLoadMoreComplete(false);
					 Toast.makeText(context, "没有数据更新",Toast.LENGTH_LONG).show();
					 return;
				}else{
					 
					 
					
					tadapter.notifyDataSetChanged();
					//album_listview.setAdapter(adapter);
					tour_ListView.setSelection(visibleLast - visibleCount+1);
					tour_ListView.onLoadMoreComplete(false);
				}
			 }
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
		
	}

}
