package li.widget;

 

import li.dream.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class homeselectDialog extends Dialog{

	 /** 
     * 带监听器参数的构造函数 
     */  
	public homeselectDialog(Context context, int theme,home_selectListener listener) {
		super(context, theme);
		this.listener=listener;
	}
	public interface home_selectListener{
		/** 
         * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示 
         */  
		public void Select_what(int type);
	}
	
	public home_selectListener listener;
	
	 
	 
	Button weishuo,help,wish;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_select_item);
		init();
		
		weishuo.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 dismiss();
				 listener.Select_what(0);
				
			}
		});
		help.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
				listener.Select_what(1);
				
			}
		});
		wish.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
				listener.Select_what(2);
				
			}
		});
	 
		
		
	}
	public void init(){
		weishuo=(Button)findViewById(R.id.home_select_weishuo);
		help=(Button)findViewById(R.id.home_select_help);
		wish=(Button)findViewById(R.id.home_select_wish);
		 
	}
	

	 
	
}

