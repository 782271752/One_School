package li.adapter;

import java.util.List;

import com.baidu.platform.comapi.map.o;

import li.bitmap.FinalBitmap;
import li.dream.R;
import li.dream.SetheadImageActivity;
import li.entity.Parttime;
import li.entity.Tour;
import li.viewholder.ParttimeHolder;
import li.viewholder.TourHolder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ParttimeAdapter extends BaseAdapter{
	public LayoutInflater inflater;	
//	public List<Map<String,Object>> list;
	private List<Parttime> plist;
	
	public ParttimeAdapter(Context context,List<Parttime> list)
	{
		this.inflater=LayoutInflater.from(context);
		this.plist=list;
	}
	
	@Override
	public int getCount() {
		if(plist.size()!=0)
		{
			return plist.size();
		}else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return plist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ParttimeHolder holder =new ParttimeHolder();
		if(convertView==null)
		{
			convertView=inflater.inflate(R.layout.part_time_job_item, null);
			holder.name=(TextView)convertView.findViewById(R.id.part_time_job_item_name);
			holder.num=(TextView)convertView.findViewById(R.id.part_time_job_item_num);
			holder.sex=(TextView)convertView.findViewById(R.id.part_time_job_item_sex);
			holder.price=(TextView)convertView.findViewById(R.id.part_time_job_item_money);
			holder.username=(TextView)convertView.findViewById(R.id.part_time_job_item_boss);
			holder.time=(TextView)convertView.findViewById(R.id.part_time_job_item_time);
			convertView.setTag(holder);//绑定ViewHolder对象
		}else {
			holder=(ParttimeHolder)convertView.getTag();
		}
		
		holder.name.setText(plist.get(position).getName());
		holder.num.setText(plist.get(position).getNum());
		
		if (plist.get(position).getSex().equals("0")) {
			holder.sex.setText("女");
		}else if(plist.get(position).getSex().equals("1"))
		{
			holder.sex.setText("男");
		}else {
			holder.sex.setText("不限");
		}
		
		
		holder.price.setText(plist.get(position).getReward());
		holder.username.setText(plist.get(position).getUsername());
		holder.time.setText(plist.get(position).getTime());
		
		
		return convertView;
	}

}
