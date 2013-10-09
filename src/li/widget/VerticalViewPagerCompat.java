package li.widget;

//public final class VerticalViewPagerCompat {
//	private VerticalViewPagerCompat() {
//	}
//
//	public interface DataSetObserver extends PagerAdapter.DataSetObserver {
//	}
//
//	public static void setDataSetObserver(PagerAdapter adapter,
//			DataSetObserver observer) {
//		adapter.setDataSetObserver(observer);
//	}
//}

import android.support.v4.view.PagerAdapter;

public final class VerticalViewPagerCompat {
	private VerticalViewPagerCompat() {
	}

	public interface DataSetObserver extends DataObserver {
	}
	private static DataSetObserver mObserver;
	public static void setDataSetObserver(PagerAdapter adapter,
			DataSetObserver observer) {
			setDataObserver(observer);
	}
	 interface  DataObserver {
		   public void onDataSetChanged();
		      }
	 static void setDataObserver(DataSetObserver observer) {
		        mObserver = observer;
		     }
	 public void notifyDataSetChanged() {
		         if (mObserver != null) {
		            mObserver.onDataSetChanged();
		         }
		     }
}
