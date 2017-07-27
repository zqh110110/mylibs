package com.kfd.common;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlUtil {
	public static boolean getBoolean(Element paramElement, String paramString) {
		boolean bool = false;
		try {
			if (!StringUtils.isEmpty(getString(paramElement, paramString))) {
				bool = Boolean
						.parseBoolean(getString(paramElement, paramString));
			}
		} catch (Exception localException) {
		}
		return bool;
	}

	public static int getInt(Element paramElement, String paramString) {
		int i = 0;
		try {
			i = Integer.parseInt(getString(paramElement, paramString));
			i = i;
			return i;
		} catch (Exception localException) {
			// while (true)
			i = 0;
			return -1;
		}
	}

	public static String getString(Element paramElement, String paramString) {
		String str = null;
		try {
			NodeList localNodeList = paramElement
					.getElementsByTagName(paramString);
			if (localNodeList.getLength() != 0) {
				str = getString(localNodeList.item(0));
			}
		} catch (Exception localException) {
		}
		return str;
	}

	public static String getString(Node paramNode) {
		NodeList localNodeList = ((Element) paramNode).getChildNodes();
		StringBuilder localStringBuilder = new StringBuilder();
		int i = 0;
		int j = localNodeList.getLength();
		while (i < j) {
			String str = localNodeList.item(i).getNodeValue();
			if (!StringUtils.isEmpty(str))
				localStringBuilder.append(str);
			i++;
		}
		return localStringBuilder.toString();
	}

	public static String getValue(Element paramElement, String paramString) {
		Object localObject1;
		try {
			Object localObject2 = paramElement
					.getElementsByTagName(paramString).item(0);
			localObject1 = new StringBuilder();
			for (localObject2 = ((Element) localObject2).getFirstChild();; localObject2 = localObject2) {
				if ((localObject2 == null)
						|| (((Node) localObject2).getNodeValue() == null)) {
					localObject1 = ((StringBuilder) localObject1).toString()
							.trim();
					break;
				}
				((StringBuilder) localObject1).append(((Node) localObject2)
						.getNodeValue());
				localObject2 = ((Node) localObject2).getNextSibling();
			}
		} catch (Exception localException) {
			localObject1 = "";
		}
		return (String) localObject1;
	}
}
