package com.kfd.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmojiUtil {

	/**
	 * 表情字符串转换
	 * @param str
	 * @return
	 */
	public static String convertTag(String str) {
		str = str.replaceAll("\\[", "&lt;").replaceAll("\\]", "&gt;").replaceAll("\n", "<br/>");
		Pattern p = Pattern.compile("&lt;face:[0-9]{3}&gt;");
		Matcher m = p.matcher(str);

		while (m.find()) {
			int index = Integer.parseInt(m.group().replace("&lt;face:", "").replace("&gt;", ""));
			if (index < 200) {
				str = str.replaceAll(m.group(), "<img src=\"" + index + ".png\"/>");
			}
		}
		return str;
	}

}
