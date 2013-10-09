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
	
	// ʹ�ÿ�ݷ������ͼ�ķ���
	private void showGrid(boolean silent) {
			Intent i = new Intent(this, ShareAllGird.class);
			// ����ʱNotification��ͼ��
			i.putExtra("notif_icon", R.drawable.logo);
			// ����ʱNotification�ı���
			i.putExtra("notif_title", "ͬУ��");
			
			// address�ǽ����˵�ַ��������Ϣ���ʼ�ʹ�ã�������Բ��ṩ
			i.putExtra("address", "12345678901");
			// title���⣬��ӡ��ʼǡ����䡢��Ϣ��΢�ţ��������Ѻ�����Ȧ������������QQ�ռ�ʹ�ã�������Բ��ṩ
			i.putExtra("title", "ͬУ��");
			// titleUrl�Ǳ�����������ӣ�����QQ�ռ�ʹ�ã�������Բ��ṩ
			i.putExtra("titleUrl", "http://121.199.40.201/");
			// text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
			i.putExtra("text", "��½ͬУ����Ӧ�о��У������뿴����:http://121.199.40.201/detail_waimai.php?wid="+id+" @��ѧ��ͬУ��");
			// imagePath�Ǳ��ص�ͼƬ·��������ƽ̨��֧������ֶΣ����ṩ�����ʾ������ͼƬ
			i.putExtra("imagePath", FinalActivity.TEST_IMAGE);
			// url������������΢�ţ��������Ѻ�����Ȧ����ʹ�ã�������Բ��ṩ
			i.putExtra("url", "http://121.199.40.201/detail_waimai.php?wid="+id);
			// thumbPath������ͼ�ı���·��������΢�ţ��������Ѻ�����Ȧ����ʹ�ã�������Բ��ṩ
			i.putExtra("thumbPath", FinalActivity.TEST_IMAGE);
			// appPath�Ǵ�����Ӧ�ó���ı���·��������΢�ţ��������Ѻ�����Ȧ����ʹ�ã�������Բ��ṩ
			i.putExtra("appPath", FinalActivity.TEST_IMAGE);
			// comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ�ã�������Բ��ṩ
			i.putExtra("comment", "����");
			// site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ�ã�������Բ��ṩ
			i.putExtra("site", "ͬУ��");
			// siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ�ã�������Բ��ṩ
			i.putExtra("siteUrl", "http://121.199.40.201/detail_waimai.php?wid="+id);
			
			// �Ƿ�ֱ�ӷ���
			i.putExtra("silent", silent);
			this.startActivity(i);
		}
		
		// ʹ�ÿ�ݷ������ֱ�ӷ���
	private void showShare(final String platform) {
			Intent i = new Intent(this, SharePage.class);
			// ����ʱNotification��ͼ��
			i.putExtra("notif_icon", R.drawable.ic_launcher);
			// ����ʱNotification�ı���
			i.putExtra("notif_title", "ͬУ��");
			
			// address�ǽ����˵�ַ��������Ϣ���ʼ�ʹ�ã�������Բ��ṩ
			i.putExtra("address", "12345678901");
			// title���⣬��ӡ��ʼǡ����䡢��Ϣ��΢�ţ��������Ѻ�����Ȧ������������QQ�ռ�ʹ�ã�������Բ��ṩ
			i.putExtra("title","����");
			// titleUrl�Ǳ�����������ӣ�����QQ�ռ�ʹ�ã�������Բ��ṩ
			i.putExtra("titleUrl", "http://121.199.40.201/");
			// text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
			i.putExtra("text", "��½ͬУ����Ӧ�о��У������뿴����:http://121.199.40.201/ @��ѧ��ͬУ��");
			// imagePath�Ǳ��ص�ͼƬ·�����ڶ��ꡢFacebook������΢��������΢������Ѷ΢����Twitter�����䡢
			// ��Ϣ��΢�ţ��������Ѻ�����Ȧ��ͼƬ������ʹ�ã�������Բ��ṩ
			i.putExtra("imagePath", FinalActivity.TEST_IMAGE);
			// imageUrl�������ͼƬ·����������������QQ�ռ�ʹ�ã�������Բ��ṩ
			i.putExtra("imageUrl", "http://121.199.40.201/images/logo2.gif");
			// url������������΢�ţ��������Ѻ�����Ȧ����ʹ�ã�������Բ��ṩ
			i.putExtra("url", "http://sharesdk.cn");
			// thumbPath������ͼ�ı���·��������΢�ţ��������Ѻ�����Ȧ����ʹ�ã�������Բ��ṩ
			i.putExtra("thumbPath", FinalActivity.TEST_IMAGE);
			// appPath�Ǵ�����Ӧ�ó���ı���·��������΢�ţ��������Ѻ�����Ȧ����ʹ�ã�������Բ��ṩ
			i.putExtra("appPath", FinalActivity.TEST_IMAGE);
			// comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ�ã�������Բ��ṩ
			i.putExtra("comment", "����");
			// site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ�ã�������Բ��ṩ
			i.putExtra("site", "ͬУ��");
			// siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ�ã�������Բ��ṩ
			i.putExtra("siteUrl", "http://121.199.40.201/");
			
			// ��ƽ̨����
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
