package li.display;


import li.core.BitmapDisplayConfig;
import android.graphics.Bitmap;
import android.view.View;

public interface Displayer {

	/**
	 * 鍥剧墖鍔犺浇瀹屾垚 鍥炶皟鐨勫嚱鏁�
	 * @param imageView
	 * @param bitmap
	 * @param config
	 */
	public void loadCompletedisplay(View imageView,Bitmap bitmap,BitmapDisplayConfig config);
	
	/**
	 * 鍥剧墖鍔犺浇澶辫触鍥炶皟鐨勫嚱鏁�
	 * @param imageView
	 * @param bitmap
	 */
	public void loadFailDisplay(View imageView,Bitmap bitmap);
	
}
