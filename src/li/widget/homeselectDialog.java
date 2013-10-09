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
     * �������������Ĺ��캯�� 
     */  
	public homeselectDialog(Context context, int theme,home_selectListener listener) {
		super(context, theme);
		this.listener=listener;
	}
	public interface home_selectListener{
		/** 
         * �ص�������������Dialog�ļ����¼�������ˢ��Activity��UI��ʾ 
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

