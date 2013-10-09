package li.entity;

import java.io.Serializable;

import android.graphics.Bitmap;

public class ImageViewInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String filePath;
	private Bitmap bitmap;

	public ImageViewInfo() {
	}

	public ImageViewInfo(String filePath, Bitmap bitmap) {
		this.filePath = filePath;
		this.bitmap = bitmap;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

}
