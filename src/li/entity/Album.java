package li.entity;

public class Album {
	
	private String pid;//相册pid
	private String covert;//封面图片的地址
	private String name;//相册名称
	private String counts;//图片数量
	private String date;//相册建立时间
	 
	public String getPid() {
		return pid;
	}
	 
	public void setPid(String pid) {
		this.pid = pid;
	}
	 
	public String getCovert() {
		return covert;
	}
	 
	public void setCovert(String covert) {
		this.covert = covert;
	}
	 
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
	 
	public String getCounts() {
		return counts;
	}
	 
	public void setCounts(String counts) {
		this.counts = counts;
	}
	 
	public String getDate() {
		return date;
	}
 
	public void setDate(String date) {
		this.date = date;
	}
	

}
