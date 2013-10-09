package li.adapter;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import li.dream.R;
import li.entity.ImageViewInfo;
import li.utils.AlertDialogUtils;
import li.utils.SDCardFileUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;



public class ImageViewSwitcherAdapter extends BaseAdapter{

	private Context context;
	private List<Serializable>  list ;
	public ImageViewSwitcherAdapter(Context context,List<Serializable> srcList) {
		this.context= context;
		this.list = srcList;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public ImageViewInfo getItem(int position) {
		return (ImageViewInfo) list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.imageviewinfo, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Builder builder = new AlertDialog.Builder(context);
				ImageView imageView = new ImageView(context);
				imageView.setImageBitmap(BitmapFactory.decodeFile(((ImageViewInfo)(getList().get(position))).getFilePath()));
				builder.setView(imageView);
				final AlertDialog alertDialog = builder.create();
				imageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						alertDialog.cancel();
					}
				});
				imageView.setOnLongClickListener(new OnLongClickListener() {
					@Override
					public boolean onLongClick(final View v) {
						AlertDialogUtils.deleteChooseDialog((Activity) context,
								new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,int which) {
								dialog.dismiss();
								alertDialog.dismiss();
								final String filePath = getItem(position).getFilePath();
								// 删除图片
								list.remove(position);
								notifyDataSetChanged();
								new Thread(new Runnable() {
									@Override
									public void run() {
										//删除文件
										try {
											SDCardFileUtils.forceDelete(new File(filePath));
										} catch (IOException e) {
											Log.e(this.getClass().toString(), filePath+"文件未找到或文件正在使用");
										}
									}
								}).start();
								
							}
						});
						return true;
					}
				});
				alertDialog.show();
			}
		});
		if(getItem(position)!=null){
			imageView.setImageBitmap(getItem(position).getBitmap());
		}
		return view;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public List<Serializable> getList() {
		return list;
	}


}
