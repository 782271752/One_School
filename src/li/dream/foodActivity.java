package li.dream;

import android.os.Bundle;
import li.widget.DragListView;
public class foodActivity extends loginActivity implements DragListView.OnRefreshLoadingMoreListener{

	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.food);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}

}
