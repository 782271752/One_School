package li.adapter;

import java.util.List;
import java.util.Map;

import li.bitmap.FinalBitmap;
import li.dream.R;
import li.dream.SetheadImageActivity;
import li.entity.Album;
import li.viewholder.albumHolder;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class albumAdapter extends BaseAdapter{

	public LayoutInflater inflater;	
//	public List<Map<String,Object>> list;
	private List<Album> alist;
	public FinalBitmap fb;
	//public int i;
	 
 
	public albumAdapter(Context context, List<Album> list){
		this.inflater=LayoutInflater.from(context);
		this.alist=list;
		fb = FinalBitmap.create(context);
		fb.configDiskCachePath(new SetheadImageActivity().getCache());
		fb.configLoadingImage(R.drawable.head);
		fb.configLoadfailImage(R.drawable.head);
		fb.configBitmapLoadThreadSize(5);
		fb.init();
	}
	@Override
	public int getCount() {
 
		if(alist.size()!=0)
		{
			return alist.size();
		}else{
			return 0;
		}
	}

	 
	@Override
	public Album getItem(int position) {
//		 return list.get(position);
		 return alist.get(position);
	}

	 
	@Override
	public long getItemId(int position) {
		 
		return position;
	}

	 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		albumHolder holder=new albumHolder();;
		Log.v("ada_position", String.valueOf(position)); 
//		Log.v("ada_pid", String.valueOf(list.get(position).get("pid")));
		if(convertView==null){
			convertView=inflater.inflate(R.layout.album_item, null);
			 
			holder.covert=(ImageView)convertView.findViewById(R.id.album_item_covert);
			holder.name=(TextView)convertView.findViewById(R.id.album_item_name);
			holder.time=(TextView)convertView.findViewById(R.id.album_item_time);
			holder.counts=(TextView)convertView.findViewById(R.id.album_item_count);
			
			holder.covert.setScaleType(ScaleType.CENTER_CROP);
			
			convertView.setTag(holder);//绑定ViewHolder对象                   
		}else{
			holder=(albumHolder)convertView.getTag();//取出ViewHolder对象
		}
		 
		 
		
		holder.name.setText(alist.get(position).getName());
		
		holder.counts.setText(alist.get(position).getCounts());
		holder.time.setText(alist.get(position).getDate());
		
		fb.display(holder.covert, alist.get(position).getCovert());
		
		return convertView;
	}

}
