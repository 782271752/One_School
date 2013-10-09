package li.widget;



import li.dream.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;



public class MyTurnplateView extends View implements  OnTouchListener{
	
	

	private OnTurnplateListener onTurnplateListener;
	public void setOnTurnplateListener(OnTurnplateListener onTurnplateListener) {
		this.onTurnplateListener = onTurnplateListener;
	}
	/**
	 * 画笔：点、线
	 */
	private Paint mPaint = new Paint();
	/**
	 * 画笔：圆
	 */
	private Paint paintCircle =  new Paint();
	/**
	 * 图标列表
	 */
	private Bitmap[] icons = new Bitmap[10];
	/**
	 * point列表
	 */
	private Point[] points;
	/**
	 * 数目
	 */
	private static final int PONIT_NUM = 10;
	
	/**
	 * 圆心坐标
	 */
	private int mPointX=0, mPointY=0;
	/**
	 * 半径
	 */
	private int mRadius = 0;
	/**
	 * 每两个点间隔的角度
	 */
	private int mDegreeDelta;
	/**
	 * 每次转动的角度差
	 */
	private int tempDegree = 0;
	/**
	 * 选中的图标标识 999：未选中任何图标
	 */
	private int chooseBtn=999;
	private Matrix mMatrix = new Matrix();  
	
	public MyTurnplateView(Context context){
		super(context);
		
	}
	public MyTurnplateView(Context context,AttributeSet attrs){
		super(context,attrs);
		paintCircle.setAntiAlias(true);
		paintCircle.setColor(Color.WHITE);
		loadIcons();
		mPointY= this.getContext().getResources().getDisplayMetrics().heightPixels/2-100;
		mPointX= this.getContext().getResources().getDisplayMetrics().widthPixels/2;
		mRadius=this.getContext().getResources().getDisplayMetrics().widthPixels/3+30;
		initPoints();
		computeCoordinates();
	}
	
	
/*	public MyTurnplateView(Context context, int px, int py, int radius) {
		super(context);		
	//	mPaint.setColor(Color.RED);
	//	mPaint.setStrokeWidth(2);
		paintCircle.setAntiAlias(true);
		paintCircle.setColor(Color.WHITE);
		loadIcons();
		mPointX = px;
		mPointY = py;
		mRadius = radius;
		
		initPoints();
		computeCoordinates();
	}
*/
	/**
	 * 
	 * 方法名：loadBitmaps 
	 * 功能：装载图片
	 * 参数：
	 * @param key
	 * @param d

	 */
	public void loadBitmaps(int key,Drawable d){
		Bitmap bitmap = Bitmap.createBitmap(130,130,Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		d.setBounds(0, 0, 130, 130);
		d.draw(canvas);
		icons[key]=bitmap;
	}
	/**
	 * 
	 * 方法名：loadIcons 
	 * 功能：获取所有图片
	 * 参数：

	 */
	public void loadIcons(){
		Resources r = getResources();	
		loadBitmaps(8, r.getDrawable(R.drawable.photo));
		loadBitmaps(1, r.getDrawable(R.drawable.photo));
		loadBitmaps(2, r.getDrawable(R.drawable.photo));
		loadBitmaps(3, r.getDrawable(R.drawable.photo));
		loadBitmaps(4, r.getDrawable(R.drawable.photo));
		loadBitmaps(5, r.getDrawable(R.drawable.photo));
		loadBitmaps(6, r.getDrawable(R.drawable.photo));
		loadBitmaps(7, r.getDrawable(R.drawable.photo));
		loadBitmaps(0, r.getDrawable(R.drawable.photo));
		loadBitmaps(9, r.getDrawable(R.drawable.photo));	
//		loadBitmaps(10, r.getDrawable(R.drawable.girl));	
//		loadBitmaps(9, r.getDrawable(R.drawable.hoticon));
	}

	
	/**
	 * 
	 * 方法名：initPoints 
	 * 功能：初始化每个点
	 * 参数：

	 */
	 
	private void initPoints() {
		points = new Point[PONIT_NUM];
		Point point;
		int angle = 0;
		mDegreeDelta =360/PONIT_NUM;
		
		for(int index=0; index<PONIT_NUM; index++) {
			point = new Point();
			point.angle = angle;
			angle += mDegreeDelta;
			point.bitmap = icons[index];
			point.flag=index;
			points[index] = point;
			
		}
	}
	
	/**
	 * 
	 * 方法名：resetPointAngle 
	 * 功能：重新计算每个点的角度
	 * 参数：
	 * @param x
	 * @param y

	 */	
	private void resetPointAngle(float x, float y) {
		int degree = computeMigrationAngle(x, y);
		for(int index=0; index<PONIT_NUM; index++) {			
			points[index].angle += degree;		
			if(points[index].angle>360){
				points[index].angle -=360;
			}else if(points[index].angle<0){
				points[index].angle +=360;
			}
			
		}
	}
	
	/**
	 * 
	 * 方法名：computeCoordinates 
	 * 功能：计算每个点的坐标
	 * 参数：
 
	 */
	private void computeCoordinates() {
		Point point;
		for(int index=0; index<PONIT_NUM; index++) {
			point = points[index];
			point.x = mPointX+ (float)(mRadius * Math.cos(point.angle*Math.PI/180));
			point.y = mPointY+ (float)(mRadius * Math.sin(point.angle*Math.PI/180));	
			point.x_c = mPointX+(point.x-mPointX)/2;
			point.y_c = mPointY+(point.y-mPointY)/2;
			//Log.e(TAG, point.angle+"");
		}
	}
	
	/**
	 * 
	 * 方法名：computeMigrationAngle 
	 * 功能：计算偏移角度
	 * 参数：
	 * @param x
	 * @param y

	 *///
	private int computeMigrationAngle(float x, float y) {
		int a=0;
		float distance = (float)Math.sqrt(((x-mPointX)*(x-mPointX) + (y-mPointY)*(y-mPointY)));
		int degree = (int)(Math.acos((x-mPointX)/distance)*180/Math.PI);
		if(y < mPointY) {
			degree = -degree;
		}	
		if(tempDegree!=0){
			a = degree - tempDegree;
		}
		tempDegree=degree;		
		return a;
	}
	
	
	
	/**
	 * 
	 * 方法名：computeCurrentDistance 
	 * 功能：计算触摸的位置与各个元点的距离
	 * 参数：
	 * @param x
	 * @param y
	 * @return
	 */
	private void computeCurrentDistance(float x, float y) {
		for(Point point:points){
			float distance = (float)Math.sqrt(((x-point.x)*(x-point.x) + (y-point.y)*(y-point.y)));			
			if(distance<31){
				chooseBtn = 999;
				point.isCheck = true;
				break;
			}else{
				point.isCheck = false;
				chooseBtn =  point.flag;
			}
		}	
	}
	
	private void switchScreen(MotionEvent event){
		computeCurrentDistance(event.getX(), event.getY());
		for(Point point:points){
			if(point.isCheck)
			{
				onTurnplateListener.onPointTouch(point);
				break;
			}
		}
		//Log.e(TAG,chooseBtn+"");	
		
		
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {		
		 int action = event.getAction();
	        switch (action) {
	        case MotionEvent.ACTION_DOWN:
	            break;
	        case MotionEvent.ACTION_MOVE:
	        	resetPointAngle(event.getX(), event.getY());
	    		computeCoordinates();
	    		invalidate();
	            break;
	        case MotionEvent.ACTION_UP:
	        	switchScreen(event);
	        	tempDegree = 0;
	        	invalidate();
	            break;
	        case MotionEvent.ACTION_CANCEL:
	        	//系统在运行到一定程度下无法继续响应你的后续动作时会产生此事件。
	        	//一般仅在代码中将其视为异常分支情况处理
	            break;
	        }
		return true;
	}
	
	@Override
	public void onDraw(Canvas canvas) {
//		canvas.drawCircle(mPointX, mPointY, mRadius, paintCircle);
//		canvas.drawPoint(mPointX, mPointY, mPaint);
		
		
		
		
/*		Paint paint = new Paint();
		paint.setColor(getResources().getColor(R.color.blue));
		canvas.drawRect(0,0,2*mPointX,80,paint);  
		//paint.setTextSize(15.0f);
 		//canvas.drawText("测试 demo", 5, 15, paint);
		 
		Paint textpaint = new Paint();   
		textpaint.setColor(Color.WHITE);   
		textpaint.setTextSize(40);   
		textpaint.setTextAlign(Align.CENTER);   
		

		FontMetrics fontMetrics = textpaint.getFontMetrics();   
		// 计算文字高度   
		float fontHeight = fontMetrics.bottom - fontMetrics.top;   
		// 计算文字baseline   
		float textBaseY = 80 - (80 - fontHeight) / 2 - fontMetrics.bottom;   
		canvas.drawText("分类",  mPointX, textBaseY, textpaint);  
		*/
		
		//Bitmap bitmap = ((BitmapDrawable)(getResources().getDrawable(R.drawable.welcome))).getBitmap();
		Bitmap girlBitmap = ((BitmapDrawable)(getResources().getDrawable(R.drawable.center_))).getBitmap();
		//canvas.drawBitmap(bitmap, mPointX- bitmap.getWidth()/2, mPointY-bitmap.getHeight()/2, null);
		canvas.drawBitmap(girlBitmap, mPointX- girlBitmap.getWidth()/2, mPointY-girlBitmap.getHeight()/2, null);
		for(int index=0; index<PONIT_NUM; index++) {
			//canvas.drawPoint(points[index].x_c, points[index].y_c, mPaint);
			drawInCenter(canvas, points[index].bitmap, points[index].x, points[index].y,points[index].flag);
		}
		
		
	}
	
	/**
	 * 
	 * 方法名：drawInCenter 
	 * 功能：把点放到图片中心处
	 * 参数：
	 * @param canvas
	 * @param bitmap
	 * @param left
	 */
	void drawInCenter(Canvas canvas, Bitmap bitmap, float left, float top,int flag) {
		canvas.drawPoint(left, top, mPaint);
		if(chooseBtn==flag){
			//Log.e("Width", bitmap.getWidth()+";"+70f/bitmap.getWidth());
			//Log.e("Height", bitmap.getHeight()+";"+70f/bitmap.getHeight());
		/*	mMatrix.setScale(70f/bitmap.getWidth(), 70f/bitmap.getHeight());   
			mMatrix.postTranslate(left-35, top-35);  
			canvas.drawBitmap(bitmap, mMatrix, null); */
			
			canvas.drawBitmap(bitmap, left-bitmap.getWidth()/2, top-bitmap.getHeight()/2, null);
		}else{
			canvas.drawBitmap(bitmap, left-bitmap.getWidth()/2, top-bitmap.getHeight()/2, null);
		}
		
		
	}	
	
	public class Point {
		
		/**
		 * 位置标识
		 */
		public int flag;
		/**
		 * 图片
		 */
		Bitmap bitmap;
		
		/**
		 * 角度
		 */
		int angle;
		
		/**
		 * x坐标
		 */
		float x;
		
		/**
		 * y坐标
		 */
		float y;
		
		/**
		 * 点与圆心的中心x坐标
		 */
		float x_c;
		/**
		 * 点与圆心的中心y坐标
		 */
		float y_c;
		
		boolean isCheck;
	}

	 public static interface OnTurnplateListener {
	       
	     public void onPointTouch(Point point);
	       	             	        
	 }

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		return false;
	}
	
	
}
