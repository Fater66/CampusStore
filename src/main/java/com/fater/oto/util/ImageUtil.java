package com.fater.oto.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

//封装thumbnailator方法
public class ImageUtil {
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();
	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	
	public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile) {
		File newFile = new File(cFile.getOriginalFilename());
		try {
			cFile.transferTo(newFile);
		} catch (IllegalStateException e) {
			logger.error(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return newFile;
	}
	/**
	 * 处理缩略图，并返回新生成图片的相对值路径
	 * @param thumbnailInputStream
	 * @param targetAddr
	 * @return
	 */
	public static String generateThumbnail(InputStream thumbnailInputStream,String fileName,String targetAddr) {
		//随机名称
		String realFileName = getRandomFileName();
		//文件拓展名
		String extension=getFileExtension(fileName);
		makeDirpath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is" + relativeAddr);
		File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
		logger.debug("current complete Addr is" + PathUtil.getImgBasePath()+relativeAddr);
		try {
			Thumbnails.of(thumbnailInputStream).size(200, 200).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/watermark.jpg")), 
					0.25f).outputQuality(0.8f).toFile(dest);
		}catch(IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return relativeAddr;
	}
	/**
	 * 创建目标路径所涉及到的目录，即/home/work/fater/xxx.jpg
	 * 那么home work fater 这三个文件夹都得自动创建
	 * @param targetAddr
	 */
	private static void makeDirpath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath()+targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}
	/*
	 * 获取输入文件流的扩展名
	 */
	private static String getFileExtension(String fileName) {
//		String originalFileName = cFile.getName();
		return fileName.substring(fileName.lastIndexOf("."));
	}
	/**
	 * 生成随机文件名：当前时间+五位随机数
	 * @return
	 */
	public static String getRandomFileName() {
		// 获取随机五位数
		int rannum = r.nextInt(89999)+10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr+rannum;
	}

	public static void main(String[] args) throws IOException {
		
		Thumbnails.of(new File("/Users/fater/eclipse-workspace/oto/src/main/resources/testimg.png"))
		.size(200, 200).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/watermark.jpg")), 
		0.25f).outputQuality(0.8f).toFile("/Users/fater/eclipse-workspace/oto/src/main/resources/testimgnew.png");
	}
	/**
	 * 判断storepath是文件路径还是目录路径
	 * @param storePath
	 */
	public static void deleteFileOrPath(String storePath) {
		File fileOrPath = new File(PathUtil.getImgBasePath()+storePath);
		if(fileOrPath.exists()) {
			if(fileOrPath.isDirectory()) {
				File files[]=fileOrPath.listFiles();
				for(int i =0;i<files.length;i++)
				{
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}
	}
}
