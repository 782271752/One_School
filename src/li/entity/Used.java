package li.entity;

import java.io.Serializable;

public class Used implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String shop_id;
	private String shop_name; //名称
	private String into_new; //成新
	private String des; //描述
	private String buttime;
	private String pic;
	private String label; //标签
	private String price;
	private String ori_price;
	private String longtel; //长号
	private String shorttel; //短号
	private String picUrls;
	
	/**
	 * @return the picUrls
	 */
	public String getPicUrls() {
		return picUrls;
	}
	/**
	 * @param picUrls the picUrls to set
	 */
	public void setPicUrls(String picUrls) {
		this.picUrls = picUrls;
	}
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
	 * @return the shop_id
	 */
	public String getShop_id() {
		return shop_id;
	}
	/**
	 * @param shop_id the shop_id to set
	 */
	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}
	/**
	 * @return the shop_name
	 */
	public String getShop_name() {
		return shop_name;
	}
	/**
	 * @param shop_name the shop_name to set
	 */
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	/**
	 * @return the into_new
	 */
	public String getInto_new() {
		return into_new;
	}
	/**
	 * @param into_new the into_new to set
	 */
	public void setInto_new(String into_new) {
		this.into_new = into_new;
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
	 * @return the buttime
	 */
	public String getButtime() {
		return buttime;
	}
	/**
	 * @param buttime the buttime to set
	 */
	public void setButtime(String buttime) {
		this.buttime = buttime;
	}
	/**
	 * @return the pic
	 */
	public String getPic() {
		return pic;
	}
	/**
	 * @param pic the pic to set
	 */
	public void setPic(String pic) {
		this.pic = pic;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
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
	 * @return the ori_price
	 */
	public String getOri_price() {
		return ori_price;
	}
	/**
	 * @param ori_price the ori_price to set
	 */
	public void setOri_price(String ori_price) {
		this.ori_price = ori_price;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
