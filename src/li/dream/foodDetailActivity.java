package li.dream;

 
import li.onekeyshare.ShareAllGird;
import li.onekeyshare.SharePage;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.view.View.OnClickListener;

public class foodDetailActivity extends loginActivity implements OnClickListener{
	private String id="14";
	private Button longphone,shortphone;
	private ImageView share;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.food_detail);
		init();
		share.setOnClickListener(this);
		
	}
	public void init()
	{
		longphone=(Button)findViewById(R.id.food_detail_longphone);
		shortphone=(Button)findViewById(R.id.food_detail_shortphone);
		share=(ImageView)findViewById(R.id.food_detail_share);
	}
	
	// 使用快捷分享完成图文分享
	private void showGrid(boolean silent) {
			Intent i = new Intent(this, ShareAllGird.class);
			// 分享时Notification的图标
			i.putExtra("notif_icon", R.drawable.logo);
			// 分享时Notification的标题
			i.putExtra("notif_title", "同校网");
			
			// address是接收人地址，仅在信息和邮件使用，否则可以不提供
			i.putExtra("address", "12345678901");
			// title标题，在印象笔记、邮箱、信息、微信（包括好友和朋友圈）、人人网和QQ空间使用，否则可以不提供
			i.putExtra("title", "同校网");
			// titleUrl是标题的网络链接，仅在QQ空间使用，否则可以不提供
			i.putExtra("titleUrl", "http://121.199.40.201/");
			// text是分享文本，所有平台都需要这个字段
			i.putExtra("text", "登陆同校网，应有尽有，详情请看官网:http://121.199.40.201/detail_waimai.php?wid="+id+" @大学生同校网");
			// imagePath是本地的图片路径，所有平台都支持这个字段，不提供，则表示不分享图片
			i.putExtra("imagePath", FinalActivity.TEST_IMAGE);
			// url仅在人人网和微信（包括好友和朋友圈）中使用，否则可以不提供
			i.putExtra("url", "http://121.199.40.201/detail_waimai.php?wid="+id);
			// thumbPath是缩略图的本地路径，仅在微信（包括好友和朋友圈）中使用，否则可以不提供
			i.putExtra("thumbPath", FinalActivity.TEST_IMAGE);
			// appPath是待分享应用程序的本地路劲，仅在微信（包括好友和朋友圈）中使用，否则可以不提供
			i.putExtra("appPath", FinalActivity.TEST_IMAGE);
			// comment是我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供
			i.putExtra("comment", "分享");
			// site是分享此内容的网站名称，仅在QQ空间使用，否则可以不提供
			i.putExtra("site", "同校网");
			// siteUrl是分享此内容的网站地址，仅在QQ空间使用，否则可以不提供
			i.putExtra("siteUrl", "http://121.199.40.201/detail_waimai.php?wid="+id);
			
			// 是否直接分享
			i.putExtra("silent", silent);
			this.startActivity(i);
		}
		
		// 使用快捷分享完成直接分享
	private void showShare(final String platform) {
			Intent i = new Intent(this, SharePage.class);
			// 分享时Notification的图标
			i.putExtra("notif_icon", R.drawable.ic_launcher);
			// 分享时Notification的标题
			i.putExtra("notif_title", "同校网");
			
			// address是接收人地址，仅在信息和邮件使用，否则可以不提供
			i.putExtra("address", "12345678901");
			// title标题，在印象笔记、邮箱、信息、微信（包括好友和朋友圈）、人人网和QQ空间使用，否则可以不提供
			i.putExtra("title","分享");
			// titleUrl是标题的网络链接，仅在QQ空间使用，否则可以不提供
			i.putExtra("titleUrl", "http://121.199.40.201/");
			// text是分享文本，所有平台都需要这个字段
			i.putExtra("text", "登陆同校网，应有尽有，详情请看官网:http://121.199.40.201/ @大学生同校网");
			// imagePath是本地的图片路径，在豆瓣、Facebook、网易微博、新浪微博、腾讯微博、Twitter、邮箱、
			// 信息和微信（包括好友和朋友圈）图片分享中使用，否则可以不提供
			i.putExtra("imagePath", FinalActivity.TEST_IMAGE);
			// imageUrl是网络的图片路径，仅在人人网和QQ空间使用，否则可以不提供
			i.putExtra("imageUrl", "http://121.199.40.201/images/logo2.gif");
			// url仅在人人网和微信（包括好友和朋友圈）中使用，否则可以不提供
			i.putExtra("url", "http://sharesdk.cn");
			// thumbPath是缩略图的本地路径，仅在微信（包括好友和朋友圈）中使用，否则可以不提供
			i.putExtra("thumbPath", FinalActivity.TEST_IMAGE);
			// appPath是待分享应用程序的本地路劲，仅在微信（包括好友和朋友圈）中使用，否则可以不提供
			i.putExtra("appPath", FinalActivity.TEST_IMAGE);
			// comment是我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供
			i.putExtra("comment", "分享");
			// site是分享此内容的网站名称，仅在QQ空间使用，否则可以不提供
			i.putExtra("site", "同校网");
			// siteUrl是分享此内容的网站地址，仅在QQ空间使用，否则可以不提供
			i.putExtra("siteUrl", "http://121.199.40.201/");
			
			// 是平台名称
			i.putExtra("platform", platform);
			this.startActivity(i);
		}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.food_detail_share:
			showGrid(false);
			break;

		default:
			break;
		}
			
	}
	
	
		
}
