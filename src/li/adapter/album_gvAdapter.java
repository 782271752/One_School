package li.adapter;

import java.util.List;
import java.util.Map;

import li.bitmap.FinalBitmap;
import li.dream.R;
import li.dream.SetheadImageActivity;
import li.entity.album_pictures;
import li.viewholder.album_gridviewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class album_gvAdapter extends BaseAdapter{
	
	private LayoutInflater inflater;	
//	public List<Map<String, Object>> list;
	
	public List<album_pictures> plist;
	public FinalBitmap fb;
	
 
	public album_gvAdapter(Context context,List<album_pictures> list){
		this.inflater=LayoutInflater.from(context);
		this.plist=list;
		fb = FinalBitmap.create(context);
		fb.configDiskCachePath(new SetheadImageActivity().getCache());
		fb.configLoadingImage(R.drawable.head);
		fb.configLoadfailImage(R.drawable.head);
		fb.configBitmapLoadThreadSize(5);
		fb.init();
	}


	@Override
	public int getCount() {
		 if(plist.size()!=0){
			 return plist.size();
		 }else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		 return plist.get(position);
	}

	@Override
	public long getItemId(int position) {
		 
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		album_gridviewHolder gridviewHolder;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.gridview_item, null);
			gridviewHolder=new album_gridviewHolder();
			
			gridviewHolder.image=(ImageView)convertView.findViewById(R.id.album_gridview_image);
			gridviewHolder.image.setScaleType(ScaleType.CENTER_CROP);
			
			convertView.setTag(gridviewHolder);
		}else {
			gridviewHolder=(album_gridviewHolder)convertView.getTag();
		}
		fb.display(gridviewHolder.image, plist.get(position).getPicture_url());
		return convertView;
	}
	
	
	
}
