package li.entity;

import java.io.Serializable;

public class Tour implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String uid;
	private String name;  // 旅游名称
	private String start;  //出发地点
	private String destin; //目的地
	private String days; //天数
	private String cover; // 封面
	private String price; //价格
	private String time; //发布时间
	private String username; //用户名称
	private String long_tel; //长号
	private String short_tel; //短号
	private String des; //描述
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
	 * @return the start
	 */
	public String getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}
	/**
	 * @return the destin
	 */
	public String getDestin() {
		return destin;
	}
	/**
	 * @param destin the destin to set
	 */
	public void setDestin(String destin) {
		this.destin = destin;
	}
	/**
	 * @return the days
	 */
	public String getDays() {
		return days;
	}
	/**
	 * @param days the days to set
	 */
	public void setDays(String days) {
		this.days = days;
	}
	/**
	 * @return the cover
	 */
	public String getCover() {
		return cover;
	}
	/**
	 * @param cover the cover to set
	 */
	public void setCover(String cover) {
		this.cover = cover;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the long_tel
	 */
	public String getLong_tel() {
		return long_tel;
	}
	/**
	 * @param long_tel the long_tel to set
	 */
	public void setLong_tel(String long_tel) {
		this.long_tel = long_tel;
	}
	/**
	 * @return the short_tel
	 */
	public String getShort_tel() {
		return short_tel;
	}
	/**
	 * @param short_tel the short_tel to set
	 */
	public void setShort_tel(String short_tel) {
		this.short_tel = short_tel;
	}
	/**
	 * @return the des
	 */
	public String getDes() {
		return des;
	}
	/**
	 * @param des the des to set
	 */
	public void setDes(String des) {
		this.des = des;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
