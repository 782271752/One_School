package li.dream;

import java.util.ArrayList;



import android.R.attr;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
 
import android.support.v4.view.ViewPager;
 
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import li.adapter.sortAdapter;
import li.widget.MyImageView;
import li.widget.MyTurnplateView;
import li.widget.MyTurnplateView.OnTurnplateListener;
import li.widget.MyTurnplateView.Point;
import li.widget.RotatView;

public class sortActivity extends loginActivity implements OnTurnplateListener{

//	public ViewPager viewPager;
//	public ImageView page00,page11;
	public int current_Index=0;
//	MyImageView food,hotel;
	MyTurnplateView fenlei;
 
	private GridView gridview;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		context=this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fenlei);
		fenlei=(MyTurnplateView)findViewById(R.id.fenlei_view);
		fenlei.setOnTurnplateListener(this);
		
	/*   ----------------------------------------自定义view-------------------------------------------
	 	int height = getWindowManager().getDefaultDisplay().getHeight();
		int width = getWindowManager().getDefaultDisplay().getWidth();
		getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.orange_all));
		MyTurnplateView myView = new MyTurnplateView(this, width/2, height/2, width/3+30);
		myView.setOnTurnplateListener(this);
		setContentView(myView);
	*/
		
	}
	@Override
	public void onPointTouch(Point point) {
		 
		int flag=point.flag;
		Intent intent;
		switch (flag) {
		
		case 0:
			intent=new Intent();
			intent.setClass(this, foodActivity.class);
			startActivity(intent);
			break;
			
		case 1:
			intent=new Intent(this,hotelActivity.class);
			startActivity(intent);
			break;
		
		case 2:
			intent=new Intent(this,tourActivity.class);
			startActivity(intent);
			break;
		case 3:
			intent=new Intent(this,ParttimeActivity.class);
			startActivity(intent);
			break;
		default:
			Toast.makeText(this, ""+flag+"", Toast.LENGTH_SHORT).show();
			break;
		}
		
		
	}
		
		 
		
	
	
}

























































/*	gridview.setAdapter(new sortAdapter(context));


gridview.setOnItemClickListener(new OnItemClickListener() {

	 
	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
		Log.v("sortActivity", String.valueOf(position));
		// Toast.makeText(context, "第"+position+"个", Toast.LENGTH_LONG).show();
		switch (position) {
		case 0:	
			break;
		case 1:	
			break;
		case 2:	
			break;
		case 3:	
			break;
		case 4:	
			break;
		case 5:	
			break;
		case 6:	
			break;
		case 7:	
			break;
		
		 
		}
		
	}
	
});



}
public void init(){
gridview = (GridView) findViewById(R.id.sort_gridview);
}*/
