package com.fater.oto.util;

public class PathUtil {
	private static String seperator = System.getProperty("file.separator");
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "D:/projectdev/image";
		}else {
			basePath = "/Users/fater/image";
		}
		//根据系统不同更换分隔符
		basePath = basePath.replace("/", seperator);
		return basePath;
	}
	public static String getShopImagePath(long shopId) {
		String imagePath = "/upload/item/shop/"+shopId+"/";
		return imagePath.replace("/", seperator);
	}
}
