package li.dream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import li.adapter.albumAdapter;
import li.entity.Album;
import li.utils.ConnWeb;
import li.widget.DragListView;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
public class albumActivity extends loginActivity implements DragListView.OnRefreshLoadingMoreListener{
 
	private DragListView album_listview;
	private List<Album> album_list;
	private List<Album> album_con;
	private final int Drag_index=1;
	private final int Loadmore_index=2;
	private Integer Start_Index=0;
	private Bundle bundle;
	private String uid="100000";
	private int visibleCount;
	private int visibleLast;
	private String pid;
	private albumAdapter adapter;
	private boolean firstload=true;
	//public List<Map<String,Object>> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.album);		
		album_list=new ArrayList<Album>();
		album_con=new ArrayList<Album>();
		init();
	//	bundle=this.getIntent().getExtras();
	//	uid=bundle.getString("uid");
		album_listview.setOnRefreshListener(this);
		onRefresh();
		
		
		album_listview.setOnItemClickListener(new OnItemClickListener() {

			 
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Album albums=album_list.get(position);
				try {
					pid=album_list.get(position-1).getPid();
				
					
					
					 Log.v("pid", pid);
					 Log.v("position", String.valueOf(position));
				//	 System.out.print(pid);
				//	 System.out.print(String.valueOf(position));
					 Intent intent=new Intent();
					 Bundle bundle=new Bundle();
					 intent.setClass(albumActivity.this, albumItemActivity.class);
					 bundle.putString("pid", pid);
					 intent.putExtras(bundle);
					 startActivity(intent);
				} catch (Exception e) {
					e.printStackTrace();
					Log.v("pid", pid);
					Log.v("position", String.valueOf(position));
					 
				}
				 
				
			}
			
		});
		album_listview.setOnScrollListener(new OnScrollListener() {
			
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
	}
	
	public void init()
	{
		adapter=new albumAdapter(this, album_list);
		album_listview=(DragListView)findViewById(R.id.album_list);
		album_listview.setAdapter(adapter);
	}
	

	
	@Override
	public void onRefresh() {
		Start_Index=0;
		album_list.clear();
		 new AlbumTask(this, Drag_index).execute(Start_Index);	
	}

	 
	@Override
	public void onLoadMore() {
		 Start_Index=Start_Index+1;
		 new AlbumTask(this, Loadmore_index).execute(Start_Index);
		
	}
 
	public class AlbumTask extends AsyncTask<Integer, Integer, Void>
	{
		private Context context;
		private int index;	// 用于判断是下拉刷新还是点击加载更多

		public AlbumTask(Context context,int index){
			this.context = context;
			this.index = index;
		}

		 
		@Override
		protected Void doInBackground(Integer... params) {
			
			if(firstload){
				publishProgress(30);
				
			}
			 
			
			try{				 	
				album_con= new ConnWeb().getAlbum(uid, params[0]);
				album_list.addAll(album_con);
			}catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

	 
		 
		@Override
		protected void onProgressUpdate(Integer... values) {
			if(values[0]==30)
			{
				show_dialog("获取相册数据,请稍等...");
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
			if(firstload){ 
				publishProgress(100);
				firstload=false;
			}
			 if(index==Drag_index){
//				 adapter=new albumAdapter(albumActivity.this,getAlbum_Data());
				 if(album_list.size()==0){
					 album_listview.onRefreshComplete();
					 Toast.makeText(context, "没有数据更新",Toast.LENGTH_LONG).show();
					 return;
				 }else{		
					 //Start_Index=album_list.size();
					 try {
						 
						 
						 adapter.notifyDataSetChanged();
						 //album_listview.setAdapter(adapter);
						 album_listview.setSelection(visibleLast - visibleCount+1);
						 album_listview.onRefreshComplete();
					} catch (Exception e) {
						e.printStackTrace();
					}
					 
					  
				 }
			 }else if(index==Loadmore_index){
				  
				  
				 if (album_con.size()==0) {
					 album_listview.onLoadMoreComplete(false);
					 Toast.makeText(context, "没有数据更新",Toast.LENGTH_LONG).show();
					 return;
				}else{
					 
					 
					
					adapter.notifyDataSetChanged();
					//album_listview.setAdapter(adapter);
					album_listview.setSelection(visibleLast - visibleCount+1);
					album_listview.onLoadMoreComplete(false);
				}
			 }
		}		 
		 
		

	 	 
		
	}
}
