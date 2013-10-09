package li.adapter;

import java.net.URL;
import java.util.List;

import li.bitmap.FinalBitmap;
import li.dream.R;
import li.dream.SetheadImageActivity;
import li.entity.Album;
import li.entity.hotel;
import li.viewholder.galleryHolder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class hotelgalleryAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private List<Album> hotel_list;
	//private FinalBitmap fb;
	private Bitmap bp;
	
	public hotelgalleryAdapter(Context context,List<Album> list){
		this.inflater=LayoutInflater.from(context);
		this.hotel_list=list;
		
//		fb=FinalBitmap.create(context);
//		 fb.configDiskCachePath(new SetheadImageActivity().getCache());
//		 fb.configLoadingImage(R.drawable.head);
//		 fb.configLoadfailImage(R.drawable.head);
//		 fb.configBitmapLoadThreadSize(3);
//		 fb.init();
	}
	@Override
	public int getCount() {
		 
		return hotel_list.size();
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
		galleryHolder holder=new galleryHolder();
		if(convertView==null){
			convertView=inflater.inflate(R.layout.gallery_item, null);
			holder.gallery_item=(ImageView)convertView.findViewById(R.id.gallery_item_image);
			convertView.setTag(holder);
		}else {
			holder=(galleryHolder)convertView.getTag();
		}
		
	//	fb.display(holder.gallery_item,hotel_list.get(position).getCovert());
		try {
			URL picUrl = new URL(hotel_list.get(position).getCovert());
			bp=BitmapFactory.decodeStream(picUrl.openStream());
			holder.gallery_item.setImageBitmap(bp);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return convertView;
	}

}
