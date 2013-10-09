package li.widget;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MyImageView extends ImageView {

	private boolean onAnimation = true;

	private boolean isFirst = true;
	private float minScale = 0.95f;
	private int vWidth;
	private int vHeight;
	private boolean isFinish = true;
	private boolean clickGuolv = false;
	//private Camera camera;

	boolean XbigY = false;
	float RolateX = 0;
	float RolateY = 0;

	OnViewClick onclick=null;
	
	public MyImageView(Context context) {
		super(context);
	}

	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void SetAnimationOnOff(boolean oo) {
		onAnimation = oo;
	}
	public void setOnClickIntent(OnViewClick onclick){
		this.onclick=onclick;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (isFirst) {
			isFirst = false;
			init();
		}
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
				| Paint.FILTER_BITMAP_FLAG));
	}

	public void init() {
		vWidth = getWidth() - getPaddingLeft() - getPaddingRight();
		vHeight = getHeight() - getPaddingTop() - getPaddingBottom();
		Drawable drawable = getDrawable();
		BitmapDrawable bd = (BitmapDrawable) drawable;
		bd.setAntiAlias(true);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		if (!onAnimation)
			return true;

		
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			
			handler.sendEmptyMessage(1);				
			break;
		case MotionEvent.ACTION_MOVE:
			
			
			break;
		case MotionEvent.ACTION_UP:
			
				handler.sendEmptyMessage(6);
				
			break;
		}
		return true;
	}
	

	public interface OnViewClick {
		public void onClick();
	}
	

	private Handler handler = new Handler() {
		private Matrix matrix = new Matrix();
		private float s;
		int count = 0;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			matrix.set(getImageMatrix());
			switch (msg.what) {
			case 1:
				if (!isFinish) {
					return;
				} else {
					isFinish = false;
					count = 0;
					s = (float)Math.sqrt(Math.sqrt(minScale));
					BeginScale(matrix, s);
					handler.sendEmptyMessage(2);
				}
				break;
			case 2:
				BeginScale(matrix, s);
				if (count < 4) {
					handler.sendEmptyMessage(2);
				} else {
					isFinish = true;
					if(clickGuolv){
						clickGuolv = false;
						onclick.onClick();
						
					}
					else {clickGuolv = true;}
				}
				count++;
				break;
			case 6:
				if (!isFinish) {
					handler.sendEmptyMessage(6);
				} else {
					isFinish = false;
					count = 0;
					s = (float) Math.sqrt(Math.sqrt(1.0f / minScale));
					BeginScale(matrix, s);
					handler.sendEmptyMessage(2);
				}
				break;
			}
		}
	};
	
	
	private synchronized void BeginScale(Matrix matrix, float scale) {
		int scaleX = (int) (vWidth * 0.5f);
		int scaleY = (int) (vHeight * 0.5f);
		matrix.postScale(scale, scale, scaleX, scaleY);//中心处缩放
		setImageMatrix(matrix);
	}

}
