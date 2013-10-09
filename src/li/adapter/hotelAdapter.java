package li.adapter;

import java.util.List;

import li.bitmap.FinalBitmap;
import li.dream.R;
import li.dream.SetheadImageActivity;
import li.entity.hotel;
import li.viewholder.hotelHolder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class hotelAdapter extends BaseAdapter{

	public LayoutInflater inflater;
	public List<hotel> hotel_list;
	public FinalBitmap fb;
	 public hotelAdapter(Context context,List<hotel> list){
		 this.inflater=LayoutInflater.from(context);
		 this.hotel_list=list;
		 
		 fb=FinalBitmap.create(context);
		 fb.configDiskCachePath(new SetheadImageActivity().getCache());
		 fb.configLoadingImage(R.drawable.head);
		 fb.configLoadfailImage(R.drawable.head);
		 fb.configBitmapLoadThreadSize(5);
		 fb.init();
	 }

	@Override
	public  int getCount() {
		if(hotel_list.size()!=0){
			return hotel_list.size();
		}else {
			return 0;
		}
		 
	}

	@Override
	public Object getItem(int position) {
		 
		return hotel_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		 
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		hotelHolder holder=new hotelHolder();
		Log.v("holder", String.valueOf(position));
		
		if(convertView==null){
			convertView=inflater.inflate(R.layout.hotel_item, null);
			holder.name=(TextView)convertView.findViewById(R.id.hotel_item_message);
			holder.hote_cover=(ImageView)convertView.findViewById(R.id.hotel_item_img);
			holder.hotelname=(TextView)convertView.findViewById(R.id.hotel_item_hotelname);
		//	holder.time=(TextView)convertView.findViewById(R.id.hotel_item_time);
			holder.money=(TextView)convertView.findViewById(R.id.hotel_item_money);
			holder.where=(TextView)convertView.findViewById(R.id.hotel_item_where);
			
			convertView.setTag(holder);
		}else {
			holder=(hotelHolder)convertView.getTag();
		}
		
		fb.display(holder.hote_cover, hotel_list.get(position).getCovert());
		holder.name.setText(hotel_list.get(position).getName());
		holder.hotelname.setText(hotel_list.get(position).getHotelname());
//		holder.time.setText(hotel_list.get(position).getTime());
		holder.money.setText(hotel_list.get(position).getMoney());
		holder.where.setText(hotel_list.get(position).getWhere());
		
		return convertView;
	}
}
