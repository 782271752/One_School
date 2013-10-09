package li.entity;

import java.io.Serializable;

public class note implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String uid;
	private String head_url;
	private String name;
	private String time;
	private String context;
	private String note_pic;
	private String comment_count;
	private String like_count;
	private String share_count;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	/**
	 * @return the head_url
	 */
	public String getHead_url() {
		return head_url;
	}
	/**
	 * @param head_url the head_url to set
	 */
	public void setHead_url(String head_url) {
		this.head_url = head_url;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return the context
	 */
	public String getContext() {
		return context;
	}
	/**
	 * @param context the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}
	/**
	 * @return the note_pic
	 */
	public String getNote_pic() {
		return note_pic;
	}
	/**
	 * @param note_pic the note_pic to set
	 */
	public void setNote_pic(String note_pic) {
		this.note_pic = note_pic;
	}
	/**
	 * @return the comment_count
	 */
	public String getComment_count() {
		return comment_count;
	}
	/**
	 * @param comment_count the comment_count to set
	 */
	public void setComment_count(String comment_count) {
		this.comment_count = comment_count;
	}
	/**
	 * @return the like_count
	 */
	public String getLike_count() {
		return like_count;
	}
	/**
	 * @param like_count the like_count to set
	 */
	public void setLike_count(String like_count) {
		this.like_count = like_count;
	}
	/**
	 * @return the share_count
	 */
	public String getShare_count() {
		return share_count;
	}
	/**
	 * @param share_count the share_count to set
	 */
	public void setShare_count(String share_count) {
		this.share_count = share_count;
	}
	
	
}
