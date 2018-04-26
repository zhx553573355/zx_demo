package zx.demo.test;

import zx.demo.service.FileDownload;

public class Test {

	public static void main(String args[]) throws Exception {
		FileDownload file = new FileDownload();
		file.downloadNetFile("http://test.e-autofinance.net:8080/kp_test/car_show_image/J/江淮/江淮汽车/瑞风M5.jpg",
				"e:\\2018\\");
		
		
	}
}
