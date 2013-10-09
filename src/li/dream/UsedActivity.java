package li.dream;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tencent.mm.sdk.platformtools.Log;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import li.adapter.TourAdapter;
import li.adapter.UsedAdapter;
import li.dream.tourActivity.TourAsync;
import li.entity.Tour;
import li.entity.Used;
import li.utils.ConnWeb;
import li.widget.CommentListView;

public class UsedActivity extends loginActivity implements CommentListView.OnRefreshLoadingMoreListener{

	private CommentListView used_ListView;
	private List<Used> used_list;
	private List<Used> used_con;
	private final int Drag_index=1;
	private final int Loadmore_index=2;
	private Integer Start_Index=0;
	private Bundle bundle;
	
	private int visibleCount;
	private int visibleLast;
	private UsedAdapter uadapter;
	private boolean firstload=true;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.used);
		used_list=new ArrayList<Used>();
		used_con=new ArrayList<Used>();
		context=this;
		init();
		used_ListView.setOnRefreshListener(this);
		onRefresh();
		
		used_ListView.setOnScrollListener(new OnScrollListener() {
			
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
		used_ListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					Used used_detail=used_list.get(position);
					Log.d("used_detail", "---------------"+position+"----------"+ used_detail.toString()+"");
					Intent it = new Intent();//创建Intent对象
					Bundle bundle = new Bundle();//创建Bundle对象
					it.setClass(UsedActivity.this, UsedDetailActivity.class);
					bundle.putSerializable("used_details",(Serializable) used_detail);
					Log.d("used_detail", used_detail.toString());
					it.putExtras(bundle);
					startActivity(it);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	public void init()
	{
		uadapter=new UsedAdapter(context, used_list);
		used_ListView=(CommentListView)findViewById(R.id.used_list);
		used_ListView.setAdapter(uadapter);
	}
	@Override
	public void onRefresh() {
		Start_Index=0;
		used_list.clear();
		new UsedAsync(context, Drag_index).execute(Start_Index);
		
	}

	@Override
	public void onLoadMore() {
		Start_Index=Start_Index+10;
		new UsedAsync(context, Loadmore_index).execute(Start_Index);
		
	}
	
	public class UsedAsync extends AsyncTask<Integer, Integer, Void>
	{
		private Context context;
		private int index;	// 用于判断是下拉刷新还是点击加载更多

		public UsedAsync(Context context,int index){
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
				used_con= new ConnWeb().getUseds("560", params[0]); //---------------------------------
				used_list.addAll(used_con);
			}catch (Exception e) {
				e.printStackTrace();
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
		protected void onPostExecute(Void result) {
			if(firstload){ 
				publishProgress(100);
				firstload=false;
			}
			 if(index==Drag_index){
//				 adapter=new albumAdapter(albumActivity.this,getAlbum_Data());
				 if(used_list.size()==0){
				//	 tour_ListView.onRefreshComplete();
					 Toast.makeText(context, "已加载全部数据",Toast.LENGTH_LONG).show();
					 return;
				 }else{		
					 try {
						 
						 
						 uadapter.notifyDataSetChanged();
						 used_ListView.setSelection(visibleLast - visibleCount+1);
					//	 tour_ListView.onRefreshComplete();
					} catch (Exception e) {
						e.printStackTrace();
					}
					 
					  
				 }
			 }else if(index==Loadmore_index){
				  
				  
				 if (used_con.size()==0) {
					 used_ListView.onLoadMoreComplete(false);
					 Toast.makeText(context, "没有数据更新",Toast.LENGTH_LONG).show();
					 return;
				}else{
					 
					 
					
					uadapter.notifyDataSetChanged();
					//album_listview.setAdapter(adapter);
					used_ListView.setSelection(visibleLast - visibleCount+1);
					used_ListView.onLoadMoreComplete(false);
				}
			 }
		}
		
	}

}
