package li.base;

import li.dream.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class baseActivity extends Activity{

	protected final int OPTIONS_MENU_ID_SETTINGS =1;//设置
	protected final int OPTIONS_MENU_ID_LOGOUT =2;//注销
	protected final int OPTIONS_MENU_ID_ABOUT =3;//关于
	protected final int OPTIONS_MENU_ID_EXIT=4;//退出
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		
		MenuItem Item;
		
		Item=menu.add(0,OPTIONS_MENU_ID_SETTINGS,0,"设置");
		Item.setIcon(android.R.drawable.ic_menu_preferences);
		
		Item=menu.add(0,OPTIONS_MENU_ID_LOGOUT,0,"注销");
		Item.setIcon(android.R.drawable.ic_menu_revert);
		
		Item=menu.add(0,OPTIONS_MENU_ID_ABOUT,0,"关于");
		Item.setIcon(android.R.drawable.ic_menu_preferences);
		
		Item=menu.add(0,OPTIONS_MENU_ID_EXIT,0,"退出");
		Item.setIcon(R.drawable.menu_exit);
		
		return true;
	}


	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		
		switch(item.getItemId()){
		
		case OPTIONS_MENU_ID_SETTINGS:
			break;
		case OPTIONS_MENU_ID_LOGOUT:
			Logout();
			break;
		case OPTIONS_MENU_ID_ABOUT:
			break;
		case OPTIONS_MENU_ID_EXIT:
			Exit();
			break;
		
		
		}
		
		return true;
	}
	
	public void Logout()
	{
		
	}
	
	public void Exit()
	{
		
	}
	
}
