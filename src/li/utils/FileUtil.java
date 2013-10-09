package li.utils;

import java.io.File;
import java.io.IOException;
 
import li.dream.SetheadImageActivity;

public class FileUtil {
	public static File updateDir = null;
	public static File updateFile = null;

	/***
	 * �����ļ�
	 */
	public static void createFile(String name) {
			updateDir = new File(new SetheadImageActivity().getCache()+"APK/");
			updateFile = new File(updateDir + name + ".apk");

			if (!updateDir.exists()) {
				updateDir.mkdirs();
			}
			if (!updateFile.exists()) {
				try {
					updateFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

	}
}
