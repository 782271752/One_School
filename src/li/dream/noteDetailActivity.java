package li.dream;

import java.net.URL;



import li.bitmap.FinalBitmap;
import li.entity.note;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class noteDetailActivity extends loginActivity implements OnClickListener{

	private ImageView head,pic;
	private TextView name,text,comment_count,like_count,share_count,likeadd;
	private String id,uid;
	private String head_url,pic_url,m_pic_url;
	private FinalBitmap fb;
	private LinearLayout pic_layout;
	private Button comment,like;
	private Animation animation;
	private note note_detail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_detail);
		init();
		fb = FinalBitmap.create(this);
		fb.configDiskCachePath(new SetheadImageActivity().getCache());
		fb.configLoadingImage(R.drawable.two);
		fb.configLoadfailImage(R.drawable.two);
		fb.configBitmapLoadThreadSize(4);
		fb.init();
		getnote();
		animation=AnimationUtils.loadAnimation(noteDetailActivity.this,R.anim.nn);
		like.setOnClickListener(this);
		//like_count.setOnClickListener(this);
		pic.setOnClickListener(this);
	}
	
	public void init(){
		head=(ImageView)findViewById(R.id.note_detail_user_pic);
		name=(TextView)findViewById(R.id.note_detail_user_name);
		text=(TextView)findViewById(R.id.note_detail_content);
		pic=(ImageView)findViewById(R.id.note_detail_pic);
		comment_count=(TextView)findViewById(R.id.note_detail_comment_count);
		like_count=(TextView)findViewById(R.id.note_detail_like_count);
		share_count=(TextView)findViewById(R.id.note_detail_share_count);
		pic_layout=(LinearLayout)findViewById(R.id.note_detail_pic_layout);
	//	comment=(Button)findViewById(R.id.note_detail_comment);
		like=(Button)findViewById(R.id.note_detail_like);
		
		likeadd=(TextView)findViewById(R.id.tv_one);
	}
	public void getnote(){
		
		Bundle bundle = this.getIntent().getExtras();//获取Bundle
		note_detail=(note)bundle.getSerializable("details");//获取Bundle中的对象
		id=note_detail.getId();  //获取id
		uid=note_detail.getUid();//获取uid
		head_url=note_detail.getHead_url();
		fb.display(head, head_url);
		name.setText(note_detail.getName());
		text.setText(note_detail.getContext());
		share_count.setText(note_detail.getShare_count());
		comment_count.setText(note_detail.getComment_count());
		like_count.setText(note_detail.getLike_count());
		
		pic_url=note_detail.getNote_pic();
		if(!pic_url.equals("")){
			try {
				//URL picUrl = new URL(pic_url);
				//Bitmap pngBM = BitmapFactory.decodeStream(picUrl.openStream());
				//pic.setImageBitmap(pngBM);
				fb.display(pic, pic_url);
				pic_layout.setVisibility(View.VISIBLE);		
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else {
			 pic_layout.setVisibility(View.GONE);
		}
		
		
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.note_detail_like){ //点击喜欢按钮
			likeadd.setVisibility(View.VISIBLE);
			likeadd.startAnimation(animation);
			new Handler().postDelayed(new Runnable(){
	            public void run() {
	            	likeadd.setVisibility(View.GONE);
	            } 
			}, 1000);
			like.setEnabled(false);
			
			like.setText("已赞过");
			int count=Integer.parseInt(note_detail.getLike_count())+1;
			String string=String.valueOf(count);
			like_count.setText(string);
		}
		
		if(v.getId()==R.id.note_detail_pic){//点击图片按钮
			
			//-----------------------------------加载中图的代码--------------------------------------//
			String[] name;
			name=pic_url.split("\\.");
			
			
			String[] dividepid;//分隔
			dividepid=pic_url.split("\\_150x150");
			m_pic_url=dividepid[0]+"_m."+name[name.length-1];
			
			Intent intent=new Intent(noteDetailActivity.this, middlepicActivity.class);
			intent.putExtra("m_pic_url", m_pic_url);
			startActivity(intent);
				
			 		
		}
	}
}
