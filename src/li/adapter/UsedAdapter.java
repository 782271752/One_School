package li.adapter;

import java.util.List;

import li.dream.R;
import li.entity.Parttime;
import li.entity.Used;
import li.viewholder.ParttimeHolder;
import li.viewholder.UsedHolder;
import android.content.Context;
import android.graphics.Region.Op;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UsedAdapter extends BaseAdapter{
	
	public LayoutInflater inflater;	
	private List<Used> ulist;
	
	public UsedAdapter(Context context,List<Used> list){
		this.inflater=LayoutInflater.from(context);
		this.ulist=list;
	}
	@Override
	public int getCount() {
		if(ulist.size()!=0)
		{
			return ulist.size();
		}else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return ulist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		UsedHolder holder=new UsedHolder();
		if(convertView==null)
		{
			convertView=inflater.inflate(R.layout.used_item, null);
			holder.label=(TextView)convertView.findViewById(R.id.used_item_name);
			holder.price=(TextView)convertView.findViewById(R.id.used_item_money);
			holder.into_new=(TextView)convertView.findViewById(R.id.used_item_into_new);
			holder.des=(TextView)convertView.findViewById(R.id.used_item_describe);
			convertView.setTag(holder);//绑定ViewHolder对象
		}else {
			holder=(UsedHolder)convertView.getTag();
		}
		holder.label.setText(ulist.get(position).getLabel());
		holder.price.setText(ulist.get(position).getPrice());
		holder.into_new.setText(ulist.get(position).getInto_new()+"成新");
		holder.des.setText(ulist.get(position).getDes());
		return convertView;
	}

}
