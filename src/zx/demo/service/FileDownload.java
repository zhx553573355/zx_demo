package zx.demo.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileDownload {

	private static final Logger logger = LoggerFactory.getLogger(FileDownload.class);

	/**
	 * srcFileUrl 源文件路径 outFileUrl 新文件路径
	 */
	public void downloadNetFile(String srcFileUrl, String outFileUrl) throws Exception {
		
		logger.info("----原文件路径-------");
		logger.info(srcFileUrl);
		
		// 空格转码,不转码到时候http请求会出现异常
		String srcFileStr = srcFileUrl.replaceAll(" ", "%20");
		
		URL url = new URL(srcFileStr);
		try {
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(30*1000);
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
			conn.setRequestProperty("Accept-Charset", "UTF-8");

			InputStream inStream = conn.getInputStream();
			FileOutputStream fs = new FileOutputStream(outFileUrl);

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
	}
}
