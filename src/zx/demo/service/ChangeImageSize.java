package zx.demo.service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.List;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 修改图片文件的大小
 * 
 * @author zhangxing
 *
 */
public class ChangeImageSize {

	public static int count = 0;

	// 文件大小标准为100K
	public static final int max_size = 1024 * 100;

	/**
	 * 重新修改文件大小
	 * 
	 * @param file
	 * @param compress_rate
	 * @throws Exception
	 */
	public static void changeImageSize(File file, Double compress_rate) throws Exception {
		// 文件绝对路径
		String file_path = file.getAbsolutePath();

		Image img = ImageIO.read(file); // 图片对象

		// 新图片的宽度
		int width = Integer.parseInt(new java.text.DecimalFormat("0").format(img.getWidth(null) * compress_rate));
		// 新图片的高度
		int height = Integer.parseInt(new java.text.DecimalFormat("0").format(img.getHeight(null) * compress_rate));

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		image.getGraphics().drawImage(img, 0, 0, width, height, null);

		File destFile = new File(file_path);
		FileOutputStream out = new FileOutputStream(destFile);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image); // JPEG编码
		out.close();

	}

	/**
	 * 读取文件夹下所有文件
	 * 
	 * @param dir
	 * @throws Exception
	 */
	public static void readImageDir(File dir) throws Exception {
		File files[] = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 如果是文件夹，则进入继续解析文件夹
			if (files[i].isDirectory()) {
				readImageDir(files[i]);
			} else {
				checkFileSize(files[i]);
			}
		}
	}

	/**
	 * 校验文件的大小
	 * 
	 * @param file
	 * @throws Exception
	 */
	public static void checkFileSize(File file) throws Exception {
		// count = count + 1;
		// if (count > 3) {
		// throw new Exception("123");
		// }
		String file_name = file.getName();
		String file_path = file.getAbsolutePath();
		String suffix = file_name.substring(file_name.lastIndexOf(".") + 1, file_name.length());
		// 判断文件的类型是否是jpg
		if ("jpg".equals(suffix)) {
			Double size = Double.valueOf(file.length());

			// 如果文件大于标准文件的20%，则需要重新缩放照片
			if (size > max_size * 1.2) {
				Double compress_rate = Math.sqrt(max_size / size);
				// 重新修改大小
				changeImageSize(file, compress_rate);
			}
		}

	}

	public static void main(String args[]) throws Exception {
		File rootDir = new File("G:\\car_show_image");
		readImageDir(rootDir);
	}

}
