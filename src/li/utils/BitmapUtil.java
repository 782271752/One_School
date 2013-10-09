package li.utils;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * bitmap�Ĳ�����
 * 
 * 
 * 
 */
public class BitmapUtil {

	/**
	 * ��drawable ר��bitmap
	 * 
	 * @param context
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Context context, Drawable drawable) {
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * ѹ��ͼƬ��ָ����С����
	 * 
	 * @param pathName
	 * @param maxWidth
	 * @param maxHeight
	 * @return null or thumbs bitmap
	 */
	public static Bitmap compressBitmap(String pathName, int maxWidth, int maxHeight) {
		// ��ȡԴͼƬ�Ĵ�С
		Bitmap bm = null;
		try {
			BitmapFactory.Options opts = createOptions(pathName, maxWidth, maxHeight);
			// ��ȡ���ź�ͼƬ
			return BitmapFactory.decodeFile(pathName, opts);

		} catch (Exception e) {
			// showAlert("CreateFile" 0 e.toString() "OK" false);
			Log.e("error", "Compress BitMap failed! " + e.getMessage());
		} catch (OutOfMemoryError ex) {
			ex.printStackTrace();
		}

		return bm;
	}

	/**
	 * ��ָ��ͼƬ��С����ѹ������
	 * 
	 * @param pathName  ����ͼƬ·��
	 * @param maxWidth  ѹ�����ͼƬ�����
	 * @param maxHeight ѹ�����ͼƬ���߶�
	 * @return
	 */
	public static BitmapFactory.Options createOptions(String pathName, int maxWidth, int maxHeight) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		// ��opts��Ϊnullʱ����decodeFile���ؿգ���ΪͼƬ�����ڴ棬ֻ��ȡͼƬ�Ĵ�С����������opts��outWidth��outHeight
		BitmapFactory.decodeFile(pathName, opts);

		int srcWidth = opts.outWidth;
		int srcHeight = opts.outHeight;
		int destWidth = 0;
		int destHeight = 0;
		// ���ŵı���
		double ratio = 0.0;
		// �������������ź��ͼƬ��С��maxLength�ǳ�����������󳤶�
		if (srcWidth > srcHeight) {
			ratio = srcWidth / maxWidth;
			destWidth = maxWidth;
			destHeight = (int) (srcHeight / ratio);
		} else {
			ratio = srcHeight / maxHeight;
			destHeight = maxHeight;
			destWidth = (int) (srcWidth / ratio);
		}
		// ��ͼƬ����ѹ�������ڶ�ȡ�Ĺ����н���ѹ���������ǰ�ͼƬ�������ڴ��ٽ���ѹ��
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// ���ŵı����������Ǻ��Ѱ�׼���ı����������ŵģ�Ŀǰ��ֻ����ֻ��ͨ��inSampleSize���������ţ���ֵ�������ŵı�����SDK�н�����ֵ��2��ָ��ֵ
		newOpts.inSampleSize = (int) ratio + 1;
		// inJustDecodeBounds��Ϊfalse��ʾ��ͼƬ�����ڴ���
		newOpts.inJustDecodeBounds = false;
		// ���ô�С�����һ���ǲ�׼ȷ�ģ�����inSampleSize��Ϊ׼���������������ȴ��������
		newOpts.outHeight = destHeight;
		newOpts.outWidth = destWidth;
		return newOpts;
	}
	 public static long getBitmapSize(Bitmap bitmap) {
	        long length = 0;
	        if (bitmap != null) {
	            ByteArrayOutputStream stream = new ByteArrayOutputStream();
	            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
	            byte[] imageInByte = stream.toByteArray();
	            length = imageInByte.length;
	        }
	        return length;
	    }
}
