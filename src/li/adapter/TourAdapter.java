package li.adapter;

import java.util.List;

import li.bitmap.FinalBitmap;
import li.dream.R;
import li.dream.SetheadImageActivity;
import li.entity.Album;
import li.entity.Tour;
import li.viewholder.TourHolder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TourAdapter extends BaseAdapter{
	
	public LayoutInflater inflater;	
//	public List<Map<String,Object>> list;
	private List<Tour> tlist;
	public FinalBitmap fb;
	
	public TourAdapter(Context context,List<Tour> list)
	{
		this.inflater=LayoutInflater.from(context);
		this.tlist=list;
		fb = FinalBitmap.create(context);
		fb.configDiskCachePath(new SetheadImageActivity().getCache());
		fb.configLoadingImage(R.drawable.ic_launcher);
		fb.configLoadfailImage(R.drawable.ic_launcher);
		fb.configBitmapLoadThreadSize(5);
		fb.init();
	}

	@Override
	public int getCount() {
		if(tlist.size()!=0)
		{
			return tlist.size();
		}else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return tlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TourHolder holder =new TourHolder();
		if(convertView==null)
		{
			convertView=inflater.inflate(R.layout.tour_item, null);
			holder.covert=(ImageView)convertView.findViewById(R.id.tour_item_img);
			holder.name=(TextView)convertView.findViewById(R.id.tour_item_name);
			holder.money=(TextView)convertView.findViewById(R.id.tour_item_money);
			holder.days=(TextView)convertView.findViewById(R.id.tour_item_days);
			holder.username=(TextView)convertView.findViewById(R.id.tour_item_bossname);
			convertView.setTag(holder);//绑定ViewHolder对象
		}else {
			holder=(TourHolder)convertView.getTag();
		}
		
		fb.display(holder.covert,tlist.get(position).getCover());
		holder.name.setText(tlist.get(position).getName());
		holder.days.setText(tlist.get(position).getDays()+"天");
		holder.money.setText("￥"+tlist.get(position).getPrice());
		holder.username.setText(tlist.get(position).getUsername());
		
		
		return convertView;
	}
	
}
