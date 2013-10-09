package li.adapter;

import java.util.List;

import li.bitmap.FinalBitmap;
import li.dream.R;
import li.dream.SetheadImageActivity;
import li.entity.youji;
import li.viewholder.youjiHolder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class youjiAdapter extends BaseAdapter{
	public LayoutInflater inflater;	
	public List<youji> youjilist;
	public FinalBitmap fb;
	
	
	public youjiAdapter(Context context,List<youji> list){
		this.inflater=LayoutInflater.from(context);
		this.youjilist=list;
		
		fb = FinalBitmap.create(context);
		fb.configDiskCachePath(new SetheadImageActivity().getCache());
		fb.configLoadingImage(R.drawable.head);
		fb.configLoadfailImage(R.drawable.head);
		fb.configBitmapLoadThreadSize(5);
		fb.init();
	}

	@Override
	public int getCount() {
		if(youjilist.size()!=0){
			return youjilist.size();
		}else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		 
		return youjilist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		youjiHolder yjhHolder=new youjiHolder();
		Log.v("youjiAdapter",String.valueOf(position));
		
		if(convertView==null){
			convertView=inflater.inflate(R.layout.youji_item, null);
			
			yjhHolder.cover=(ImageView)convertView.findViewById(R.id.youji_item_covert);
			yjhHolder.name =(TextView)convertView.findViewById(R.id.youji_item_name);
			yjhHolder.time=(TextView)convertView.findViewById(R.id.youji_item_time);
			
			convertView.setTag(yjhHolder);
		}else {
			yjhHolder=(youjiHolder)convertView.getTag();
		}
		yjhHolder.name.setText(youjilist.get(position).getTitle());
		yjhHolder.time.setText(youjilist.get(position).getTime());
		fb.display(yjhHolder.cover,youjilist.get(position).getCover());
		
		 
		return convertView;
	}
}
