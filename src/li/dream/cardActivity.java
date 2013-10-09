package li.dream;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class cardActivity extends loginActivity implements AnimationListener{

	private View card ,room;//������Ƭ�Ϳռ�����View
	private ListView room_listView; //�ռ��listview
	private Button slide; //������ť
	private String[] item_name;   //item������
	private List<HashMap<String, Object>> item_value =new ArrayList<HashMap<String,Object>>();
	boolean roomout=false;//�Ƿ��Ѿ������ռ�ҳ��
	
	AnimParams animParams = new AnimParams();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.card);
		
		init();
		room.setVisibility(View.INVISIBLE);
		slide.setOnClickListener(new ClickListener());
		room_listView.setOnItemClickListener(new OnItemClickListener() {

			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				switch (position) {
				case 0:
					show_dialog("΢˵");
					break;
				case 1:
					show_dialog("�μ�");
					break;
				case 2:
					Intent intent=new Intent(cardActivity.this,albumActivity.class);
					startActivity(intent);
					break;
				case 3:
					show_dialog("��æ");
					break;
				case 4:
					show_dialog("��Ը");
					break;
				case 5:
					show_dialog("��ע");
					break;
				case 6:
					show_dialog("��˿");
					break;
				case 7:
					show_dialog("��ע�ú���");
					break;
				}
				
			}
			
		});
		
		
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		cardActivity cardact= cardActivity.this;
		Animation anim;
		int w = card.getMeasuredWidth();
        int h = card.getMeasuredHeight();
        int left = (int) (card.getMeasuredWidth() * (- 0.6));
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if(roomout){
				anim = new TranslateAnimation(0, -left, 0, 0);
                animParams.init(0, 0, w, h);
                anim.setDuration(500);
                anim.setAnimationListener(cardact);

                 anim.setFillEnabled(true);
                
                card.startAnimation(anim);
                return true;
			}else if(!roomout){
				return super.onKeyDown(keyCode, event);
			}
		}
		 return false;
	}
	public void init()
	{
		card=(View)findViewById(R.id.card_view);
		room=(View)findViewById(R.id.room_view);
		room_listView=(ListView)findViewById(R.id.room_child);
		slide=(Button)findViewById(R.id.slide);
		
		item_name=this.getResources().getStringArray(R.array.self_center);
		
		 //������������Item�Ͷ�̬�����Ӧ��Ԫ��
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,getItemData(),//����Դ 
            R.layout.room_item,//ListItem��XMLʵ��
            new String[] {"ItemImage","ItemTitle", "ItemText"},//��̬������ImageItem��Ӧ������         
            new int[] {R.id.ItemImage,R.id.ItemTitle,R.id.ItemText}//ImageItem��XML�ļ������һ��ImageView,����TextView ID
        );
      
        room_listView.setAdapter(listItemAdapter);//��Ӳ�����ʾ
	}
	
	public List<HashMap<String, Object>> getItemData()
	{
		for(int i=0;i<item_name.length;i++)
		{
			HashMap<String, Object> map= new HashMap<String, Object>();
			map.put("ItemImage", R.drawable.ic_arrow);//ͼ����Դ��ID
        	map.put("ItemTitle", item_name[i]);
        	map.put("ItemText", "~");
			item_value.add(map);
		}
		return item_value;
	}
	
	@Override
	public void onAnimationStart(Animation animation) {
		System.out.println("onAnimationStart");
		
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		 roomout=!roomout;
		 if(!roomout)
		 {
			 room.setVisibility(View.INVISIBLE);
		 }
		layoutApp(roomout);
	}
	void layoutApp(boolean roomout)
	{
		card.layout(animParams.left, animParams.top, animParams.right, animParams.bottom);
	}
	
	@Override
	public void onAnimationRepeat(Animation animation) {
		System.out.println("onAnimationRepeat");
		
	}
	public class ClickListener implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			
			cardActivity cardact= cardActivity.this;
			Animation anim;
			int w = card.getMeasuredWidth();
            int h = card.getMeasuredHeight();
            int left = (int) (card.getMeasuredWidth() * (- 0.6));

            if (!roomout) {
                // anim = AnimationUtils.loadAnimation(context, R.anim.push_right_out_80);
                anim = new TranslateAnimation(0, left, 0, 0);
                room.setVisibility(View.VISIBLE);
                animParams.init(left, 0, left + w, h);
            } else {
                // anim = AnimationUtils.loadAnimation(context, R.anim.push_left_in_80);
                anim = new TranslateAnimation(0, -left, 0, 0);
                animParams.init(0, 0, w, h);
            }

            anim.setDuration(500);
            anim.setAnimationListener(cardact);

             anim.setFillEnabled(true);
            
            card.startAnimation(anim);
		}
		
	}
	 static class AnimParams {
	        int left, right, top, bottom;

	        void init(int left, int top, int right, int bottom) {
	            this.left = left;
	            this.top = top;
	            this.right = right;
	            this.bottom = bottom;
	        }
	    }

	

	
	
}
