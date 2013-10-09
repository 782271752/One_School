package li.utils;

import java.util.Random;

public class Salt {
	 public String salts()
		{
				String saltsString="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
				Random ran= new Random();
				StringBuffer sb=new StringBuffer();
				for(int i=0;i<6;i++)
				{
					int num=ran.nextInt(saltsString.length());
					sb.append(saltsString.charAt(num));
					
				}
				return sb.toString();
		}
}
