package li.dream;

 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



import com.tencent.mm.sdk.platformtools.Log;

import li.adapter.NoteAdapter;
import li.adapter.albumAdapter;
import li.adapter.youjiAdapter;
import li.entity.Album;
import li.entity.note;
import li.entity.youji;
import li.utils.ConnWeb;
import li.widget.DragListView;
import li.widget.MyAnimation;
import li.widget.UgcAnimations;
import li.widget.homeselectDialog;
 
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class homeActivity extends loginActivity implements DragListView.OnRefreshLoadingMoreListener{

	private enum Select_state{
		weishuo_state,help_state,wish_state 
	}
	private Select_state select_state=null;  //当前选中状态
	
	private DragListView home_listview;
	
	private List<note> note_list;  //微说
	private List<note> help_list;  //帮忙
	private List<note> wish_list;  //许愿
	
	
	private NoteAdapter noteadapter;		/********************************************************/
	private NoteAdapter helpadapter;
	private NoteAdapter wishAdapter;
	
	private String note_url,help_url,wish_url;
	private String school_id="560";
	 
	private Button title;
	//private ImageButton home_refresh;
	private String select_what;
	private Context context;
	
	 
	private int visibleCount;
	private int visibleLast;
	private final int Drag_index=1;
	private final int Loadmore_index=2;
	private boolean firstload=true;
	private Integer Start_Index=0;
	private RelativeLayout home_write_layout;
	private ImageView home_write;
	private ImageView weishuo_write;
	private ImageView wish_write;

	ImageView help_write;
	private RelativeLayout level2;	
	private boolean isLevel2Show;

	private String type;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_page);
		note_list=new ArrayList<note>();
		help_list=new ArrayList<note>(); /*************************************************************/
		wish_list=new ArrayList<note>();
		init();
		context=this;
		home_listview.setOnRefreshListener(this); 
		note_url="http://121.199.40.201/m/echo/note.php?from=";
		MyAnimation.startAnimationOUT(level2, 1200, 0);
		isLevel2Show=false;
		
		
		home_write.setOnClickListener(new OnClickListener() {   //点击发布按钮
			
			@Override
			public void onClick(View v) {
				if(!isLevel2Show){
					 
					//显示2级导航菜单
					MyAnimation.startAnimationIN(level2, 500);
				} else {			
					 
					//隐藏2级导航菜单
					MyAnimation.startAnimationOUT(level2, 500, 0);
				}
				isLevel2Show = !isLevel2Show;
				
			}
		});
		weishuo_write.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Animation anim = UgcAnimations.clickAnimation(500);
				anim.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						 Intent intent=new Intent(homeActivity.this,writeActivity.class);
						 intent.putExtra("key", "wei_shuo");
						 startActivity(intent);
						  
					}
				});
				weishuo_write.startAnimation(anim);
			}
		});
		
		help_write.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Animation animation=UgcAnimations.clickAnimation(500);
				 animation.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						Intent intent=new Intent(homeActivity.this,writeActivity.class);
						 intent.putExtra("key", "help");
						 startActivity(intent);
						  
						
					}
				});
				help_write.startAnimation(animation);
			}
		});
		
		wish_write.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Animation animation=UgcAnimations.clickAnimation(500);
				 animation.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						Intent intent=new Intent(homeActivity.this,writeActivity.class);
						 intent.putExtra("key", "wish");
						 startActivity(intent);
						  
						
					}
				});
				wish_write.startAnimation(animation);
			}
		});
		
		
		
		
		
		
		select_what="help";
		onRefresh();
		select_state=Select_state.help_state;
		
		
		
		
		title.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			 homeselectDialog selece_Dialog=new homeselectDialog(context,R.style.dialog, new homeselectDialog.home_selectListener() {
				
				@Override
				public void Select_what(int type) {
					switch (type) {
					case 0:
						select_what="weishuo";
 
						noteadapter=new NoteAdapter(context, note_list);           ///////////////////////////////////////
						home_listview.setAdapter(noteadapter);							/////////////////////////////////////
						title.setText("吐槽");
						firstload=true;
						onRefresh();
						select_state=Select_state.weishuo_state;
						break;
					case 1:
						select_what="help";
 
						helpadapter=new NoteAdapter(context, help_list);           ///////////////////////////////////////
						home_listview.setAdapter(helpadapter);	
						title.setText("帮忙");
						firstload=true;
						onRefresh();
						select_state=Select_state.help_state;
						break;
					case 2:
						select_what="wish";
 
						wishAdapter=new NoteAdapter(context, wish_list);           ///////////////////////////////////////
						home_listview.setAdapter(wishAdapter);	
						title.setText("许愿");
						firstload=true;
						onRefresh();
						select_state=Select_state.wish_state;
						break;
					default:
						break;
					}
					
				}
			});
			 selece_Dialog.setCanceledOnTouchOutside(true);// 点击Dialog之外的区域对话框消失
			  
			 Window window=selece_Dialog.getWindow();
			 WindowManager.LayoutParams lp=  new LayoutParams();
			 
			 lp.dimAmount=0f;
			// lp.alpha=0.6f;
			 lp.x=(getWindowManager().getDefaultDisplay().getWidth())/3;
			 lp.y=-(getWindowManager().getDefaultDisplay().getHeight())/10;
			 
			 window.setAttributes(lp);		
			// window.setGravity(Gravity.TOP);
			 selece_Dialog.show();
			}
		});
		home_listview.setOnScrollListener(new OnScrollListener() {
			
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
		
		home_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(select_state==Select_state.weishuo_state){
					try {
						note note_detail=note_list.get(position-1);// 获取当前列表项选中的
						Log.d("note", "---------------"+position+"----------"+ note_detail.toString()+"");
						Intent it = new Intent();//创建Intent对象
						Bundle bundle = new Bundle();//创建Bundle对象
						it.setClass(homeActivity.this, noteDetailActivity.class);
						bundle.putSerializable("details",(Serializable) note_detail);
						Log.d("note_detail", note_detail.toString());
						it.putExtras(bundle);
						startActivity(it);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}else if(select_state==Select_state.help_state){
					 try {
						note help_detail=help_list.get(position-1);// 获取当前列表项选中的
						Log.d("note", "---------------"+position+"----------"+ help_detail.toString()+"");
						Intent it = new Intent();//创建Intent对象
						Bundle bundle = new Bundle();//创建Bundle对象
						it.setClass(homeActivity.this, noteDetailActivity.class);
						bundle.putSerializable("details",(Serializable) help_detail);
						Log.d("note_detail", help_detail.toString());
						it.putExtras(bundle);
						startActivity(it);
					} catch (Exception e) {
						e.printStackTrace();
					}
						
					
				}else if(select_state==Select_state.wish_state){
					
					try {
						note wish_detail=wish_list.get(position-1);// 获取当前列表项选中的
						Log.d("note", "---------------"+position+"----------"+ wish_detail.toString()+"");
						Intent it = new Intent();//创建Intent对象
						Bundle bundle = new Bundle();//创建Bundle对象
						it.setClass(homeActivity.this, noteDetailActivity.class);
						bundle.putSerializable("details",(Serializable) wish_detail);
						Log.d("note_detail", wish_detail.toString());
						it.putExtras(bundle);
						startActivity(it);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				
				
				
			}
		});
	}
	public void init()
	{
		title=(Button)findViewById(R.id.home_chose);
	//	home_refresh=(ImageButton)findViewById(R.id.home_refresh);
		helpadapter=new NoteAdapter(this, help_list);      //////////////微说适配器////////////////
		home_listview=(DragListView)findViewById(R.id.home_list);
		home_listview.setAdapter(helpadapter);                   ////////加载适配器//////////////////////
		
		home_write_layout=(RelativeLayout)findViewById(R.id.home_write_layout);
		home_write=(ImageView)findViewById(R.id.home_write);
		help_write=(ImageView)findViewById(R.id.home_help_write);
		wish_write=(ImageView)findViewById(R.id.home_wish_write);
		weishuo_write=(ImageView)findViewById(R.id.home_weishuo_write);
		level2=(RelativeLayout)findViewById(R.id.level2);
	}
	
	 
	
	public void ClearList(){
		if(select_state==Select_state.weishuo_state){
			note_list.clear();
		}else if(select_state==Select_state.help_state){
			help_list.clear();
		}else if(select_state==Select_state.wish_state){
			wish_list.clear();
		}
	}
	
	@Override
	public void onRefresh() {
		ClearList();
		Start_Index=0;
		new homeTask(context, select_what, Drag_index).execute(Start_Index);
		
	}
	 
	@Override
	public void onLoadMore() {
		 Start_Index+=10;
		 new homeTask(context, select_what, Loadmore_index).execute(Start_Index);
		
	}
	public class homeTask extends AsyncTask<Integer, Integer, Void>{

		private Context context;
		private int index;	// 用于判断是下拉刷新还是点击加载更多
		private String string;//用来判断是 那个内容
		
		public homeTask(Context context,String string,int index){
			this.context=context;
			this.string=string;
			this.index=index;
		}
		
		 
		
		@Override
		protected Void doInBackground(Integer... params) {
			if(firstload){
				publishProgress(30);
				
			}
			if(string.equals("weishuo")){
				type="note";
				note_list.addAll(new ConnWeb().getNote(note_url, school_id, type,Start_Index));    //获取微说数据
				Log.v("note", Start_Index.toString());
				
			}else if(string.equals("help"))
			{	type="aid";
				help_list.addAll(new ConnWeb().getNote(note_url, school_id, type,Start_Index));
			}else if(string.equals("wish")){
				type="vow";
				wish_list.addAll(new ConnWeb().getNote(note_url, school_id, type,Start_Index));
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
				if(string.equals("weishuo")){
					 if(note_list.size()==0){
						 home_listview.onRefreshComplete();
						 Toast.makeText(context, "没有数据更新",Toast.LENGTH_LONG).show();
						 return;
					 }else{		
						 //Start_Index=album_list.size();
						 try {
							 
							 
							 noteadapter.notifyDataSetChanged();
							 //album_listview.setAdapter(adapter);
							 home_listview.setSelection(visibleLast - visibleCount+1);
							 home_listview.onRefreshComplete();
						} catch (Exception e) {
							e.printStackTrace();
						}
						 
						  
					 }					
				}else if(string.equals("help"))
				{
					if(help_list.size()==0){
						 home_listview.onRefreshComplete();
						 Toast.makeText(context, "没有数据更新",Toast.LENGTH_LONG).show();
						 return;
					 }else{		
						 //Start_Index=album_list.size();
						 try {
							 helpadapter.notifyDataSetChanged();
							 //album_listview.setAdapter(adapter);
							 home_listview.setSelection(visibleLast - visibleCount+1);
							 home_listview.onRefreshComplete();
						} catch (Exception e) {
							e.printStackTrace();
						}
						 
						  
					 }
				}else if(string.equals("wish")){
					if(wish_list.size()==0){
						 home_listview.onRefreshComplete();
						 Toast.makeText(context, "没有数据更新",Toast.LENGTH_LONG).show();
						 return;
					 }else{		
						 //Start_Index=album_list.size();
						 try {
							 
							 
							 wishAdapter.notifyDataSetChanged();
							 //album_listview.setAdapter(adapter);
							 home_listview.setSelection(visibleLast - visibleCount+1);
							 home_listview.onRefreshComplete();
						} catch (Exception e) {
							e.printStackTrace();
						}
						 
						  
					 }
					
					 
				}
			}else if(index==Loadmore_index){
				if(string.equals("weishuo")){
					 if (note_list.size()==0) {
						 home_listview.onLoadMoreComplete(false);
						 Toast.makeText(context, "没有数据更新",Toast.LENGTH_LONG).show();
						 return;
					}else{
						noteadapter.notifyDataSetChanged();
						//album_listview.setAdapter(adapter);
						home_listview.setSelection(visibleLast - visibleCount+1);
						home_listview.onLoadMoreComplete(false);
					}
					
				}else if(string.equals("help"))
				{
					if (help_list.size()==0) {
						 home_listview.onLoadMoreComplete(false);
						 Toast.makeText(context, "没有数据更新",Toast.LENGTH_LONG).show();
						 return;
					}else{
						helpadapter.notifyDataSetChanged();
						//album_listview.setAdapter(adapter);
						home_listview.setSelection(visibleLast - visibleCount+1);
						home_listview.onLoadMoreComplete(false);
					}
				}else if(string.equals("wish")){
					
					if (wish_list.size()==0) {
						 home_listview.onLoadMoreComplete(false);
						 Toast.makeText(context, "没有数据更新",Toast.LENGTH_LONG).show();
						 return;
					}else{
						wishAdapter.notifyDataSetChanged();
						//album_listview.setAdapter(adapter);
						home_listview.setSelection(visibleLast - visibleCount+1);
						home_listview.onLoadMoreComplete(false);
					}
				} 
			}
		}
		
		 
		 
		@Override
		protected void onProgressUpdate(Integer... values) {
			if(values[0]==30)
			{
				show_dialog("加载数据,请稍等...");
			}
			else
			{
				cancel_dialog();
			}
		}
		
	}
}
