package laughing.permission.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author huang.xiaolong
 * @date 2013-7-18
 */
public class StringTool {

	/**
	 * 高效字符串转换
	 * 
	 * @param strSource
	 *            要转换的字符串
	 * @param strFrom
	 *            被替换的内容
	 * @param strTo
	 *            替换的内容
	 * @return
	 */
	public static String replace(String strSource, String strFrom, String strTo) {
		if (strSource == null) {
			return null;
		}
		int i = 0;
		if ((i = strSource.indexOf(strFrom, i)) >= 0) {
			char[] cSrc = strSource.toCharArray();
			char[] cTo = strTo.toCharArray();
			int len = strFrom.length();
			StringBuffer buf = new StringBuffer(cSrc.length);
			buf.append(cSrc, 0, i).append(cTo);
			i += len;
			int j = i;
			while ((i = strSource.indexOf(strFrom, i)) > 0) {
				buf.append(cSrc, j, i - j).append(cTo);
				i += len;
				j = i;
			}
			buf.append(cSrc, j, cSrc.length - j);
			return buf.toString();
		}
		return strSource;
	}

	/**
	 * java实现不区分大小写替换
	 * 
	 * @param source
	 * @param oldstring
	 * @param newstring
	 * @return
	 */
	public static String IgnoreCaseReplace(String source, String oldstring,
			String newstring) {
		Pattern p = Pattern.compile(oldstring, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(source);
		String ret = m.replaceAll(newstring);
		return ret;
	}

}
