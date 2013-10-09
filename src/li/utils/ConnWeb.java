package li.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import li.dream.introduce;
import li.entity.Album;
import li.entity.Parttime;
import li.entity.Tour;
import li.entity.Used;
import li.entity.album_pictures;
import li.entity.checkVersion;
import li.entity.hotel;
import li.entity.note;
import li.entity.weishuo;
import li.entity.youji;
import li.entity.youji_content;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.omg.CORBA.PUBLIC_MEMBER;

import android.content.Context;
import android.graphics.Picture;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;



public class ConnWeb {
	
	public String baseUrl="http://121.199.40.201";
	public String loginUrl=baseUrl+"/m/check/login.php";
	//public String headPhotoUrl="";
	public String send_infoUrl="";
	
	

	// 访问网站数据库获取数据
	private String conWeb(String url) {
		String str = "";
		try {
			HttpGet request = new HttpGet(url); 
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				str = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
				e.printStackTrace();
		}
			return str;
		}
	public List<Album> getAlbum(String uid,Integer start_index) //获取相册
	{
		List<Album> album_list=new ArrayList<Album>();
		//m/echo/note.php?from=photos&uid=100000
		String url=baseUrl+"/m/echo/note.php?from=photos&&start="+start_index+"&uid="+uid;
		String str=conWeb(url);
		Log.v("getAlbum", str);
		try{
			 
			JSONObject job = new JSONObject(str);
			JSONArray jay = job.getJSONArray("picture");
			for(int i=0;i<jay.length();i++){
				
				JSONObject temp= (JSONObject)jay.get(i);				
				Album albums=new Album();
				albums.setPid(temp.getString("id"));
				albums.setName(temp.getString("name"));
				albums.setCounts(temp.getString("counts"));
				albums.setDate(temp.getString("time"));
				albums.setCovert(baseUrl+temp.getString("cover"));
				album_list.add(albums);
			}
		}catch (Exception e) {
			e.printStackTrace();
			 
		}
		
		return album_list;
	}
	public checkVersion getAPK(){
		checkVersion check =new checkVersion();
		 
		String url=baseUrl+"";
		String string=conWeb(url);
		try{
			JSONObject object=new JSONObject(string);
			
			check.setServiceVersion(object.getString("version"));
			check.setApk_url(baseUrl+object.getString("apk_url"));
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return check;
	}
	public List<album_pictures> getPictures(String pid,String uid,Integer start_index){ //获取相册相片
		List<album_pictures> pictures_list=new ArrayList<album_pictures>();
		String url=baseUrl+"mobile/echo/echo_picture.php?uid=100000&pid="+pid+"&start="+start_index;
		String str=conWeb(url);
		Log.v("getPictures", str);
		try{
			JSONObject job = new JSONObject(str);
			JSONArray jay=job.getJSONArray("picture");
			for(int i=0;i<jay.length();i++){
				JSONObject temp= (JSONObject)jay.get(i);	
				album_pictures pictures=new album_pictures();
				pictures.setTid(temp.getString("id"));
				pictures.setPicture_url(baseUrl+temp.getString("path"));
				pictures_list.add(pictures);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return pictures_list;
	}	
	public List<youji> getyouji(){  //获取游记
		List<youji> youji_list= new ArrayList<youji>();		
		String url="http://172.18.6.68:718/mobile/htmlTest.php";
		String string=conWeb(url);
		Log.v("ConnWeb", string);	
		try{
			JSONObject object=new JSONObject(string);
			JSONArray array=object.getJSONArray("youji");
			 
			for(int i=0;i<array.length();i++){
				JSONObject temp=(JSONObject)array.get(i);
				youji yj=new youji();
				yj.setId(temp.getString("id"));
				yj.setCover(temp.getString("cover"));
				yj.setTime(temp.getString("time"));
				yj.setDays(temp.getString("days"));
				yj.setStart_time(temp.getString("start_time"));
				yj.setStart(temp.getString("start"));
				yj.setCosts(temp.getString("costs"));
				yj.setDestination(temp.getString("destination"));
				yj.setGroups(temp.getString("groups"));
				yj.setComments_count(temp.getString("comments_count"));
				yj.setShares_count(temp.getString("shares_count"));
				yj.setFavours_count(temp.getString("favours_count"));
				yj.setBrowse_count(temp.getString("browse_count"));
				yj.setTitle(temp.getString("title"));
				 
				
				youji_list.add(yj);
			}
			 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return youji_list;
	}
	public youji_content getContent( ){
		youji_content content=new youji_content();
		String url=baseUrl+"";
		String str=conWeb(url);
		Log.v("getContent", str);
		try {
			JSONObject object=new JSONObject(str);
			content.setContent(object.getString("content"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
	//---------------------------------------租房--------------------------//
	public List<hotel> gethotel(String url,String schoolid,Integer start_index){
		url="http://121.199.40.201/m/echo/tmall.php?from=house";
		List<hotel> hotel_list=new ArrayList<hotel>();
		String[] dividepid;
		String s_url;
		String str=conWeb(url+"&school_id="+schoolid+"&start="+start_index);
		Log.v("gethotel", str);
		try {
			JSONObject object=new JSONObject(str).getJSONObject("note");//获取json对象
			JSONArray jay=object.getJSONArray("r");
			for(int i=0;i<jay.length();i++){
				JSONObject temp=(JSONObject)jay.get(i);
				hotel hotel=new hotel();
				hotel.setHid(temp.getString("id"));
				hotel.setUid(temp.getString("shop_id"));
		//		hotel.setCovert(baseUrl+temp.getString("cover"));
				hotel.setName(temp.getString("name"));
				hotel.setHotelname(temp.getString("shop_name"));
		//		hotel.setTime(temp.getString("time"));
				hotel.setMoney(temp.getString("price"));
				hotel.setWhere(temp.getString("location"));
				hotel.setDescribe(temp.getString("des"));
			//	hotel.setBoss(temp.getString("boss"));
				hotel.setLongtel(temp.getString("phone"));
		
			//	hotel.setLongitude(temp.getString("longitude"));
			//	hotel.setLatitude(temp.getString("latitude"));
				
//-------------------------------判断短号是否为空----------------------------------//
				
				if((!temp.getString("duan").equals("null"))&&temp.getString("duan")!=null&&(!temp.getString("duan").equals(""))&&(!temp.getString("duan").equals("0"))){
					hotel.setShorttel(temp.getString("duan"));
				}else{
					hotel.setShorttel("");
				}
				
				
				if((!temp.getString("cover").equals("null"))&&temp.getString("cover")!=null&&(!temp.getString("cover").equals(""))){
					try {
						dividepid=temp.getString("cover").split("\\.");   //裁剪后缀名
						s_url=baseUrl+temp.getString("cover").toString()+"_s."+dividepid[dividepid.length-1]; //换成小图片
						Log.v("hotel_covert", s_url + i+"");
						hotel.setCovert(s_url);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}else {
					hotel.setCovert("");
				}
				
				hotel_list.add(hotel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hotel_list;
	}
	//----------获取社交部分内容-----------------//
	public List<note> getNote(String url,String schoolid,String type,Integer start_index){ 
		List<note> noteList=new ArrayList<note>();
		String str=conWeb(url+type+"&school_id="+schoolid+"&start="+start_index);
		String[] dividepid;
		String s_url;
		Log.v("getnote", str);
		try {
			JSONObject object=new JSONObject(str).getJSONObject("note");
			
			JSONArray jay=object.getJSONArray("r");
			for(int i=0;i<jay.length();i++){
				JSONObject temp=(JSONObject)jay.get(i);
				note note=new note();
				note.setId(temp.getString("id"));
				note.setUid(temp.getString("uid"));
				note.setName(temp.getString("user_name"));
				
				note.setHead_url(baseUrl+temp.getString("user_pic"));
			//	Log.v("getnotehead",temp.getString("user_pic"));
				
				note.setTime(temp.getString("time"));
				note.setContext(temp.getString("text"));
				
				Log.v("pic",temp.getString("pic").toString()+i+"");
				
				if((!temp.getString("pic").equals("null"))&&temp.getString("pic")!=null&&(!temp.getString("pic").equals(""))){
					try {
						dividepid=temp.getString("pic").split("\\.");   //裁剪后缀名
						s_url=baseUrl+temp.getString("pic").toString()+"_150x150."+dividepid[dividepid.length-1]; //换成小图片
						Log.v("note_pic", s_url + i+"");
						note.setNote_pic(s_url);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}else {
					note.setNote_pic("");
					 
				}		
				note.setComment_count(temp.getString("comments_count"));
				note.setShare_count(temp.getString("shares_count"));
				note.setLike_count(temp.getString("favours_count"));
				
				noteList.add(note);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return noteList;
	}
	//------------------获取旅游信息-----------------------//
	public List<Tour> getTour(String url,String schoolid,Integer start_index)
	{
		url="http://121.199.40.201/m/echo/tmall.php?from=travel";
		List<Tour> tourList=new ArrayList<Tour>();
		String str=conWeb(url+"&school_id="+schoolid+"&start="+start_index);
		String[] dividepid;
		String s_url;
		Log.v("gettour", str);
		try {
			JSONObject object=new JSONObject(str).getJSONObject("note");//获取json对象
			JSONArray array=object.getJSONArray("r");
			for(int i=0;i<array.length();i++)
			{
				JSONObject temp=(JSONObject)array.get(i);
				Tour tour=new Tour();
				tour.setId(temp.getString("id"));
				tour.setUid(temp.getString("shop_id"));
				tour.setName(temp.getString("name"));
				tour.setStart(temp.getString("start"));
				tour.setDestin(temp.getString("destin"));
				tour.setDays(temp.getString("days"));
				tour.setPrice(temp.getString("price"));
			
				tour.setUsername(temp.getString("shop_name"));
				tour.setLong_tel(temp.getString("phone"));
				
				tour.setDes(temp.getString("des"));
				
				//-------------------------------判断短号是否为空----------------------------------//
				
				if((!temp.getString("duan").equals("null"))&&temp.getString("duan")!=null&&(!temp.getString("duan").equals(""))&&(!temp.getString("duan").equals("0"))){
					tour.setShort_tel(temp.getString("duan"));
				}else{
					tour.setShort_tel("");
				}
				
				
				if((!temp.getString("cover").equals("null"))&&temp.getString("cover")!=null&&(!temp.getString("cover").equals(""))){
					try {
						dividepid=temp.getString("cover").split("\\.");   //裁剪后缀名
						s_url=baseUrl+temp.getString("cover").toString()+"_s."+dividepid[dividepid.length-1]; //换成小图片
						Log.v("tour_covert", s_url + i+"");
						tour.setCover(s_url);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}else {
					tour.setCover("");
					 
				}
				tourList.add(tour);
			
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tourList;
	}
	
	public List<Parttime> getParttimes(String url,String schoolid,Integer start_index)
	{
		url="http://121.199.40.201/m/echo/tmall.php?from=parttime";
		List<Parttime> partlist=new ArrayList<Parttime>();
		String str=conWeb(url+"&school_id="+schoolid+"&start="+start_index);
		//String str=conWeb("http://www.tong-xiao.com/m/echo/tmall.php?from=parttime&school_id=560&start=0");
		
		Log.v("getPart", str);
		try {
			JSONObject object=new JSONObject(str).getJSONObject("note");//获取json对象
			JSONArray array=object.getJSONArray("r");
			for(int i=0;i<array.length();i++)
			{
				JSONObject temp=(JSONObject)array.get(i);
				Parttime parttime =new Parttime();
				parttime.setId(temp.getString("id"));
				parttime.setUid(temp.getString("shop_id"));
				parttime.setName(temp.getString("name"));
				parttime.setDays(temp.getString("days"));
				parttime.setWork_hour(temp.getString("work_hour"));
				parttime.setArea(temp.getString("area"));
				parttime.setReward(temp.getString("reward"));
				parttime.setSex(temp.getString("sex"));
				parttime.setNum(temp.getString("num"));
				parttime.setDes(temp.getString("des"));
				//parttime.setTime(temp.getString("time"));
				parttime.setUsername(temp.getString("shop_name"));
				parttime.setLongtel(temp.getString("phone"));
				
			
				String re_StrTime = null; 
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分"); 
				String cc_time=temp.getString("time");
				long lcc_time = Long.valueOf(cc_time); 
				re_StrTime = sdf.format(new Date(lcc_time * 1000L)); 
				parttime.setTime(re_StrTime);

				if((!temp.getString("duan").equals("null"))&&temp.getString("duan")!=null&&(!temp.getString("duan").equals(""))&&(!temp.getString("duan").equals("0"))){
					parttime.setShort_tel(temp.getString("duan"));
				}else{
					parttime.setShort_tel("");
				}
				
				partlist.add(parttime);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return partlist;
	}
	//-------------------------二手----------------------------//
	public List<Used> getUseds(String schoolid,Integer start_index){
		List<Used> uList=new ArrayList<Used>();
		
		String url="http://121.199.40.201/m/echo/tmall.php?from=transfer";
		String str=conWeb(url+"&school_id="+schoolid+"&start="+start_index);
		Log.v("getUsed", str);
		try {
			JSONObject object=new JSONObject(str).getJSONObject("note");//获取json对象
			JSONArray array=object.getJSONArray("r");
			for(int i=0;i<array.length();i++)
			{
				JSONObject temp=(JSONObject)array.get(i);
				Used used=new Used();
				used.setId(temp.getString("id"));
				used.setShop_id(temp.getString("shop_id"));
				used.setDes(temp.getString("des"));
				used.setLabel(temp.getString("label"));
				used.setPrice(temp.getString("price"));
				used.setOri_price(temp.getString("ori_price"));
				used.setInto_new(temp.getString("new"));
				used.setButtime(temp.getString("buytime"));
				used.setShop_name(temp.getString("shop_name"));
				used.setPic(temp.getString("pic"));
				used.setLongtel(temp.getString("phone"));
				
				if((!temp.getString("duan").equals("null"))&&temp.getString("duan")!=null&&(!temp.getString("duan").equals(""))&&(!temp.getString("duan").equals("0"))){
					used.setShorttel(temp.getString("duan"));
				}else{
					used.setShorttel("");
				}
				//used.setPicUrls(temp.getString("picUrls"));  
				//describ=tour_des.replace("/images/shop/t", "http://121.199.40.201/images/shop/t");
				if (temp.getString("picUrls").equals("null")&&temp.getString("picUrls")!=null&&(!temp.getString("picUrls").equals(""))&&(!temp.getString("picUrls").equals("0"))) {
					used.setPicUrls("");
				}else{
					String picu=temp.getString("picUrls").replace("/images/shop/ts", "http://121.199.40.201/images/shop/ts");
					used.setPicUrls(picu);
					
				}
				uList.add(used);
				
			}
		}catch (Exception e) {
				e.printStackTrace();
		}
		return uList;
	}
}
