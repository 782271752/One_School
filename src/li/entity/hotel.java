package li.entity;

import java.io.Serializable;

public class hotel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String hid;   //宝贝id
	private String name;	//宝贝名称
	private String covert;
	private String uid; //商家id
	
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

	private String money;
	private String where;
	private String describe;
	
	private String boss;		//商家名称
	private String longtel;		//长号
	private String shorttel;	//短号
	
	private String longitude; //经度
	private String latitude; 	//纬度
	
	private String hotelname;		//消息来源
	private String time;		//发布时间
	public String getHid() {
		return hid;
	}
	 
	public void setHid(String hid) {
		this.hid = hid;
	}
	 
	public String getName() {
		return name;
	}
	 
	public void setName(String name) {
		this.name = name;
	}
	 
	public String getCovert() {
		return covert;
	}
	 
	public void setCovert(String covert) {
		this.covert = covert;
	}
	 
	public String getMoney() {
		return money;
	}
	 
	public void setMoney(String money) {
		this.money = money;
	}
	 
	public String getWhere() {
		return where;
	}
	 
	public void setWhere(String where) {
		this.where = where;
	}

	/**
	 * @return the describe
	 */
	public String getDescribe() {
		return describe;
	}

	/**
	 * @param describe the describe to set
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}

	/**
	 * @return the boss
	 */
	public String getBoss() {
		return boss;
	}

	/**
	 * @param boss the boss to set
	 */
	public void setBoss(String boss) {
		this.boss = boss;
	}

	/**
	 * @return the longtel
	 */
	public String getLongtel() {
		return longtel;
	}

	/**
	 * @param longtel the longtel to set
	 */
	public void setLongtel(String longtel) {
		this.longtel = longtel;
	}

	/**
	 * @return the shorttel
	 */
	public String getShorttel() {
		return shorttel;
	}

	/**
	 * @param shorttel the shorttel to set
	 */
	public void setShorttel(String shorttel) {
		this.shorttel = shorttel;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	 
	/**
	 * @return the hotelname
	 */
	public String getHotelname() {
		return hotelname;
	}

	/**
	 * @param hotelname the hotelname to set
	 */
	public void setHotelname(String hotelname) {
		this.hotelname = hotelname;
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
	 
	 
	 
}
