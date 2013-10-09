package li.adapter;

import java.util.List;

import li.bitmap.FinalBitmap;
import li.dream.R;
import li.dream.SetheadImageActivity;
import li.entity.Album;
import li.entity.note;
import li.viewholder.albumHolder;
import li.viewholder.noteHolder;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class NoteAdapter extends BaseAdapter{

	public LayoutInflater inflater;	
	private List<note> nlist;
	public FinalBitmap fb;
	
	public NoteAdapter(Context context, List<note> list){
		this.inflater=LayoutInflater.from(context);
		this.nlist=list;
		fb = FinalBitmap.create(context);
		fb.configDiskCachePath(new SetheadImageActivity().getCache());
		fb.configLoadingImage(R.drawable.head);
		fb.configLoadfailImage(R.drawable.head);
		fb.configBitmapLoadThreadSize(4);
		fb.init();
	}
	 
	@Override
	public int getCount() {
		if(nlist.size()!=0)
		{
			return nlist.size();
		}else{
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return nlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		noteHolder holder=new noteHolder();;
		Log.v("note_position", String.valueOf(position)); 
		if(convertView==null){
			convertView=inflater.inflate(R.layout.note_list, null);
			 
			holder.head=(ImageView)convertView.findViewById(R.id.note_list_head);
			holder.name=(TextView)convertView.findViewById(R.id.note_list_name);
			holder.time=(TextView)convertView.findViewById(R.id.note_list_time);
			holder.context=(TextView)convertView.findViewById(R.id.note_list_context);
			holder.pic=(ImageView)convertView.findViewById(R.id.note_list_photo);
			holder.comment_count=(TextView)convertView.findViewById(R.id.note_list_comment_count);
			holder.share_count=(TextView)convertView.findViewById(R.id.note_list_share_count);
			holder.like_count=(TextView)convertView.findViewById(R.id.note_list_like_count);
			
			holder.head.setScaleType(ScaleType.CENTER_CROP);
			holder.pic.setScaleType(ScaleType.CENTER_INSIDE);
			
			convertView.setTag(holder);//绑定ViewHolder对象                   
		}else{
			holder=(noteHolder)convertView.getTag();//取出ViewHolder对象
		}
		 
		 
		fb.display(holder.head, nlist.get(position).getHead_url());
		holder.name.setText(nlist.get(position).getName());
		holder.time.setText(nlist.get(position).getTime());
		holder.context.setText(nlist.get(position).getContext());
		
		Log.v("note_img", nlist.get(position).getNote_pic());
		
		if(!nlist.get(position).getNote_pic().equals("")){
			try {
				holder.pic.setVisibility(View.VISIBLE);
				Log.v("adapter_pic", nlist.get(position).getNote_pic());
				fb.display(holder.pic, nlist.get(position).getNote_pic());
			} catch (Exception e) {
				e.printStackTrace();
				}
			
		}else {
			holder.pic.setVisibility(View.GONE);
		}
		
		
		holder.comment_count.setText(nlist.get(position).getComment_count());
		holder.share_count.setText(nlist.get(position).getShare_count());
		holder.like_count.setText(nlist.get(position).getLike_count());		
		
		return convertView;
	}

}
