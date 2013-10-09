package li.dream;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import li.adapter.album_gvAdapter;
import li.entity.Album;
import li.entity.album_pictures;
import li.utils.ConnWeb;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



public class albumItemActivity extends loginActivity  {
	
	private GridView album_gridView;
	private List<album_pictures> pictures_list;
	private List<album_pictures> pictures_con;
	private Integer start_Index=0;
	private LinearLayout linear_more;
	private ProgressBar pb;
	private TextView tv_more;
	private Bundle bundle;
	private Album album;
	private String pid,uid;
	private album_gvAdapter adapter;
	private Context context;
	private int visibleCount;
	private int visibleLast;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.album_item_gridview);	
		pictures_list=new ArrayList<album_pictures>();
		pictures_con=new ArrayList<album_pictures>( );
		context=this;
		init();
		linear_more.setVisibility(View.VISIBLE);
		bundle=this.getIntent().getExtras();
		//album=(Album)bundle.getSerializable("albums_item");
		//pid=album.getPid();
		pid=bundle.getString("pid");
		
		Loadmore(0);
		
		album_gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				   String url = pictures_list.get(position).getPicture_url();
				   
				   String[] dividepid=url.split("\\.");
				   String m_url=url+"_m."+dividepid[dividepid.length-1];
				   //System.out.print(m_url);
				   Log.v("albumitemUrl", m_url);
				   
				   Intent intent= new Intent();
				   Bundle bundle=new Bundle();
				   intent.setClass(albumItemActivity.this, album_m_photo.class);
				   bundle.putString("m_photo", m_url);
				   intent.putExtras(bundle);
				   startActivity(intent);
				   
				
			}
		});
		
		album_gridView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				 switch(scrollState){
			/*	 case OnScrollListener.SCROLL_STATE_FLING:
					 linear_more.setVisibility(View.GONE);
					 break;*/
				 case OnScrollListener.SCROLL_STATE_IDLE:
					 linear_more.setVisibility(View.VISIBLE);
					 break;
				 case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					 linear_more.setVisibility(View.GONE);
					 break;
				 }
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				 if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {    
					 	linear_more.setVisibility(View.VISIBLE);
		            }    
				 visibleCount = visibleItemCount;
				 visibleLast = firstVisibleItem + visibleItemCount - 1;
				
			}
		});
		tv_more.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 tv_more.setVisibility(View.GONE);
				 pb.setVisibility(View.VISIBLE);
				 start_Index+=1;//获取数据
				 Loadmore(start_Index);
				
			}
		});
	}
	
	public void init(){
		adapter=new album_gvAdapter(this,pictures_list);
		album_gridView=(GridView)findViewById(R.id.album_gridview);
		linear_more=(LinearLayout)findViewById(R.id.album_linear_more);
		pb=(ProgressBar)findViewById(R.id.album_gridview_progress);
		tv_more=(TextView)findViewById(R.id.album_gridview_more_text);
		album_gridView.setAdapter(adapter);
	}
 
	public void Loadmore(Integer index){
		new GridviewTask(context).execute(index);
	}
	public void clearLayou(){
		pb.setVisibility(View.GONE);
		tv_more.setVisibility(View.VISIBLE);
		//linear_more.setVisibility(View.GONE);
	}
	public class GridviewTask extends AsyncTask<Integer, Integer, Void>{
		
		private Context context;
		public GridviewTask(Context context){
			this.context=context;
		}
		
		@Override
		protected Void doInBackground(Integer... params) {
			try{
				pictures_con=new ConnWeb().getPictures(pid, uid, params[0]);
				//pictures_list.addAll(new ConnWeb().getPictures(pid, uid, params[0]));
				pictures_list.addAll(pictures_con);
			}catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		 
		@Override
		protected void onPreExecute() {
			 
			super.onPreExecute();
		}

		 
		@Override
		protected void onPostExecute(Void result) {
			 
			if(pictures_con.size()==0){
				clearLayou();
				Toast.makeText(context, "已加载全部", Toast.LENGTH_LONG).show();
				return;
			}else {
				clearLayou();
				if(start_Index==0){
					//album_gridView.setAdapter(adapter);
					adapter.notifyDataSetChanged();
				}else{
 
					adapter.notifyDataSetChanged();
					//album_gridView.setAdapter(adapter);
					album_gridView.setSelection(visibleLast - visibleCount + 1);
				}
			}
			
			super.onPostExecute(result);
		}
		
	}
}
