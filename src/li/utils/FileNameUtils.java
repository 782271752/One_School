package li.utils;

public class FileNameUtils {

	public static String removeExtension(String filename) {
		if (filename == null) {
			return null;
		}
		int index = indexOfExtension(filename);
		if (index == -1) {
			return filename;
		}
		return filename.substring(0, index);
	}

	public static int indexOfExtension(String filename) {
		if (filename == null) {
			return -1;
		}
		int extensionPos = filename.lastIndexOf('.');
		int lastSeparator = indexOfLastSeparator(filename);
		return lastSeparator > extensionPos ? -1 : extensionPos;
	}

	public static int indexOfLastSeparator(String filename) {
		if (filename == null) {
			return -1;
		}
		int lastUnixPos = filename.lastIndexOf('/');
		int lastWindowsPos = filename.lastIndexOf('\\');
		return Math.max(lastUnixPos, lastWindowsPos);
	}

	/**
	 * 获取文件�?
	 * @param filename
	 * @return
	 */
	public static String getName(String filename) {
		if (filename == null) {
			return null;
		}
		int index = indexOfLastSeparator(filename);
		return filename.substring(index + 1);
	}

	/**
	 * 获取去除后缀名之后的文件�?
	 * @param filename
	 * @return
	 */
	public static String getBaseName(String filename) {
		return removeExtension(getName(filename));
	}

}
