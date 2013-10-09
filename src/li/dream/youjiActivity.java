package li.dream;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import li.adapter.youjiAdapter;
import li.entity.youji;
import li.utils.ConnWeb;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import li.widget.DragListView;


public class youjiActivity extends loginActivity implements DragListView.OnRefreshLoadingMoreListener{
	
	private DragListView youji_listview;
	private List<youji> youji_list;
	private int visibleCount;
	private int visibleLast;
	private final int Drag_index=1;
	private final int Loadmore_index=2;
	private youjiAdapter adapter;
	private Context context;
	private boolean firstload=true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.youji);
		context=this;
		youji_list=new ArrayList<youji>();
		init();
		youji_listview.setOnRefreshListener(this);
		onRefresh();
		
		youji_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				 youji yj=youji_list.get(position-1);
				 Log.v("youjiActivity", yj.toString());
				 Intent intent=new Intent();
				 Bundle bundle=new Bundle();
				 intent.setClass(context, youjidetailActivity.class);
				 bundle.putSerializable("youji", (Serializable)yj);
				 intent.putExtras(bundle);
				 startActivity(intent);
				
			}
		});
		
		youji_listview.setOnScrollListener(new OnScrollListener() {
			
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
	public void init(){
		adapter=new youjiAdapter(context, youji_list);
		youji_listview=(DragListView)findViewById(R.id.youji_list);
		youji_listview.setAdapter(adapter);
	}
	@Override
	public void onRefresh() {
		 youji_list.clear();
		 new youjiTask(context, Drag_index).execute();     //输入下拉参数，暂时设为空
		
	}

	@Override
	public void onLoadMore() {
		 
		new youjiTask(context, Loadmore_index).execute();//输入加载更多参数，暂时设为空
	}
	
	
	public class youjiTask extends AsyncTask<Integer, Integer, Void>{

		private Context context;
		private int index;	// 用于判断是下拉刷新还是点击加载更多
		
		public youjiTask(Context context,int index){
			this.context=context;
			this.index=index;
			
		}
		
		
		@Override
		protected Void doInBackground(Integer... params) {
			if(firstload){
				publishProgress(30);
				
			}
			try {
				youji_list.addAll(new ConnWeb().getyouji());      //得到游记后续补全
			} catch (Exception e) {
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
				if(youji_list.size()==0){
					youji_listview.onRefreshComplete();
					Toast.makeText(context, "没有数据更新",Toast.LENGTH_LONG).show();
					return;
				}else {
					try {
						adapter.notifyDataSetChanged();
						youji_listview.setSelection(visibleLast - visibleCount+1);
						youji_listview.onRefreshComplete();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}else if(index==Loadmore_index){
				if(youji_list.size()==0){
					youji_listview.onLoadMoreComplete(false);
					Toast.makeText(context, "没有数据更新",Toast.LENGTH_LONG).show();
					return;
				}else {
					adapter.notifyDataSetChanged();
					youji_listview.setSelection(visibleLast - visibleCount+1);
					youji_listview.onLoadMoreComplete(false);
				}
			}
		}

		 
		@Override
		protected void onProgressUpdate(Integer... values) {
			if(values[0]==30){
				show_dialog("数据加载中...");
			}else {
				cancel_dialog();
			}
		}

		 
	}
		
}
