
package li.adapter;

import java.util.HashMap;

import li.dream.R;
import li.dream.moreActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.framework.AuthorizeAdapter;
import cn.sharesdk.framework.WeiboActionListener;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.weibo.TencentWeibo;

/** 
 * �?��用于演示{@link AuthorizeAdapter}的例子�?
 * <p>
 * 本demo将在授权页面底部显示�?��“关注官方微博�?的提示框�?
 *用户可以在授权期间对这个提示进行控制，�?择关注或者不�?
 *注，如果用户�?��确定关注此平台官方微博，会在授权结束�?
 *后执行关注的方法�?
 */
public class MyAdapter extends AuthorizeAdapter implements OnClickListener, WeiboActionListener {
	private CheckedTextView ctvFollow;
	private WeiboActionListener backListener;
	    
	public void onCreate() {
		String weiboName = getWeiboName();
		int count=getTitleLayout().getChildCount();      
		getTitleLayout().removeViewAt(count-1);
		
		if (SinaWeibo.NAME.equals(weiboName)
				|| TencentWeibo.NAME.equals(weiboName)) {
			initUi(weiboName);
			interceptWeiboActionListener(weiboName);
		}
	}

	private void initUi(String weiboName) {
		ctvFollow = new CheckedTextView(getActivity());
		ctvFollow.setBackgroundResource(R.drawable.auth_follow_bg);
		ctvFollow.setChecked(true);
		ctvFollow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.auth_cb, 0, 0, 0);
		int dp_10 = dipToPx(10);
		ctvFollow.setCompoundDrawablePadding(dp_10);
		ctvFollow.setGravity(Gravity.CENTER_VERTICAL);
		ctvFollow.setPadding(dp_10, dp_10, dp_10, dp_10);
		ctvFollow.setText("关注新浪微博");
		if (weiboName.equals(TencentWeibo.NAME)) {
			ctvFollow.setText("关注腾讯微博");
		}
		ctvFollow.setTextColor(0xff909090);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		ctvFollow.setLayoutParams(lp);
		LinearLayout llBody = (LinearLayout) getBodyView().getChildAt(0);
		llBody.addView(ctvFollow);
		ctvFollow.setOnClickListener(this);
		
		ctvFollow.measure(0, 0);
		int height = ctvFollow.getMeasuredHeight();
		TranslateAnimation animShow = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0, 
				Animation.RELATIVE_TO_SELF, 0, 
				Animation.ABSOLUTE, height, 
				Animation.ABSOLUTE, 0);
		animShow.setDuration(1000);
		getWebBody().startAnimation(animShow);
		
		
		ctvFollow.startAnimation(animShow);
	}
	
	private void interceptWeiboActionListener(String weiboName) {
		AbstractWeibo weibo = AbstractWeibo.getWeibo(getActivity(), weiboName);
		// 备份此前设置的事件监听器
		backListener = weibo.getWeiboActionListener();
		// 设置新的监听器，实现事件拦截
		weibo.setWeiboActionListener(this);
	}

	public void onError(AbstractWeibo weibo, int action, Throwable t) {
		if (action == AbstractWeibo.ACTION_AUTHORIZING) { // 授权即出�?
			weibo.setWeiboActionListener(backListener);
			if (backListener != null) {
				backListener.onError(weibo, action, t);
			}
		}
		else { // 当作授权以后不做任何事情
			weibo.setWeiboActionListener(backListener);
			if (backListener != null) {
				backListener.onComplete(weibo, AbstractWeibo.ACTION_AUTHORIZING, null);
			}
		}
	}
	
	public void onComplete(AbstractWeibo weibo, int action,
			HashMap<String, Object> res) {
		if (action == AbstractWeibo.ACTION_FOLLOWING_USER) { // 关注成功也只是当作授权成功返�?
			weibo.setWeiboActionListener(backListener);
			if (backListener != null) {
				backListener.onComplete(weibo, AbstractWeibo.ACTION_AUTHORIZING, null);
			}
		}
		else if (ctvFollow.isChecked())  // 授权成功，执行关�?
		{ 			
			String account = moreActivity.SDK_SINAWEIBO_UID;
			if (TencentWeibo.NAME.equals(weibo.getName())) {
				account = moreActivity.SDK_TENCENTWEIBO_UID;
			}
			weibo.followFriend(account);
		}
		else { // 如果没有标记为�?授权并关注�?则直接返�?
			weibo.setWeiboActionListener(backListener);
			if (backListener != null) {
				backListener.onComplete(weibo, AbstractWeibo.ACTION_AUTHORIZING, null);
			}
		}
	}
	
	public void onCancel(AbstractWeibo weibo, int action) {
		weibo.setWeiboActionListener(backListener);
		if (action == AbstractWeibo.ACTION_AUTHORIZING) { // 授权前取�?
			if (backListener != null) {
				backListener.onCancel(weibo, action);
			}
		}
		else { // 当作授权以后不做任何事情
			if (backListener != null) {
				backListener.onComplete(weibo, AbstractWeibo.ACTION_AUTHORIZING, null);
			}
			
		}
	}
	
	public void onClick(View v) {
		CheckedTextView ctv = (CheckedTextView) v;
		ctv.setChecked(!ctv.isChecked());
	}
	
	public void onResize(int w, int h, int oldw, int oldh) {
		if (ctvFollow != null) {
			if (oldh - h > 100) {
				ctvFollow.setVisibility(View.GONE);
			}
			else {
				ctvFollow.setVisibility(View.VISIBLE);
			}
		}
	}
	
	private int dipToPx(int dip) {
		return (int) (dip * getActivity().getResources().getDisplayMetrics().density + 0.5f);
	}
	
}
