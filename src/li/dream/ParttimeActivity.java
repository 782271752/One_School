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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import li.adapter.ParttimeAdapter;
import li.adapter.TourAdapter;
import li.dream.tourActivity.TourAsync;
import li.entity.Parttime;
import li.entity.Tour;
import li.utils.ConnWeb;
import li.widget.CommentListView;
public class ParttimeActivity extends loginActivity implements CommentListView.OnRefreshLoadingMoreListener,OnClickListener{

	private CommentListView parttime_ListView;
	private List<Parttime> parttime_list;
	private List<Parttime> parttime_con;
	private final int Drag_index=1;
	private final int Loadmore_index=2;
	private Integer Start_Index=0;
	private Bundle bundle;
	private int visibleCount;
	private int visibleLast;
	private ParttimeAdapter padapter;
	private boolean firstload=true;
	private Context context;
	private ImageView back;
	private String schoolid="560";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.part_time_job);
		parttime_list=new ArrayList<Parttime>();
		parttime_con=new ArrayList<Parttime>();
		context=this;
		init();
		parttime_ListView.setOnRefreshListener(this);
		onRefresh();
		
		
		parttime_ListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					Parttime parttime_detail=parttime_list.get(position);
					Log.d("parttime", "---------------"+position+"----------"+ parttime_detail.toString()+"");
					Intent it = new Intent();//创建Intent对象
					Bundle bundle = new Bundle();//创建Bundle对象
					it.setClass(ParttimeActivity.this, ParttimeDetailActivity.class);
					bundle.putSerializable("parttime_details",(Serializable) parttime_detail);
					Log.d("parttime_detail", parttime_detail.toString());
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
		padapter=new ParttimeAdapter(this, parttime_list);
		parttime_ListView=(CommentListView)findViewById(R.id.part_time_job_list);
		parttime_ListView.setAdapter(padapter);
		back=(ImageView)findViewById(R.id.part_time_job_back);
	}
	
	public class ParttimeAsync extends AsyncTask<Integer, Integer, Void>
	{
		
		private Context context;
		private int index;	// 用于判断是下拉刷新还是点击加载更多

		public ParttimeAsync(Context context,int index){
			this.context = context;
			this.index = index;
		}
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Void doInBackground(Integer... params) {
			if(firstload){
				publishProgress(30);
				
			}
			 
			//----------------------------修改school id----------------------------------------------------
			try{				 	
				parttime_con= new ConnWeb().getParttimes("url", schoolid, params[0]); //---------------------------------
				parttime_list.addAll(parttime_con);
			}catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Void result) {
			if(firstload){ 
				publishProgress(100);
				firstload=false;
			}
			 if(index==Drag_index){
//				 adapter=new albumAdapter(albumActivity.this,getAlbum_Data());
				 if(parttime_list.size()==0){
					// parttime_ListView.onRefreshComplete();
					 Toast.makeText(context, "已加载全部数据",Toast.LENGTH_LONG).show();
					 return;
				 }else{		
					 try {
						 
						 
						 padapter.notifyDataSetChanged();
						 parttime_ListView.setSelection(visibleLast - visibleCount+1);
						// parttime_ListView.onRefreshComplete();
					} catch (Exception e) {
						e.printStackTrace();
					}
					 
					  
				 }
			 }else if(index==Loadmore_index){
				  
				  
				 if (parttime_con.size()==0) {
					 parttime_ListView.onLoadMoreComplete(false);
					 Toast.makeText(context, "没有数据更新",Toast.LENGTH_LONG).show();
					 return;
				}else{
					 
					 
					
					padapter.notifyDataSetChanged();
					//album_listview.setAdapter(adapter);
					parttime_ListView.setSelection(visibleLast - visibleCount+1);
					parttime_ListView.onLoadMoreComplete(false);
				}
			 }
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onProgressUpdate(Progress[])
		 */
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
	@Override
	public void onRefresh() {
		Start_Index=0;
		parttime_list.clear();
		new ParttimeAsync(context, Drag_index).execute(Start_Index);
		
	}

	@Override
	public void onLoadMore() {
		Start_Index=Start_Index+10;
		new ParttimeAsync(context, Loadmore_index).execute(Start_Index);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.part_time_job_back:
			finish();
			break;

		default:
			break;
		}
		
	}

}
