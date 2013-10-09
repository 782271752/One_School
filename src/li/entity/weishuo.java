package li.entity;

public class weishuo {
	private String headImage_url;
	private String name;
	private String text;
	private String textImage_url;
	private int share_count;
	private int like_count;
	private int comment_count;
	
	public String getHeadImage_url() {
		return headImage_url;
	}
	
	public void setHeadImage_url(String headImage_url) {
		this.headImage_url = headImage_url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getTextImage_url() {
		return textImage_url;
	}
	
	public void setTextImage_url(String textImage_url) {
		this.textImage_url = textImage_url;
	}
	
	public int getShare_count() {
		return share_count;
	}
	
	public void setShare_count(int share_count) {
		this.share_count = share_count;
	}
	
	public int getLike_count() {
		return like_count;
	}
	
	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}
	
	public int getComment_count() {
		return comment_count;
	}

	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}
	
}
