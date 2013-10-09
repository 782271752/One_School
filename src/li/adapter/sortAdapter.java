package li.adapter;



import java.util.List;

import li.dream.R;
 
import li.viewholder.sortHolder;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class sortAdapter extends BaseAdapter{
	private Context context;
	private LayoutInflater inflater;
	 
	public int[] sort_image={
			R.drawable.hotel1, R.drawable.tour1,
			R.drawable.hotel1, R.drawable.tour1,
			R.drawable.hotel1, R.drawable.tour1,
			R.drawable.hotel1, R.drawable.tour1
	};
	
	public sortAdapter(Context context){
		this.context=context;
		this.inflater = LayoutInflater.from(context); 
	}

	@Override
	public int getCount() {
		return sort_image.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		sortHolder Holder = new sortHolder(); 
		Log.v("sortadapter", String.valueOf(position));
	        if (convertView == null) 
	        { 
	            convertView = inflater.inflate(R.layout.sort_gv_item,null);
	            Holder.image=(ImageView)convertView.findViewById(R.id.sort_gridview_image);
	            convertView.setTag(Holder.image); 
	        }else {
				Holder.image=(ImageView)convertView.getTag();
			}
	        Holder.image.setImageResource(sort_image[position]);
	        
	        return convertView;
	}

}
