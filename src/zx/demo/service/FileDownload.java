package zx.demo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileDownload {

	private static final Logger logger = LoggerFactory.getLogger(FileDownload.class);


	/**
	 * 获取文件夹存放路径
	 * 
	 * @param rootPath
	 * @return
	 * @throws Exception
	 */
	public static String getRealDirectory(String rootPath) throws Exception {
		 SimpleDateFormat year_df = new SimpleDateFormat("yyyy");
		 SimpleDateFormat month_df = new SimpleDateFormat("MM");
		
		// 判断文件存储路径是否存在
		File dir = new File(rootPath);
		if (!dir.exists() && !dir.isDirectory()) {
			throw new Exception("-----文件夹不存在:" + rootPath);
		}

		String ablolutePath = dir.getAbsolutePath();
		String year = year_df.format(new Date());
		String month = month_df.format(new Date());

		// 带年月日的文件名
		File realDir = new File(ablolutePath + File.separator + year + File.separator + month);

		if (!realDir.exists()) {
			realDir.mkdirs();
		}
		System.out.println(realDir.getAbsolutePath());
		return realDir.getAbsolutePath() + File.separator;
	}

	/**
	 * @param srcFileUrl 源文件路径
	 * @param rootPath 文件存储位置
	 * @return 本地文件存储绝对路径
	 */
	public String downloadNetFile(String srcFileUrl, String rootPath) throws Exception {

		// 空格转码,不转码到时候http请求会出现异常
		String srcFileStr = srcFileUrl.replaceAll(" ", "%20");
		String newfileName = "";
		try {
			// 创建文件存放路径
			String realDirPath = getRealDirectory(rootPath);
			// 创建文件绝对路径
			newfileName = realDirPath + UUID.randomUUID().toString().replaceAll("-", "");
			logger.info("----附件存放文件夹-------");
			logger.info(realDirPath);
			URL url = new URL(srcFileStr);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setConnectTimeout(30 * 1000);
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
			conn.setRequestProperty("Accept-Charset", "UTF-8");

			InputStream inStream = conn.getInputStream();
			FileOutputStream fs = new FileOutputStream(newfileName);
			byte buffer[] = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) > 0) {
				fs.write(buffer, 0, len);
			}
			inStream.close();
			fs.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newfileName;
	}
}
